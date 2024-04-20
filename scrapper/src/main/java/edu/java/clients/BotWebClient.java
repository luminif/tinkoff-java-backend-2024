package edu.java.clients;

import edu.java.api.components.ApiErrorResponse;
import edu.java.api.components.LinkUpdateRequest;
import edu.java.configuration.RetryConfiguration;
import edu.java.exceptions.ApiErrorException;
import edu.java.retry.RetryPolicy;
import edu.java.retry.RetrySettings;
import io.github.resilience4j.retry.Retry;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotWebClient {
    private final WebClient webClient;
    @Value("${api.bot.base-url}")
    private String baseUrl;
    @Value("${api.bot.retry-policy}")
    private RetryPolicy policy;
    @Value("${api.bot.attempts}")
    private int attempts;
    @Value("${api.bot.statuses}")
    private List<HttpStatus> statuses;
    private Retry retry;

    public BotWebClient() {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public BotWebClient(String baseUrl) {
        if (!baseUrl.isEmpty()) {
            this.baseUrl = baseUrl;
        }

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @PostConstruct
    public void configureRetryPolicy() {
        RetrySettings settings = new RetrySettings(
            policy,
            attempts,
            statuses
        );

        retry = RetryConfiguration.config(settings);
    }

    public String sendUpdate(LinkUpdateRequest request) {
        return webClient
            .post()
            .uri("/updates")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new ApiErrorException(errorResponse))))
            .bodyToMono(String.class)
            .block();
    }

    public String sendUpdateRetry(LinkUpdateRequest request) {
        return Retry.decorateSupplier(retry, () -> sendUpdate(request)).get();
    }
}
