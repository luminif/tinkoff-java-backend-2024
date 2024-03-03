package edu.java.exceptions;

import edu.java.api.components.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiErrorException extends RuntimeException {
    private final ApiErrorResponse apiErrorException;
}
