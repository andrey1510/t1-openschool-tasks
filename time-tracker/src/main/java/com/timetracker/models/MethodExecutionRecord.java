package com.timetracker.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "execution_record")
public class MethodExecutionRecord {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "Идентификатор конкретного вызова метода, присваивается случайным образом.")
    private UUID id;

    @Column(name = "method_signature", nullable = false)
    @Schema(description = "Сигнатура метода.")
    private String methodSignature;

    @Column(name = "class_name", nullable = false)
    @Schema(description = "Класс метода.")
    private String className;

    @Column(name = "package_name", nullable = false)
    @Schema(description = "Пакет метода.")
    private String packageName;

    @Column(name = "start_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Начало работы метода.")
    private LocalDateTime startTime;

    @Column(name = "end_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Завершение работы метода.")
    private LocalDateTime endTime;

    @Column(name = "duration", updatable = false, nullable = false)
    @Schema(description = "Длительность работы метода, миллисекунды.")
    private long duration;

}
