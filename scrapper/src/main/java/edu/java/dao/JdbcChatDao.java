package edu.java.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcChatDao {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Long id) {
        jdbcTemplate.update("INSERT INTO chats (id) VALUES (?)", id);
    }

    @Transactional
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM chats where id = ?", id);
    }

    @Transactional
    public List<Long> findById(Long chatId) {
        return jdbcTemplate
            .queryForList("SELECT id FROM chats WHERE id = ?", Long.class, chatId);
    }
}
