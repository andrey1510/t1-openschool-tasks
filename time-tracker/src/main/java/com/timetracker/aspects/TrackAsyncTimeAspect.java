package com.timetracker.aspects;

import com.timetracker.exceptions.TimeTrackingAspectException;
import com.timetracker.repositories.TimeTrackingRepository;
import com.timetracker.utils.MethodExecutionEntryCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class TrackAsyncTimeAspect {

    private final TimeTrackingRepository timeTrackingRepository;

    @Pointcut("@annotation(com.timetracker.annotations.TrackAsyncTime)")
    public void trackAsyncTimePointcut() {
    }

    @Around("trackAsyncTimePointcut()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) {
        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(() -> {
            Object result;
            MethodSignature fullSignature = (MethodSignature) joinPoint.getSignature();
            LocalDateTime startTime = LocalDateTime.now();
            log.info("Method {} started at {}.", fullSignature, startTime);

            try {
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("Method - {} encountered an exception: {}", joinPoint.getSignature().getName(), throwable.getMessage());
                throw new TimeTrackingAspectException("Method encountered an exception.", throwable);
            } finally {
                LocalDateTime endTime = LocalDateTime.now();
                log.info("Method {} finished at {}.", fullSignature, endTime);
                timeTrackingRepository.save(
                    MethodExecutionEntryCreator.createEntity(joinPoint, fullSignature, startTime, endTime));
            }
            return result;
        });
        return completableFuture.join();
    }

}

