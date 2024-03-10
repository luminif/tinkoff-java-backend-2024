package edu.java.scrapper;

import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunMigrationTest extends IntegrationTest {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
        .create()
        .url(POSTGRES.getJdbcUrl())
        .username(POSTGRES.getUsername())
        .password(POSTGRES.getPassword())
        .build());

    @Test
    void test() {
        long chatId = 7;
        long linkId = 1;
        String link = "123";
        OffsetDateTime updatedAt = OffsetDateTime.parse("2024-03-10T18:57:00Z");

        jdbcTemplate.update("INSERT INTO chats (id) VALUES (?)", chatId);
        jdbcTemplate.update("INSERT INTO links (link, id, updated_at) VALUES (?, ?, ?)", link, linkId, updatedAt);
        jdbcTemplate.update("INSERT INTO chat_links (link_id, chat_id) VALUES (?, ?)", linkId, chatId);

        Long actualChatId = jdbcTemplate.queryForObject("SELECT id FROM chats WHERE id = ?", Long.class, chatId);
        Long actualLinkId = jdbcTemplate.queryForObject("SELECT link_id FROM chat_links WHERE link_id = ?", Long.class, linkId);
        String actualLink = jdbcTemplate.queryForObject("SELECT link FROM links WHERE link = ?", String.class, link);
        OffsetDateTime actualUpdatedAt =
            jdbcTemplate.queryForObject("SELECT updated_at FROM links WHERE updated_at = ?", OffsetDateTime.class, updatedAt);

        assertEquals(chatId, actualChatId);
        assertEquals(link, actualLink);
        assertEquals(linkId, actualLinkId);
        assertEquals(updatedAt, actualUpdatedAt);
    }
}
