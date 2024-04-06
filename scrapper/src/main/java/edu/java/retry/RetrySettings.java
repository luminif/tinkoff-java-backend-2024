package edu.java.retry;

import java.util.List;
import org.springframework.http.HttpStatus;

public record RetrySettings(
    RetryPolicy policy,
    int attempts,
    List<HttpStatus> statuses
) {
}
