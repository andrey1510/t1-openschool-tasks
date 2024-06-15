package com.metricsproducer.controllers;

import com.metricsproducer.services.MetricsProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics-sender")
@RequiredArgsConstructor
@Schema(description = "Управление отправкой метрик в другой сервис.")
public class MetricsProducerController {

    private final MetricsProducerService metricsProducerService;

    @PostMapping("/metrics")
    @Operation(description = "Отправить один раз метрики в другой сервис все отслеживаемые метрики " +
        "(jvm.memory.used, jvm.memory.committed, process.uptime, process.cpu.time.")
    public ResponseEntity<String> sendMetrics() {

        metricsProducerService.sendMetrics("jvm-metrics");
        metricsProducerService.sendMetrics("process-metrics");

        return ResponseEntity.ok("Отправлены метрики: jvm.memory.used, jvm.memory.committed, process.uptime, process.cpu.time)");
    }

}
