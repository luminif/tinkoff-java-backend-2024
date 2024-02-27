package edu.java.clients.github;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubClient implements GitHubClientInterface {
    private final WebClient webClient;
    private String baseUrl = "https://api.github.com";

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
        return webClient.get()
            .uri("/repos/{name}/{repository}", name, repository)
            .retrieve().bodyToMono(GitHubResponse.class).block();
    }
}
