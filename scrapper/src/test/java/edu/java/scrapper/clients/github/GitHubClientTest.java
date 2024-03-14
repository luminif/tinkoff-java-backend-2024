package edu.java.scrapper.clients.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.java.clients.github.*;
import java.time.OffsetDateTime;

public class GitHubClientTest {
    private WireMockServer wireMockServer;
    private GitHubClientInterface gitHubClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        String baseUrl = "http://localhost:" + wireMockServer.port();
        gitHubClient = new GitHubClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void equalBodyTest() {
        String username = "luminif";
        String repository = "tinkoff-2024";
        String body = "{\"id\":1337,\"name\":\"luminif\",\"updated_at\":\"2024-02-24T18:00:00Z\",\"pushed_at\":\"2024-02-24T18:02:05Z\"}";

        stubFor(get(urlEqualTo("/repos/%s/%s".formatted(username, repository)))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body))
            );

        GitHubResponse gitHubResponse = gitHubClient.fetchRepository(username, repository);

        assertEquals(1337, gitHubResponse.id());
        assertEquals(username, gitHubResponse.name());
        assertEquals(OffsetDateTime.parse("2024-02-24T18:00:00Z"), gitHubResponse.updatedAt());
        assertEquals(OffsetDateTime.parse("2024-02-24T18:02:05Z"), gitHubResponse.pushedAt());
    }
}
