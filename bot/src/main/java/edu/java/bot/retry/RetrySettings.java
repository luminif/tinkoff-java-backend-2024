package edu.java.bot.retry;

import java.util.List;
import org.springframework.http.HttpStatus;

public record RetrySettings(
    RetryPolicy policy,
    int attempts,
    List<HttpStatus> statuses
) {
}
