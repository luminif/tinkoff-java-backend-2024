package edu.java.api.controllers;

import edu.java.api.components.ApiErrorResponse;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.exceptions.LinkAlreadyAddedException;
import edu.java.exceptions.LinkNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorResponseHandler {
    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ApiErrorResponse handleChatAlreadyRegistered(ChatAlreadyRegisteredException ex) {
        return new ApiErrorResponse(
            ex.getDescription(),
            ex.getCode(),
            ex.getClass().getName(),
            ex.getMessage(),
            getStackTrace(ex)
        );
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ApiErrorResponse handleChatNotFoundException(ChatNotFoundException ex) {
        return new ApiErrorResponse(
            ex.getDescription(),
            ex.getCode(),
            ex.getClass().getName(),
            ex.getMessage(),
            getStackTrace(ex)
        );
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ApiErrorResponse handleLinkAlreadyAdded(LinkAlreadyAddedException ex) {
        return new ApiErrorResponse(
            ex.getDescription(),
            ex.getCode(),
            ex.getClass().getName(),
            ex.getMessage(),
            getStackTrace(ex)
        );
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ApiErrorResponse handleLinkNotFound(LinkNotFoundException ex) {
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
