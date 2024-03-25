package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.api.components.AddLinkRequest;
import edu.java.bot.api.components.LinkResponse;
import edu.java.bot.api.components.ListLinksResponse;
import edu.java.bot.api.components.RemoveLinkRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class ScrapperWebClientTest {
    private WireMockServer wireMockServer;
    private ScrapperWebClient scrapperWebClient;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        String baseUrl = "http://localhost:" + wireMockServer.port();
        scrapperWebClient = new ScrapperWebClient(baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void registerChat() {
        String body = "Чат зарегистрирован";

        wireMockServer.stubFor(post(urlEqualTo("/tg-chat/7"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)));

        String actual = scrapperWebClient.registerChat(7L);

        assertEquals(body, actual);
    }

    @Test
    void removeChat() {
        String body = "Чат успешно удалён";

        wireMockServer.stubFor(delete(urlEqualTo("/tg-chat/7"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)));

        String actual = scrapperWebClient.removeChat(7L);

        assertEquals(body, actual);
    }

    @Test
    void getLinks() {
        String body = """
            {
                "links": [
                    {
                      "id": 7,
                      "url": "github.com"
                    }
                  ],
                  "size": 1
            }

            """;

        wireMockServer.stubFor(get(urlEqualTo("/links"))
            .withHeader("Tg-Chat-Id", equalTo("7"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)));

        ListLinksResponse actual = scrapperWebClient.getLinks(7L);

        assertEquals(1, actual.size());
        assertEquals("github.com", actual.links().get(0).url().toString());
    }

    @Test
    void removeLink() throws URISyntaxException {
        String body = """
            {
                "id": 7,
                "url": "github.com"
            }

            """;

        wireMockServer.stubFor(delete(urlEqualTo("/links"))
            .withHeader("Tg-Chat-Id", equalTo("7"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)));

        LinkResponse actual = scrapperWebClient.removeLink(7L, new RemoveLinkRequest(new URI("github.com")));

        assertEquals(7, actual.id());
        assertEquals("github.com", actual.url().toString());
    }
}
