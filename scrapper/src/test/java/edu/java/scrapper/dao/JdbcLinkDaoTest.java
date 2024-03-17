package edu.java.scrapper.dao;

import edu.java.dao.JdbcLinkDao;
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

public class JdbcLinkDaoTest extends IntegrationTest {
    private JdbcLinkDao jdbcLinkDao;

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder
            .create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build());
        jdbcLinkDao = new JdbcLinkDao(jdbcTemplate);
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        jdbcLinkDao.add("123", 7L);
        List<String> links = jdbcLinkDao.findLinksById(7L);
        assertFalse(links.isEmpty());
        assertEquals("123", links.get(0));
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        jdbcLinkDao.delete(1L, 7L);
        List<String> links = jdbcLinkDao.findLinksById(7L);
        assertTrue(links.isEmpty());
    }
}
