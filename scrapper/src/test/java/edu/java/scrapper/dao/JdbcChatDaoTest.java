package edu.java.scrapper.dao;

import edu.java.dao.JdbcChatDao;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcChatDaoTest extends IntegrationTest {
    private JdbcChatDao jdbcChatDao;

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
            .create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build());
        jdbcChatDao = new JdbcChatDao(jdbcTemplate);
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        jdbcChatDao.add(8L);
        List<Long> ids = jdbcChatDao.findById(8L);
        assertFalse(ids.isEmpty());
        assertEquals(8L, ids.get(0));
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        jdbcChatDao.add(10L);
        jdbcChatDao.delete(10L);
        List<Long> ids = jdbcChatDao.findById(10L);
        assertTrue(ids.isEmpty());
    }
}
