package edu.java.bot.clients;

import edu.java.bot.api.components.AddLinkRequest;
import edu.java.bot.api.components.ApiErrorResponse;
import edu.java.bot.api.components.LinkResponse;
import edu.java.bot.api.components.ListLinksResponse;
import edu.java.bot.api.components.RemoveLinkRequest;
import edu.java.bot.exceptions.ApiErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperWebClient {
    private final WebClient webClient;
    @Value("${api.scrapper.base-url}")
    private String baseUrl;
    private static final String PATH_TO_CHAT = "/tg-chat/{id}";
    private static final String HEADER = "Tg-Chat-Id";
    private static final String PATH_TO_LINKS = "/links";

    public ScrapperWebClient() {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ScrapperWebClient(String baseUrl) {
        if (!baseUrl.isEmpty()) {
            this.baseUrl = baseUrl;
        }

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public String registerChat(Long id) {
        return webClient
            .post()
            .uri(PATH_TO_CHAT, id)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new ApiErrorException(errorResponse))))
            .bodyToMono(String.class)
            .block();
    }

    public String removeChat(Long id) {
        return webClient
            .delete()
            .uri(PATH_TO_CHAT, id)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new ApiErrorException(errorResponse))))
            .bodyToMono(String.class)
            .block();
    }

    public ListLinksResponse getLinks(Long id) {
        return webClient
            .get()
            .uri(PATH_TO_LINKS)
            .header(HEADER, String.valueOf(id))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new ApiErrorException(errorResponse))))
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public LinkResponse addLink(Long id, AddLinkRequest request) {
        return webClient
            .post()
            .uri(PATH_TO_LINKS)
            .header(HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new ApiErrorException(errorResponse))))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse removeLink(Long id, RemoveLinkRequest request) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(PATH_TO_LINKS)
            .header(HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new ApiErrorException(errorResponse))))
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
