package com.metricsconsumer.services;

import com.metricsconsumer.dto.MetricsDataDTO;
import com.metricsconsumer.dto.MetricsTypeDTO;
import com.metricsconsumer.models.MetricsType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MetricsConsumerService {


    @KafkaListener(topics = "${spring.kafka.metrics-topic.name}",
        groupId = "${spring.kafka.consumer.group-id}")
    void receiveMetrics(ConsumerRecord<String, String> message);


    @Transactional(readOnly = true)
    List<MetricsTypeDTO> getMetricsTypes();

    List<MetricsDataDTO> findMetricsByName(String name);

    @Transactional(readOnly = true)
    Optional<MetricsType> getMetricTypeByName(String name);
}
