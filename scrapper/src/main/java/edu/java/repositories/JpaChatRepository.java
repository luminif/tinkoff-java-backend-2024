package edu.java.repositories;

import edu.java.entities.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    @Query(value = "SELECT chat_id FROM chat_links WHERE link_id = :linkId", nativeQuery = true)
    List<Long> findAllIdsByLinkId(Long linkId);
}
