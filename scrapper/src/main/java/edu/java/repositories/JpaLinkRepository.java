package edu.java.repositories;

import edu.java.entities.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    @Modifying
    @Query(value = "INSERT INTO chat_links (link_id, chat_id) VALUES (:linkId, :chatId)", nativeQuery = true)
    void add(Long linkId, Long chatId);

    @Modifying
    @Query(value = "DELETE FROM chat_links WHERE link_id = :linkId AND chat_id = :chatId", nativeQuery = true)
    void delete(Long linkId, Long chatId);

    @Modifying
    @Query(value =
        """
        SELECT l.* FROM links l JOIN chat_links cl ON l.id = cl.link_id JOIN chats c
        ON c.id = cl.chat_id WHERE c.id = :chatId
        """, nativeQuery = true)
    List<Link> findLinksById(Long chatId);

    @Modifying
    @Query(value = "SELECT id FROM links where link = :link", nativeQuery = true)
    Long findLinkIdByUrl(String link);

    @Modifying
    @Query(value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM links WHERE link = :link) THEN 1 ELSE 0 END AS url_exists",
           nativeQuery = true)
    boolean isUrlExists(String link);

    @Modifying
    @Query(value = "UPDATE links SET updated_at = CURRENT_TIMESTAMP WHERE id = :linkId", nativeQuery = true)
    void setLastUpdate(Long linkId);

    @Modifying
    @Query(value = "SELECT link FROM links WHERE updated_at <= CURRENT_TIMESTAMP - INTERVAL :minutes MINUTE",
           nativeQuery = true)
    List<Link> findOutdatedLinks(Long minutes);

    @Modifying
    @Query(value = "SELECT chat_id FROM chat_links where link_id = :linkId", nativeQuery = true)
    List<Long> findIdsByLinkId(Long linkId);
}
