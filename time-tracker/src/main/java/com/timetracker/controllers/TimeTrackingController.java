package com.timetracker.controllers;

import com.timetracker.dto.MethodDurationStatistics;
import com.timetracker.exceptions.NoMethodsFoundException;
import com.timetracker.models.MethodExecutionRecord;
import com.timetracker.services.TimeTrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/methods-statistics")
@RequiredArgsConstructor
public class TimeTrackingController {

    private static final String NO_METHODS_WITH_REQUESTED_CLASS_FOUND = "В базе не найдено методов из указанного класса.";
    private static final String NO_METHODS_WITH_REQUESTED_PACKAGE_FOUND = "В базе не найдено методов из указанного пакета.";
    private static final String NO_METHODS_FOUND = "В базе не найдено подходящих методов.";

    private final TimeTrackingService timeTrackingService;

    @GetMapping("/execution-records")
    @Operation(description = "Выдать все записи об вызовах методов с аннотациями @TrackTime и @TrackAsyncTime.")
    public ResponseEntity<List<MethodExecutionRecord>> getAllCalls(){
        return ResponseEntity.ok(timeTrackingService.getAllCalls());
    }

    @GetMapping("/statistics")
    @Operation(description = "Выдать статистику времени выполнения методов с аннотациями @TrackTime и @TrackAsyncTime. " +
        "Можно задать фильтр по классу или пакету. Запрос без фильтра выдаст все подходящие методы.")
    public ResponseEntity<List<MethodDurationStatistics>> getDurationStatistics(
        @Parameter(description = "Краткое имя класса, к которому принадлежит метод.", example = "SomeClass")
        @RequestParam(value = "className", required = false) String className,
        @Parameter(description = "Полное имя пакета, к которому принадлежит метод.", example = "com.example.sample-package")
        @RequestParam(value = "packageName", required = false) String packageName){

        List<MethodDurationStatistics> statistics;

        if (className != null) {
            statistics = timeTrackingService.getDurationStatisticsFilterByClass(className);
            if (statistics.isEmpty()) throw new NoMethodsFoundException(NO_METHODS_WITH_REQUESTED_CLASS_FOUND);
        } else if (packageName != null) {
            statistics = timeTrackingService.getDurationStatisticsFilterByPackage(packageName);
            if (statistics.isEmpty()) throw new NoMethodsFoundException(NO_METHODS_WITH_REQUESTED_PACKAGE_FOUND);
        } else {
            statistics = timeTrackingService.getDurationStatistics();
            if (statistics.isEmpty()) throw new NoMethodsFoundException(NO_METHODS_FOUND);
        }

        return ResponseEntity.ok(statistics);
    }
}
