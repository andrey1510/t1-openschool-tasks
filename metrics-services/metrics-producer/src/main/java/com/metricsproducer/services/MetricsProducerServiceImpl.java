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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsProducerServiceImpl implements MetricsProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.metrics-topic.name}")
    private String topic;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String PROCESS_CPU_TIME_URL = "http://localhost:8865/actuator/metrics/process.cpu.time";
    private final String PROCESS_UPTIME_URL = "http://localhost:8865/actuator/metrics/process.uptime";
    private final String JVM_MEMORY_USED_URL = "http://localhost:8865/actuator/metrics/jvm.memory.used";

    @Override
    public void sendProcessCpuTimeMetrics() {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        List<String> metrics = getMetrics();
        log.info("Metrics collected: {}", metrics);
        metrics.forEach(metric -> {
            kafkaTemplate.send(topic, timestamp, metric);
        });
    }

    private List<String> getMetrics() {
        return List.of(
            Objects.requireNonNull(restTemplate.getForObject(PROCESS_CPU_TIME_URL, String.class)),
            Objects.requireNonNull(restTemplate.getForObject(PROCESS_UPTIME_URL, String.class)),
            Objects.requireNonNull(restTemplate.getForObject(JVM_MEMORY_USED_URL, String.class))
            );
    }
}
