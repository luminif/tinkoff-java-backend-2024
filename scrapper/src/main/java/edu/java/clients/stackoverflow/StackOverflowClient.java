package edu.java.clients.stackoverflow;

import edu.java.configuration.RetryConfiguration;
import edu.java.retry.RetryPolicy;
import edu.java.retry.RetrySettings;
import io.github.resilience4j.retry.Retry;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverflowClient implements StackOverflowClientInterface {
    private final WebClient webClient;
    @Value("${api.stackoverflow.base-url}")
    private String baseUrl;
    @Value("${api.stackoverflow.retry-policy}")
    private RetryPolicy policy;
    @Value("${api.stackoverflow.attempts}")
    private int attempts;
    @Value("${api.stackoverflow.statuses}")
    private List<HttpStatus> statuses;
    private Retry retry;

    public StackOverflowClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public StackOverflowClient(String baseUrl) {
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

    @Override
    public StackOverflowResponse fetchQuestion(Long questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}?site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class).block();
    }

    @Override
    public StackOverflowResponse fetchQuestionRetry(Long questionId) {
        return Retry.decorateSupplier(retry, () -> fetchQuestion(questionId)).get();
    }

    @Override
    public StackOverflowResponse fetchNewAnswer(Long questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}/answers?order=desc&sort=creation&site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class).block();
    }

    @Override
    public StackOverflowResponse fetchNewAnswerRetry(Long questionId) {
        return Retry.decorateSupplier(retry, () -> fetchNewAnswer(questionId)).get();
    }
}
