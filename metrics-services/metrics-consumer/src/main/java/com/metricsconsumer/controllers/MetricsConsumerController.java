package com.metricsconsumer.controllers;

import com.metricsconsumer.dto.MetricsDataDTO;
import com.metricsconsumer.dto.MetricsTypeDTO;
import com.metricsconsumer.services.MetricsConsumerService;
import io.swagger.v3.oas.annotations.Operation;
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

    private final MetricsConsumerService metricsConsumerService;

    @GetMapping("/metrics")
    @Operation(description = "Выдать все типы метрик в базе.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = MetricsTypeDTO.class)))})})
    public ResponseEntity<List<MetricsTypeDTO>> getMetricsTypes(){
        List<MetricsTypeDTO> types = metricsConsumerService.getMetricsTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/metrics{id}")
    @Operation(description = "Выдать все записи метрики в базе по ее идентификатору(имени).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = MetricsTypeDTO.class)))})})
    public ResponseEntity<List<MetricsDataDTO>> getMetricsByName(@PathVariable("id") String id){
        List<MetricsDataDTO> metrics = metricsConsumerService.findMetricsByName(id);
        return ResponseEntity.ok(metrics);
    }
}
