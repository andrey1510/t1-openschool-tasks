package com.timetracker.controllers;

import com.timetracker.exceptions.NoMethodsFoundException;
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

    @ExceptionHandler(NoMethodsFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoMethodFoundException(NoMethodsFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @AllArgsConstructor
    @Getter
    private static class ErrorResponse {
        @Schema(description = "Сообщение об ошибке.")
        private final String message;
    }

}
