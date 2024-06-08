package com.timetracker.controllers;

import com.timetracker.exceptions.IntentionallyCausedException;
import com.timetracker.exceptions.DatabaseIsEmptyException;
import com.timetracker.exceptions.WrongNumberException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {

    @ExceptionHandler(WrongNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleWrongNumberException(WrongNumberException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(DatabaseIsEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleDatabaseIsEmptyException(DatabaseIsEmptyException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(IntentionallyCausedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleIntentionallyCausedException(IntentionallyCausedException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @AllArgsConstructor
    @Getter
    private static class ErrorResponse {
        @Schema(description = "Сообщение об ошибке.")
        private final String message;
    }

}
