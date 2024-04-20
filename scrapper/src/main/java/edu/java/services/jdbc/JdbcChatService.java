package edu.java.services.jdbc;

import edu.java.dao.JdbcChatDao;
import edu.java.entities.Chat;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.services.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatDao jdbcChatDao;

    @Override
    public void register(Chat chat) {
        if (!jdbcChatDao.findById(chat.getId()).isEmpty()) {
            throw new ChatAlreadyRegisteredException("Чат уже зарегистрирован");
        }

        jdbcChatDao.add(chat.getId());
    }

    @Override
    public void unregister(Chat chat) {
        isChatExists(chat.getId());
        jdbcChatDao.delete(chat.getId());
    }

    @Override
    public void isChatExists(Long chatId) {
        if (jdbcChatDao.findById(chatId).isEmpty()) {
            throw new ChatNotFoundException("Чат не найден");
        }
    }

    @Override
    public List<Long> findAllIdsByLinkId(Long linkId) {
        return jdbcChatDao.findAllIdsByLinkId(linkId);
    }
}
