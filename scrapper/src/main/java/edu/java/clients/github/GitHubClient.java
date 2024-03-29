package edu.java.clients.github;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient implements GitHubClientInterface {
    private final WebClient webClient;
    @Value("${api.github.base-url}")
    private String baseUrl;

    public GitHubClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public GitHubClient(String baseUrl) {
        if (!baseUrl.isEmpty()) {
            this.baseUrl = baseUrl;
        }

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
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
    public List<GitHubCommitResponse> fetchCommit(String name, String repository, OffsetDateTime date) {
        return webClient
            .get()
            .uri("/repos/{name}/{repository}/commits?since={date}", name, repository, date)
            .retrieve()
            .bodyToFlux(GitHubCommitResponse.class)
            .collectList()
            .block();
    }
}
