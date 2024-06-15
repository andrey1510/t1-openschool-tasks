package com.metricsconsumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Записи со статистикой по конкретной метрике.")
public class MetricsDataDTO {

    @Schema(description = "Время снятия метрики.")
    private LocalDateTime timestamp;

    @Schema(description = "Значение метрики.")
    private Double value;

    @Schema(description = "Единица измерения метрики.")
    private String baseUnit;

}
