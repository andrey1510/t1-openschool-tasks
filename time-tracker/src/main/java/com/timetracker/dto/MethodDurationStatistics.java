package com.timetracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MethodDurationStatistics {

    @Schema(description = "Сигнатура метода.")
    private String methodSignature;

    @Schema(description = "Класс метода.")
    private String className;

    @Schema(description = "Пакет метода.")
    private String packageName;

    @Schema(description = "Общее время работы метода, миллисекунды.")
    private Long totalDuration;

    @Schema(description = "Среднее время работы метода, миллисекунды.")
    private Double averageDuration;

    @Schema(description = "Самое короткое время работы метода, миллисекунды.")
    private Long minDuration;

    @Schema(description = "Самое длительное время работы метода, миллисекунды.")
    private Long maxDuration;

    @Schema(description = "Cтандартное отклонение, миллисекунды.")
    private Double standardDeviation;

    @Schema(description = "Количество вызовов метода.")
    private Long calls;

}
