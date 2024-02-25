package edu.java.clients.stackoverflow;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverflowClient implements StackOverflowClientInterface {
    private final WebClient webClient;
    private String baseUrl = "https://api.stackexchange.com/2.3";

    public StackOverflowClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public StackOverflowClient(String baseUrl) {
        if (!baseUrl.isEmpty()) {
            this.baseUrl = baseUrl;
        }

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public StackOverflowResponse fetchQuestion(Long postId) {
        return webClient.get()
            .uri("/questions/{postId}&site=stackoverflow", postId)
            .retrieve().bodyToMono(StackOverflowResponse.class).block();
    }
}
