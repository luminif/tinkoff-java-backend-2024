package edu.java.bot.configuration;

import edu.java.bot.retry.RetrySettings;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import java.time.Duration;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class RetryConfiguration {
    private static final int INTERVAL = 2;

    private RetryConfiguration() {

    }

    public static Retry config(RetrySettings settings) {
        RetryConfig config = switch (settings.policy()) {
            case CONSTANT -> constant(settings);
            case LINEAR -> linear(settings);
            case EXPONENTIAL -> exponential(settings);
        };

        return Retry.of("bot", config);
    }

    private static RetryConfig constant(RetrySettings settings) {
        return RetryConfig.custom()
            .maxAttempts(settings.attempts())
            .intervalFunction(IntervalFunction.of(
                Duration.ofSeconds(INTERVAL)
            ))
            .retryOnException(exception -> exception instanceof WebClientResponseException
                && settings.statuses().contains(((WebClientResponseException) exception).getStatusCode()))
            .build();
    }

    private static RetryConfig linear(RetrySettings settings) {
        return RetryConfig.custom()
            .maxAttempts(settings.attempts())
            .intervalFunction(IntervalFunction.of(
                Duration.ofSeconds(INTERVAL),
                backoff -> INTERVAL * backoff
            ))
            .retryOnException(exception -> exception instanceof WebClientResponseException
                && settings.statuses().contains(((WebClientResponseException) exception).getStatusCode()))
            .build();
    }

    private static RetryConfig exponential(RetrySettings settings) {
        return RetryConfig.custom()
            .maxAttempts(settings.attempts())
            .intervalFunction(IntervalFunction.ofExponentialBackoff(
                    IntervalFunction.DEFAULT_INITIAL_INTERVAL,
                    IntervalFunction.DEFAULT_MULTIPLIER
            ))
            .retryOnException(exception -> exception instanceof WebClientResponseException
                && settings.statuses().contains(((WebClientResponseException) exception).getStatusCode()))
            .build();
    }
}
