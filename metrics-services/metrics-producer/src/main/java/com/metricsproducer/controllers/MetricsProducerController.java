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
    @Operation(description = "Отправить один раз метрики jvm.memory.used, process.uptime, process.cpu.time " +
        "в другой сервис.")
    public ResponseEntity<String> sendMetrics() {

        metricsProducerService.sendProcessCpuTimeMetrics();

        return ResponseEntity.ok("Метрики отправлены");
    }

}
