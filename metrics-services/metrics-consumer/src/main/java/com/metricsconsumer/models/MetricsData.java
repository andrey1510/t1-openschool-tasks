package com.metricsconsumer.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Таблица с показателями метрик.")
@Table(name = "metrics_data")
public class MetricsData {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Идентификатор записи метрики, присваивается случайным образом.")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name", referencedColumnName = "name")
    private MetricsType metricsType;

    @Column(name = "timestamp", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Время взятия метрики.")
    private LocalDateTime timestamp;

    @Column(name = "value", updatable = false, nullable = false)
    @Schema(description = "Значение метрики.")
    private Double value;

}
