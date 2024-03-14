package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.api.components.LinkUpdateRequest;
import edu.java.clients.BotWebClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotWebClientTest {
    private WireMockServer wireMockServer;
    private BotWebClient botWebClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        String baseUrl = "http://localhost:" + wireMockServer.port();
        botWebClient = new BotWebClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void update() throws URISyntaxException {
        String body = "Обновление обработано";

        wireMockServer.stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)));

        String actual = botWebClient.sendUpdate(new LinkUpdateRequest(
            7L,
            new URI("github.com"),
            "",
            List.of(7L, 1337L)
        ));

        assertEquals("Обновление обработано", actual);
    }
}
