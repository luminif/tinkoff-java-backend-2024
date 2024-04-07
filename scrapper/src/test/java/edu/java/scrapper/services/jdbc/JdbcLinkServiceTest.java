package edu.java.scrapper.services.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.entities.Chat;
import edu.java.entities.Link;
import edu.java.exceptions.LinkAlreadyAddedException;
import edu.java.exceptions.LinkNotFoundException;
import edu.java.scrapper.IntegrationTest;
import edu.java.services.LinkService;
import edu.java.services.jdbc.JdbcChatService;
import edu.java.services.jdbc.JdbcLinkService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JdbcLinkServiceTest extends IntegrationTest {
    private LinkService linkService;
    private List<Chat> chats;
    private List<Link> links;
    @Mock
    private JdbcChatService jdbcChatService;

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
            .create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build());
        JdbcLinkDao jdbcLinkDao = new JdbcLinkDao(jdbcTemplate);
        linkService = new JdbcLinkService(jdbcLinkDao, jdbcChatService);

        chats = List.of(
            new Chat(7L),
            new Chat(8L),
            new Chat(9L)
        );

        links = List.of(
            new Link("123"),
            new Link("124")
        );
    }

    private Link addLink(Chat chat) {
        return linkService.add(chat.getId(), links.get(0));
    }

    private Link removeLink(Chat chat, Link link) {
        return linkService.remove(chat.getId(), link);
    }

    @Test
    void successfulAddLink() {
        Link link = addLink(chats.get(0));
        assertEquals(links.get(0).getLink(), link.getLink());
    }

    @Test
    void unsuccessfulAddLink() {
        addLink(chats.get(1));
        assertThrows(LinkAlreadyAddedException.class, () -> addLink(chats.get(1)));
    }

    @Test
    void successfulRemoveLink() {
        Link link = removeLink(chats.get(0), links.get(0));
        assertEquals(links.get(0).getLink(), link.getLink());
    }

    @Test
    void unsuccessfulRemoveLink() {
        assertThrows(LinkNotFoundException.class, () -> removeLink(chats.get(1), links.get(1)));
    }

    @Test
    void findLinksById() {
        Link link = addLink(chats.get(2));
        List<Link> linkList = linkService.findLinksById(chats.get(2).getId());
        assertNotNull(linkList);
        assertEquals(link.getLink(), linkList.get(0).getLink());
    }
}
