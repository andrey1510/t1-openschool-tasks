package com.timetracker.controllers;

import com.timetracker.dto.MethodDurationStatistics;
import com.timetracker.exceptions.DatabaseIsEmptyException;
import com.timetracker.models.MethodExecutionEntry;
import com.timetracker.services.TimeTrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/methods-operations")
@RequiredArgsConstructor
@Schema(description = "Все операции по получению и анализу статистики времени выполнения методов.")
public class TimeTrackingController {

    private static final String NO_METHODS_WITH_SUCH_CLASS_FOUND = "В базе не найдено методов из указанного класса.";
    private static final String NO_METHODS_WITH_SUCH_PACKAGE_FOUND = "В базе не найдено методов из указанного пакета.";
    private static final String DATABASE_IS_EMPTY = "В базе нет методов.";

    private final TimeTrackingService timeTrackingService;

    @GetMapping("/execution-entries")
    @Operation(description = "Выдать все записи об вызовах методов с аннотациями @TrackTime и @TrackAsyncTime.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = MethodExecutionEntry.class)))})})
    public ResponseEntity<List<MethodExecutionEntry>> getAllCalls(){
        List<MethodExecutionEntry> allCalls = timeTrackingService.getAllCalls();
        if (allCalls.isEmpty()) throw new DatabaseIsEmptyException(DATABASE_IS_EMPTY);
        return ResponseEntity.ok(allCalls);
    }

    @GetMapping("/statistics")
    @Operation(description = "Выдать статистику времени выполнения методов с аннотациями @TrackTime и @TrackAsyncTime. " +
        "Можно задать фильтр по классу или пакету. Запрос без фильтра выдаст все подходящие методы.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = MethodDurationStatistics.class)))})})
    public ResponseEntity<List<MethodDurationStatistics>> getDurationStatistics(
        @Parameter(description = "Краткое имя класса, к которому принадлежит метод.",
            example = "SampleServiceTwoImpl")
        @RequestParam(value = "className", required = false) String className,
        @Parameter(description = "Полное имя пакета, к которому принадлежит метод.",
            example = "com.timetracker.services.sampleTwo")
        @RequestParam(value = "packageName", required = false) String packageName){

        List<MethodDurationStatistics> statistics;

        if (className != null) {
            statistics = timeTrackingService.getDurationStatisticsFilterByClass(className);
            if (statistics.isEmpty()) throw new DatabaseIsEmptyException(NO_METHODS_WITH_SUCH_CLASS_FOUND);
        } else if (packageName != null) {
            statistics = timeTrackingService.getDurationStatisticsFilterByPackage(packageName);
            if (statistics.isEmpty()) throw new DatabaseIsEmptyException(NO_METHODS_WITH_SUCH_PACKAGE_FOUND);
        } else {
            statistics = timeTrackingService.getDurationStatistics();
            if (statistics.isEmpty()) throw new DatabaseIsEmptyException(DATABASE_IS_EMPTY);
        }

        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/delete-all-data")
    @Operation(description = "Удалить из базы все записи о вызовах методов (удалить все строки)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Удаление выполнено")})
    public ResponseEntity<String> deleteAll(){
        timeTrackingService.deleteAll();
        return ResponseEntity.ok("Удаление выполнено");
    }
}
