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

    @Schema(description = "Идентификатор (имя) метрики.", example = "jvm.memory.used")
    private String name;

    @Schema(description = "Описание метрики.", example = "The amount of used memory")
    private String description;

}
