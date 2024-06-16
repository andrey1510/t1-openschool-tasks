package com.metricsconsumer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Имя и описание метрики.")
public class MetricsTypeDTO {

    @Schema(description = "Идентификатор (имя) метрики.")
    private String name;

    @Schema(description = "Описание метрики.")
    private String description;

}
