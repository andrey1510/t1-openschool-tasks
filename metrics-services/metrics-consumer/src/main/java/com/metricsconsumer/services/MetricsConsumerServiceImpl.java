package com.metricsconsumer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metricsconsumer.dto.MetricsDataDTO;
import com.metricsconsumer.dto.MetricsTypeDTO;
import com.metricsconsumer.models.MetricsData;
import com.metricsconsumer.models.MetricsType;
import com.metricsconsumer.repositories.MetricsDataRepository;
import com.metricsconsumer.repositories.MetricsTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.common.header.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsConsumerServiceImpl implements MetricsConsumerService {

    private final MetricsDataRepository metricsDataRepository;
    private final MetricsTypeRepository metricsTypeRepository;

    @Override
    @KafkaListener(topics = "${spring.kafka.metrics-topic.name}",
        groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void receiveMetrics(ConsumerRecord<String, String> message) {

        log.info("Message received: key: {}, value: {}", message.key(), message.value());

        LocalDateTime timestamp = LocalDateTime.parse(
            new String(message.headers().lastHeader("timestamp").value(), StandardCharsets.UTF_8),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        JsonNode extractedJson = extractJson(message.value());

        Optional<MetricsType> metricsTypeExists = metricsTypeRepository.findById(extractedJson.get("name").asText());

        MetricsType metricsType = metricsTypeExists.orElseGet(() -> MetricsType.builder()
            .name(extractedJson.get("name").asText())
            .description(extractedJson.get("description").asText())
            .baseUnit(extractedJson.get("baseUnit").asText())
            .build());

        if (metricsTypeExists.isEmpty()) metricsTypeRepository.save(metricsType);
        metricsDataRepository.save(MetricsData.builder()
            .metricsType(metricsType)
            .timestamp(timestamp)
            .value(extractedJson.get("measurements").get(0).get("value").asDouble())
            .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetricsTypeDTO> getMetricsTypes() {
        return metricsTypeRepository.findMetricsTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetricsDataDTO> findMetricsByName(String name) {
        return metricsDataRepository.findMetricsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetricsType> getMetricTypeByName(String name){
        return metricsTypeRepository.findById(name);
    }

    private JsonNode extractJson(String messageValue){
        try {
            return new ObjectMapper().readTree(messageValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
