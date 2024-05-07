package edu.java.scrapper;

import java.time.OffsetDateTime;
import edu.java.entities.Chat;
import edu.java.entities.Link;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunMigrationTest extends IntegrationTest {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
        .create()
        .url(POSTGRES.getJdbcUrl())
        .username(POSTGRES.getUsername())
        .password(POSTGRES.getPassword())
        .build());

    @Test
    void test() {
        Chat chat = new Chat(23137L);
        Link link = new Link("1230");
        link.setId(91L);
        link.setUpdatedAt(OffsetDateTime.parse("2024-03-10T18:57:00Z"));

        jdbcTemplate.update("INSERT INTO chats (id) VALUES (?)", chat.getId());
        jdbcTemplate.update("INSERT INTO links (link, updated_at) VALUES (?, ?)", link.getLink(), link.getUpdatedAt());
        jdbcTemplate.update("INSERT INTO chat_links (link_id, chat_id) VALUES (?, ?)", link.getId(), chat.getId());

        Long actualChatId = jdbcTemplate.queryForObject("SELECT id FROM chats WHERE id = ?", Long.class, chat.getId());
        Long actualLinkId = jdbcTemplate.queryForObject("SELECT link_id FROM chat_links WHERE link_id = ?", Long.class, link.getId());
        String actualLink = jdbcTemplate.queryForObject("SELECT link FROM links WHERE link = ?", String.class, link.getLink());
        OffsetDateTime actualUpdate =
            jdbcTemplate.queryForObject("SELECT updated_at FROM links WHERE updated_at = ?", OffsetDateTime.class, link.getUpdatedAt());

        assertEquals(chat.getId(), actualChatId);
        assertEquals(link.getLink(), actualLink);
        assertEquals(link.getId(), actualLinkId);
        assertEquals(link.getUpdatedAt(), actualUpdate);
    }
}
