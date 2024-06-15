package com.metricsproducer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsProducerServiceImpl implements MetricsProducerService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.metrics-topic.name}")
    private String metricsTopic;

    private final String PROCESS_CPU_TIME_URL = "http://localhost:8865/actuator/metrics/process.cpu.time";
    private final String PROCESS_UPTIME_URL = "http://localhost:8865/actuator/metrics/process.uptime";
    private final String JVM_MEMORY_USED_URL = "http://localhost:8865/actuator/metrics/jvm.memory.used";
    private final String JVM_MEMORY_COMMITTED = "http://localhost:8865/actuator/metrics/jvm.memory.committed";

    @Override
    public void sendMetrics(String metricsGroupType) {

        List<String> metrics = switch (metricsGroupType) {
            case "jvm-metrics" -> getJvmMetrics();
            case "process-metrics" -> getProcessMetrics();
            default -> Collections.emptyList();
        };

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Header timestampHeader = new RecordHeader("timestamp", timestamp.getBytes(StandardCharsets.UTF_8));

        log.info("Metrics collected: {}", metrics);

        metrics.forEach(metric -> {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                metricsTopic, null, metricsGroupType, metric, List.of(timestampHeader));
            kafkaTemplate.send(record);
        });
    }

    private List<String> getJvmMetrics() {
        return List.of(
            Objects.requireNonNull(restTemplate.getForObject(JVM_MEMORY_USED_URL, String.class)),
            Objects.requireNonNull(restTemplate.getForObject(JVM_MEMORY_COMMITTED, String.class))
            );
    }

    private List<String> getProcessMetrics() {
        return List.of(
            Objects.requireNonNull(restTemplate.getForObject(PROCESS_CPU_TIME_URL, String.class)),
            Objects.requireNonNull(restTemplate.getForObject(PROCESS_UPTIME_URL, String.class))
        );
    }
}
