package com.timetracker.utils;

import com.timetracker.models.MethodExecutionEntry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MethodExecutionEntryCreator {
    public static MethodExecutionEntry createEntity(
        ProceedingJoinPoint joinPoint, MethodSignature fullSignature, LocalDateTime startTime, LocalDateTime endTime) {

        String methodName = fullSignature.getMethod().getName();
        String methodParameters = Arrays.stream(fullSignature.getMethod().getParameters())
            .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
            .collect(Collectors.joining(", "));

        return MethodExecutionEntry.builder()
            .methodSignature(String.format("%s(%s)", methodName, methodParameters))
            .className(joinPoint.getTarget().getClass().getSimpleName())
            .packageName(joinPoint.getTarget().getClass().getPackageName())
            .startTime(startTime)
            .endTime(endTime)
            .duration(Duration.between(startTime, endTime).toMillis())
            .build();
    }
}
