package edu.java.scrapper.clients.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubClientInterface;
import edu.java.clients.github.GitHubResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowClientInterface;
import edu.java.clients.stackoverflow.StackOverflowResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackOverflowClientTest {
    private WireMockServer wireMockServer;
    private StackOverflowClientInterface stackOverflowClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        String baseUrl = "http://localhost:" + wireMockServer.port();
        stackOverflowClient = new StackOverflowClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void equalBodyTest() {
        String username = "luminif";
        long postId = 1337;
        String body = "{\"name\":\"luminif\",\"last_activity_date\":\"2024-02-24T18:20:00Z\",\"post_id\":1337}";

        stubFor(get(urlEqualTo("/questions/%d&site=stackoverflow".formatted(postId)))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body))
        );

        StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(postId);

        assertEquals(username, stackOverflowResponse.name());
        assertEquals(OffsetDateTime.parse("2024-02-24T18:20:00Z"), stackOverflowResponse.lastActivityDate());
        assertEquals(postId, stackOverflowResponse.postId());
    }
}
