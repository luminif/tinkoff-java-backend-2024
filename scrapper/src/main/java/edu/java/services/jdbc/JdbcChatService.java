package edu.java.services.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatDao jdbcChatDao;

    @Override
    public void register(Long chatId) {
        if (!jdbcChatDao.findById(chatId).isEmpty()) {
            throw new ChatAlreadyRegisteredException("Чат уже зарегистрирован");
        }

        jdbcChatDao.add(chatId);
    }

    @Override
    public void unregister(Long chatId) {
        isChatExists(chatId);
        jdbcChatDao.delete(chatId);
    }

    public void isChatExists(Long chatId) {
        if (jdbcChatDao.findById(chatId).isEmpty()) {
            throw new ChatNotFoundException("Чат не найден");
        }
    }
}
