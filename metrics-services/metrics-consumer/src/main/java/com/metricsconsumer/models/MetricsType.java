package com.metricsconsumer.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Таблица с видами метрик, содержащихся в базе.")
@Table(name = "metrics_type")
public class MetricsType {

    @Id
    @Column(name = "name", nullable = false)
    @Schema(description = "Имя метрики.")
    private String name;

    @Column(name = "description", nullable = false)
    @Schema(description = "Описание метрики.")
    private String description;

    @Column(name = "unit")
    @Schema(description = "Единица измерения метрики.")
    private String baseUnit;

    @OneToMany(mappedBy = "metricsType")
    private Set<MetricsData> metrics = new HashSet<>();
}
