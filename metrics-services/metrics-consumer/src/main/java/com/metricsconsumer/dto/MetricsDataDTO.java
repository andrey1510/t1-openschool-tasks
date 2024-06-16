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

    @Schema(description = "Время снятия метрики.", example = "2024-06-16T16:11:19.869285")
    private LocalDateTime timestamp;

    @Schema(description = "Значение метрики.", example = "845.287")
    private Double value;

    @Schema(description = "Единица измерения метрики.", example = "seconds")
    private String baseUnit;

}
