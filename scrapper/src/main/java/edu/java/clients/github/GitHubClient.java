package edu.java.clients.github;

import edu.java.configuration.RetryConfiguration;
import edu.java.retry.RetryPolicy;
import edu.java.retry.RetrySettings;
import io.github.resilience4j.retry.Retry;
import jakarta.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient implements GitHubClientInterface {
    private final WebClient webClient;
    @Value("${api.github.base-url}")
    private String baseUrl;
    @Value("${api.github.retry-policy}")
    private RetryPolicy policy;
    @Value("${api.github.attempts}")
    private int attempts;
    @Value("${api.github.statuses}")
    private List<HttpStatus> statuses;
    private Retry retry;

    public GitHubClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public GitHubClient(String baseUrl) {
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
    public GitHubResponse fetchRepository(String name, String repository) {
        return webClient
            .get()
            .uri("/repos/{name}/{repository}", name, repository)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }

    @Override
    public GitHubResponse fetchRepositoryRetry(String name, String repository) {
        return Retry.decorateSupplier(retry, () -> fetchRepository(name, repository)).get();
    }

    @Override
    public List<GitHubCommitResponse> fetchCommit(String name, String repository, OffsetDateTime date) {
        return webClient
            .get()
            .uri("/repos/{name}/{repository}/commits?since={date}", name, repository, date)
            .retrieve()
            .bodyToFlux(GitHubCommitResponse.class)
            .collectList()
            .block();
    }

    @Override
    public List<GitHubCommitResponse> fetchCommitRetry(String name, String repository, OffsetDateTime date) {
        return Retry.decorateSupplier(retry, () -> fetchCommit(name, repository, date)).get();
    }
}
