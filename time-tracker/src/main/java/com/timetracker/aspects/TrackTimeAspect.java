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

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class TrackTimeAspect {

    private final TimeTrackingRepository timeTrackingRepository;

    @Pointcut("@annotation(com.timetracker.annotations.TrackTime)")
    public void trackTimePointcut() {
    }

    @Around("trackTimePointcut()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) {

        MethodSignature fullSignature = (MethodSignature) joinPoint.getSignature();
        LocalDateTime startTime = LocalDateTime.now();
        log.info("Method {} started at {}.", fullSignature, startTime);

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Method - {} encountered an exception: {}", joinPoint.getSignature().getName(), throwable.getMessage());
            throw new TimeTrackingAspectException("Method encountered an exception.", throwable);
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            log.info("Method {} finished at {}.", fullSignature, endTime);
            timeTrackingRepository.save(
                MethodExecutionEntryCreator.createEntity(joinPoint, fullSignature, startTime, endTime));
        }
    }

}
