package edu.java.services;

import edu.java.entities.Chat;
import java.util.List;

public interface ChatService {
    void register(Chat chat);

    void unregister(Long chatId);

    void isChatExists(Long chatId);

    List<Long> findAllIdsByLinkId(Long linkId);
}
