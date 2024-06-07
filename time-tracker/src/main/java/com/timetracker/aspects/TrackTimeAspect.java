package com.timetracker.aspects;

import com.timetracker.exceptions.TimeTrackingAspectException;
import com.timetracker.models.MethodExecutionRecord;
import com.timetracker.repositories.TimeTrackingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

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

        MethodExecutionRecord methodExecutionRecord = new MethodExecutionRecord();

        MethodSignature fullSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = fullSignature.getMethod().getName();
        String methodParameters = Arrays.stream(fullSignature.getMethod().getParameters())
            .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
            .collect(Collectors.joining(", "));
        String methodSignature = String.format("%s(%s)", methodName, methodParameters);

        methodExecutionRecord.setMethodSignature(methodSignature);
        methodExecutionRecord.setClassName(joinPoint.getTarget().getClass().getSimpleName());
        methodExecutionRecord.setPackageName(joinPoint.getTarget().getClass().getPackageName());

        LocalDateTime startTime = LocalDateTime.now();

        methodExecutionRecord.setStartTime(startTime);

        log.info("Method {} started at {}.", methodSignature, startTime);

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Method - {} encountered an exception: {}", joinPoint.getSignature().getName(), throwable.getMessage());
            throw new TimeTrackingAspectException("Method encountered an exception.", throwable);
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            methodExecutionRecord.setEndTime(endTime);
            log.info("Method {} finished at {}.", methodSignature, endTime);
            methodExecutionRecord.setDuration(Duration.between(startTime, endTime).toMillis());
            timeTrackingRepository.save(methodExecutionRecord);
        }
    }

}
