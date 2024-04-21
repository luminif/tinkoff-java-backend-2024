package edu.java.scrapper.repositories;

import edu.java.entities.Chat;
import edu.java.entities.Link;
import edu.java.repositories.JpaChatRepository;
import edu.java.repositories.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaLinkRepositoryTest extends IntegrationTest {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;
    private Chat chat;
    private Link link;

    @Autowired
    public JpaLinkRepositoryTest(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @BeforeEach
    void setUp() {
        link = new Link("123");
        chat = new Chat(57217L);
        jpaChatRepository.save(chat);
        link.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    @Transactional
    void addTest() {
        Link addLink = jpaLinkRepository.save(link);
        jpaLinkRepository.add(link.getId(), chat.getId());
        List<Link> links = jpaLinkRepository.findLinksById(chat.getId());
        assertEquals(links.size(), 1);
        assertEquals(links.get(0), addLink);
    }

    @Test
    @Transactional
    void removeTest() {
        jpaLinkRepository.delete(link);
        jpaLinkRepository.delete(link.getId(), chat.getId());
        List<Link> links = jpaLinkRepository.findLinksById(chat.getId());
        assertEquals(links.size(), 0);
    }
}
