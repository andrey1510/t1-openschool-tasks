package com.metricsconsumer.controllers;

import com.metricsconsumer.dto.MetricsDataDTO;
import com.metricsconsumer.dto.MetricsTypeDTO;
import com.metricsconsumer.exceptions.DatabaseIsEmptyException;
import com.metricsconsumer.exceptions.MetricsDataNotFoundException;
import com.metricsconsumer.exceptions.MetricsTypeNotFoundException;
import com.metricsconsumer.services.MetricsConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metrics-receiver")
@RequiredArgsConstructor
@Schema(description = "Все операции по получению метрик из базы.")
public class MetricsConsumerController {

    private final String METRICS_DATA_NOT_FOUND = "В базе не найдено данных запрошенного типа метрики.";
    private final String METRICS_TYPE_NOT_FOUND = "В базе не найдено запрошенного типа метрики.";
    private final String DATABASE_IS_EMPTY = "В базе не содержится метрик.";

    private final MetricsConsumerService metricsConsumerService;

    @GetMapping("/metrics")
    @Operation(description = "Получить все типы метрик в базе.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = MetricsTypeDTO.class)))})})
    public ResponseEntity<List<MetricsTypeDTO>> getMetricsTypes(){
        List<MetricsTypeDTO> types = metricsConsumerService.getMetricsTypes();
        if (types.isEmpty()) throw new DatabaseIsEmptyException(DATABASE_IS_EMPTY);
        return ResponseEntity.ok(types);
    }

    @GetMapping("/metrics{id}")
    @Operation(description = "Получить все записи метрики в базе по ее идентификатору(имени).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = MetricsTypeDTO.class)))})})
    public ResponseEntity<List<MetricsDataDTO>> getMetricsByName(
        @Parameter(description = "Идентификатор (имя) метрики.", example = "process.uptime")
        @PathVariable("id") String id){

        if(metricsConsumerService.getMetricTypeByName(id).isEmpty())
            throw new MetricsTypeNotFoundException(METRICS_TYPE_NOT_FOUND);

        List<MetricsDataDTO> metrics = metricsConsumerService.findMetricsByName(id);
        if (metrics.isEmpty()) throw new MetricsDataNotFoundException(METRICS_DATA_NOT_FOUND);

        return ResponseEntity.ok(metrics);
    }
}
