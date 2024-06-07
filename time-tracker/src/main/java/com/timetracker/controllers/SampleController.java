package com.timetracker.controllers;

import com.timetracker.exceptions.WrongNumberException;
import com.timetracker.services.AsyncSampleService;
import com.timetracker.services.SyncSampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/sample-methods-launcher")
@RequiredArgsConstructor
public class SampleController {

    private static final String TOO_LESS_ITERATIONS = "Количество итераций не может быть меньше одной.";
    private static final String TOO_LITTLE_DURATION = "Длительность не может быть меньше нуля.";

    private final AsyncSampleService asyncSampleService;
    private final SyncSampleService syncSampleService;

    @PostMapping("/sync/fixedtime-method/{duration}")
    @Operation(description = "Демонстрация @TrackTime - запустить тестовый метод, " +
        "указать желаемую задержку в миллисекундах.")
    public ResponseEntity<String> launchSyncFixedtimeMethod(@PathVariable("duration") int duration) {

        String result = "Запущен метод из класса SyncSampleServiceImpl: " +
            "syncFixedtimeMethod(int duration)";

        if (duration <= 0) {throw new WrongNumberException(TOO_LITTLE_DURATION);}

        syncSampleService.syncFixedtimeMethod(duration);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sync/randomtime-methods/{iterations}")
    @Operation(description = "Демонстрация @TrackTime - запустить 3 тестовых метода " +
        "с рандомным временем работы (до 2 секунд); указать желаемое количество итераций.")
    public ResponseEntity<String> launchSyncRandomtimeMethods(@PathVariable("iterations") int iterations) {

        if (iterations <= 0) {throw new WrongNumberException(TOO_LESS_ITERATIONS);}

        for (int i = 0; i < iterations; i++) {
            syncSampleService.syncSimpleMethod();
            syncSampleService.syncOverloadedMethod(1);
            syncSampleService.syncOverloadedMethod(1,2);
        }

        String result = "Запущены методы из класса SyncSampleServiceImpl: " +
            "syncSimpleMethod(), syncOverloadedMethod(int input1), syncOverloadedMethod(int input1, long input2)";

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sync/crashing-method")
    @Operation(description = "Демонстрация @TrackTime - запустить тестовый метод syncCrashingMethod(), " +
        "который выдаст ошибку через 1 секунду после запуска.")
    public void launchSyncCrashingMethod() {
        syncSampleService.syncCrashingMethod();
    }

    @PostMapping("/async/fixedtime-method/{duration}")
    @Operation(description = "Демонстрация @TrackAsyncTime - запустить тестовый асинхронный метод, " +
        "указать желаемую задержку в миллисекундах.")
    public ResponseEntity<String> launchAsyncFixedtimeMethod(@PathVariable("duration") int duration) {

        if (duration <= 0) {throw new WrongNumberException(TOO_LITTLE_DURATION);}

        asyncSampleService.asyncFixedtimeMethod(duration);

        String result = "Запущен метод из класса AsyncSampleServiceImpl: " +
            "asyncFixedtimeMethod(int duration)";

        return ResponseEntity.ok(result);
    }

    @PostMapping("/async/randomtime-methods/{iterations}")
    @Operation(description = "Демонстрация @TrackAsyncTime - запустить 3 тестовых метода " +
        "с рандомным временем работы (от 2 до 3 секунд); указать желаемое количество итераций.")
    public ResponseEntity<String> launchAsyncRandomtimeMethods(@PathVariable("iterations") int iterations) {

        if (iterations <= 0) {throw new WrongNumberException(TOO_LESS_ITERATIONS);}

        for (int i = 0; i < iterations; i++) {
            CompletableFuture<String> method1 = asyncSampleService.asyncFirstRandomtimeMethod();
            CompletableFuture<String> method2 = asyncSampleService.asyncSecondRandomtimeMethod();
            CompletableFuture<String> method3 = asyncSampleService.asyncThirdRandomtimeMethod();
        }

        String result = "Запущены методы из класса AsyncSampleServiceImpl: " +
            "asyncFirstRandomtimeMethod(), asyncSecondRandomtimeMethod(), asyncThirdRandomtimeMethod()";

        return ResponseEntity.ok(result);
    }

}
