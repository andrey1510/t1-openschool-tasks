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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        String kafkaKey = message.key();
        String kafkaValue = message.value();

        log.info("Message received: key: {}, value: {}", kafkaKey, kafkaValue);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;
        try {
            root = objectMapper.readTree(kafkaValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Optional<MetricsType> metricsTypeAlreadySaved = metricsTypeRepository.findById(root.get("name").asText());

        MetricsType metricsType = metricsTypeAlreadySaved.orElseGet(() -> MetricsType.builder()
            .name(root.get("name").asText())
            .description(root.get("description").asText())
            .baseUnit(root.get("baseUnit").asText())
            .build());
        MetricsData metricsData = MetricsData.builder()
            .metricsType(metricsType)
            .timestamp(LocalDateTime.parse(kafkaKey, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .value(root.get("measurements").get(0).get("value").asDouble())
            .build();

        if (!metricsTypeAlreadySaved.isPresent()) metricsTypeRepository.save(metricsType);
        metricsDataRepository.save(metricsData);

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
}
