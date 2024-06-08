package com.timetracker.controllers;

import com.timetracker.exceptions.WrongNumberException;
import com.timetracker.services.sampleTwo.SampleServiceThree;
import com.timetracker.services.sampleTwo.SampleServiceTwo;
import com.timetracker.services.sampleOne.SampleServiceOne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/sample-methods-launcher")
@RequiredArgsConstructor
@Schema(description = "Управление тестовыми методами, " +
    "с помощью которых можно можно формировать данные для получения статистики времени выполнения методов.")
public class SampleMethodsController {

    private static final String TOO_LESS_ITERATIONS = "Количество итераций не может быть меньше одной.";
    private static final String TOO_LITTLE_DURATION = "Длительность не может быть меньше нуля.";

    private final SampleServiceOne sampleServiceOne;
    private final SampleServiceTwo sampleServiceTwo;
    private final SampleServiceThree sampleServiceThree;

    @PostMapping("/sync/fixedtime-method/{duration}")
    @Operation(description = "Запустить тестовый метод с @TrackTime, указать желаемую задержку в миллисекундах.")
    public ResponseEntity<String> launchFixedtimeMethodTracktime(
        @Parameter(description = "Желаемая продолжительность задержки тестового метода, миллисекунды.", example = "3000")
        @PathVariable("duration") int duration) {

        if (duration < 0) {throw new WrongNumberException(TOO_LITTLE_DURATION);}

        sampleServiceOne.fixedtimeMethodTimetrack(duration);
        String result = "Запущен метод fixedtimeMethodTimetrack(int duration) " +
            "из класса SampleServiceOne пакета com.timetracker.services.sampleOne";

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sync/randomtime-methods/{iterations}")
    @Operation(description = "Запустить 3 тестовых метода с @TrackTime " +
        "с рандомным временем работы (до 2 секунд); указать желаемое количество итераций.")
    public ResponseEntity<String> launchRandomtimeMethodsTracktime(
        @Parameter(description = "Желаемое количество вызовов всех методов.", example = "3")
        @PathVariable("iterations") int iterations) {

        if (iterations <= 0) {throw new WrongNumberException(TOO_LESS_ITERATIONS);}

        for (int i = 0; i < iterations; i++) {
            sampleServiceTwo.randomMethodTracktime();
            sampleServiceTwo.overloadedMethodTracktime(1);
            sampleServiceTwo.overloadedMethodTracktime(1,2);
        }

        String result = "Запущены методы из класса SampleServiceTwo пакета com.timetracker.services.sampleTwo: " +
            "randomMethodTracktime(), overloadedMethodTracktime(int input1), overloadedMethodTracktime(int input1, long input2)";

        return ResponseEntity.ok(result);
    }

    @PostMapping("/sync/crashing-method")
    @Operation(description = "Запустить тестовый метод crashingMethodTracktime() с @TrackTime, " +
        "который выдаст ошибку через 1 секунду после запуска.")
    public ResponseEntity<String> launchCrashingMethodTracktime() {
        sampleServiceThree.crashingMethodTracktime();
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/async/fixedtime-method/{duration}")
    @Operation(description = "Запустить тестовый метод с @TrackAsyncTime, указать желаемую задержку в миллисекундах.")
    public ResponseEntity<String> launchFixedtimeMethodTrackAsync(
        @Parameter(description = "Желаемая продолжительность задержки тестового метода, миллисекунды.", example = "3000")
        @PathVariable("duration") int duration) {

        if (duration < 0) {throw new WrongNumberException(TOO_LITTLE_DURATION);}

        sampleServiceOne.fixedtimeMethodTrackAsync(duration);
        String result = "Запущен метод fixedtimeMethodTrackAsync(int duration) " +
            "из класса SampleServiceOne пакета com.timetracker.services.sampleOne";

        return ResponseEntity.ok(result);
    }

    @PostMapping("/async/randomtime-methods/{iterations}")
    @Operation(description = "Запустить 3 тестовых метода с @TrackTime " +
        "с рандомным временем работы (до 2 секунд); указать желаемое количество итераций.")
    public ResponseEntity<String> launchRandomtimeMethodsTrackAsync(
        @Parameter(description = "Желаемое количество вызовов всех методов.", example = "3")
        @PathVariable("iterations") int iterations) {

        if (iterations <= 0) {throw new WrongNumberException(TOO_LESS_ITERATIONS);}

        for (int i = 0; i < iterations; i++) {
            sampleServiceThree.randomMethodOneTrackAsync();
            sampleServiceThree.randomMethodTwoTrackAsync();
        }

        String result = "Запущены методы из класса SampleServiceThree пакета com.timetracker.services.sampleTwo: " +
            "randomMethodOneTrackAsync(), randomMethodOneTrackAsync()";

        return ResponseEntity.ok(result);
    }

    @PostMapping("/async/crashing-method")
    @Operation(description = "Запустить тестовый метод crashingMethodTrackAsync() с @TrackAsyncTime, " +
        "который выдаст ошибку через 1 секунду после запуска.")
    public ResponseEntity<String> launchCrashingMethodTrackAsync() {
        sampleServiceThree.crashingMethodTrackAsync();
        return ResponseEntity.ok("Ok");
    }



}
