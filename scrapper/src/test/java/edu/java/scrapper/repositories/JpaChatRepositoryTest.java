package edu.java.scrapper.repositories;

import edu.java.entities.Chat;
import edu.java.repositories.JpaChatRepository;
import edu.java.repositories.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaChatRepositoryTest extends IntegrationTest {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;
    private Chat chat;

    @Autowired
    public JpaChatRepositoryTest(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @BeforeEach
    void setUp() {
        chat = new Chat(1337L);
        jpaChatRepository.save(chat);
    }

    @Test
    @Transactional
    @Rollback
    void registerTest() {
        Optional<Chat> savedChat = jpaChatRepository.findById(chat.getId());
        assertNotNull(savedChat);
    }

    @Test
    @Transactional
    @Rollback
    void unregisterTest() {
        jpaChatRepository.deleteById(1337L);
        Optional<Chat> deletedChat = jpaChatRepository.findById(chat.getId());
        assertTrue(deletedChat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        jpaLinkRepository.add(31L, 1337L);
        jpaLinkRepository.add(31L, 8138L);
        List<Long> ids = jpaChatRepository.findAllIdsByLinkId(31L);
        assertNotNull(ids);
        assertEquals(ids, List.of(1337L, 8138L));
    }
}
