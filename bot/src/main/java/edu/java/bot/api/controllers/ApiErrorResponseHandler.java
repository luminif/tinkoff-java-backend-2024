package edu.java.bot.api.controllers;

import edu.java.bot.api.components.ApiErrorResponse;
import edu.java.bot.exceptions.UpdateAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorResponseHandler {
    @ExceptionHandler(UpdateAlreadyExistsException.class)
    public ApiErrorResponse handleUpdateAlreadyExists(UpdateAlreadyExistsException ex) {
        return new ApiErrorResponse(
            ex.getDescription(),
            ex.getCode(),
            ex.getClass().getName(),
            ex.getMessage(),
            getStackTrace(ex)
        );
    }

    private List<String> getStackTrace(RuntimeException ex) {
        List<String> stackTrace = new ArrayList<>();

        for (var e : ex.getStackTrace()) {
            stackTrace.add(e.toString());
        }

        return stackTrace;
    }
}
