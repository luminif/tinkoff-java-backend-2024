package edu.java.dao;

import edu.java.entities.Link;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JdbcLinkDao {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Link link, Long chatId) {
        if (!isUrlExists(link.getLink())) {
            jdbcTemplate
                .update("INSERT INTO links (link, updated_at) VALUES (?, ?)", link.getLink(), OffsetDateTime.now());
        }

        jdbcTemplate
            .update("INSERT INTO chat_links (link_id, chat_id) VALUES (?, ?)", findLinkIdByUrl(link), chatId);
    }

    @Transactional
    public void delete(Long linkId, Long chatId) {
        jdbcTemplate
            .update("DELETE FROM chat_links where link_id = ? AND chat_id = ?", linkId, chatId);

        List<Long> ids = jdbcTemplate
            .queryForList("SELECT chat_id FROM chat_links WHERE link_id = ?", Long.class, linkId);

        if (ids.isEmpty()) {
            jdbcTemplate.update("DELETE FROM links where id = ?", linkId);
        }
    }

    @Transactional
    public List<Link> findLinksById(Long chatId) {
        return jdbcTemplate
            .queryForList(
                """
                    SELECT link FROM links WHERE id IN
                    (SELECT link_id FROM chat_links WHERE chat_id = ?)
                    """,
                Link.class, chatId
            );
    }

    @Transactional
    public Long findLinkIdByUrl(Link link) {
        return jdbcTemplate
            .queryForObject("SELECT id FROM links where link = ?", Long.class, link.getLink());
    }

    @Transactional
    public List<Long> findIdsByLinkId(Long linkId) {
        return jdbcTemplate
            .queryForList("SELECT chat_id FROM chat_links where link_id = ?", Long.class, linkId);
    }

    @Transactional
    public List<Link> findOutdatedLinks(Long minutes) {
        OffsetDateTime delta = OffsetDateTime.now().minusMinutes(minutes);

        return jdbcTemplate
            .queryForList("SELECT link FROM links WHERE updated_at <= ?", Link.class, delta);
    }

    @Transactional
    public boolean isUrlExists(String link) {
        Integer count = jdbcTemplate
            .queryForObject("SELECT COUNT(*) FROM links WHERE link = ?", Integer.class, link);
        return count != null && count > 0;
    }

    @Transactional
    public void setLastUpdate(Long linkId) {
        jdbcTemplate
            .update("UPDATE links SET updated_at = CURRENT_TIMESTAMP WHERE id = ?", linkId);
    }

    @Transactional
    public OffsetDateTime getLastUpdate(String link) {
        return jdbcTemplate
            .queryForObject("SELECT updated_at FROM links where link = ?", OffsetDateTime.class, link);
    }
}
