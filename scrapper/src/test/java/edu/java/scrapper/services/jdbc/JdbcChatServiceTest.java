package edu.java.scrapper.services.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.entities.Chat;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.scrapper.IntegrationTest;
import edu.java.services.ChatService;
import edu.java.services.jdbc.JdbcChatService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcChatServiceTest extends IntegrationTest {
    private ChatService chatService;
    private Chat chat;

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
            .create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build());
        JdbcChatDao jdbcChatDao = new JdbcChatDao(jdbcTemplate);
        chatService = new JdbcChatService(jdbcChatDao);
        chat = new Chat(7L);
    }

    private void register() {
        chatService.register(chat);
    }

    private void unregister(Chat chat) {
        chatService.unregister(chat);
    }

    @Test
    void successfulRegister() {
        assertDoesNotThrow(this::register);
    }

    @Test
    void unsuccessfulRegister() {
        assertThrows(ChatAlreadyRegisteredException.class, this::register);
    }

    @Test
    void successfulUnregister() {
        register();
        assertDoesNotThrow(() -> unregister(chat));
    }

    @Test
    void unsuccessfulUnregister() {
        assertThrows(ChatNotFoundException.class, () -> unregister(chat));
    }

    @Test
    void test() {
        List<Long> ids = chatService.findAllIdsByLinkId(1L);
        assertTrue(ids.isEmpty());
    }
}
