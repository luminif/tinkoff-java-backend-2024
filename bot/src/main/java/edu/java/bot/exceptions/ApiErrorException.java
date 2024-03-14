package edu.java.bot.exceptions;

import edu.java.bot.api.components.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiErrorException extends RuntimeException {
    private final ApiErrorResponse apiErrorException;
}
