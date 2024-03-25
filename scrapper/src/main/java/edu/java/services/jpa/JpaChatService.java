package edu.java.services.jpa;

import edu.java.entities.Chat;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.repositories.JpaChatRepository;
import edu.java.services.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;

    @Override
    public void register(Chat chat) {
        if (jpaChatRepository.existsById(chat.getId())) {
            throw new ChatAlreadyRegisteredException("Чат уже зарегистрирован");
        }

        jpaChatRepository.save(chat);
    }

    @Override
    public void unregister(Long chatId) {
        jpaChatRepository.deleteById(chatId);
    }

    @Override
    public void isChatExists(Long chatId) {
        if (!jpaChatRepository.existsById(chatId)) {
            throw new ChatNotFoundException("Чат не найден");
        }
    }

    @Override
    public List<Long> findAllIdsByLinkId(Long linkId) {
        return jpaChatRepository.findAllIdsByLinkId(linkId);
    }
}
