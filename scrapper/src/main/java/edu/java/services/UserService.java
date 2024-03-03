package edu.java.services;

import edu.java.api.components.LinkResponse;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotFoundException;
import edu.java.exceptions.LinkAlreadyAddedException;
import edu.java.exceptions.LinkNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final HashMap<Long, List<LinkResponse>> chat = new HashMap<>();

    public void registerChat(long chatId) {
        if (chat.containsKey(chatId)) {
            throw new ChatAlreadyRegisteredException("Чат уже зарегистрирован");
        }

        chat.put(chatId, new ArrayList<>());
    }

    public void removeChat(long chatId) {
        checkContainsChat(chatId);
        chat.remove(chatId);
    }

    public List<LinkResponse> getLinks(long chatId) {
        checkContainsChat(chatId);
        return chat.get(chatId);
    }

    public LinkResponse addLink(long chatId, URI link) {
        checkContainsChat(chatId);
        List<LinkResponse> linkResponses = chat.get(chatId);

        for (var linkResponse : linkResponses) {
            if (linkResponse.url().equals(link)) {
                throw new LinkAlreadyAddedException("Ссылка уже добавлена в список отслеживаемых");
            }
        }

        LinkResponse linkResponse = new LinkResponse(chatId, link);
        linkResponses.add(linkResponse);
        return linkResponse;
    }

    public LinkResponse removeLink(long chatId, URI link) {
        checkContainsChat(chatId);
        List<LinkResponse> linkResponses = chat.get(chatId);

        for (var linkResponse : linkResponses) {
            if (linkResponse.url().equals(link)) {
                linkResponses.remove(linkResponse);
                return linkResponse;
            }
        }

        throw new LinkNotFoundException("Ссылка не найдена");
    }

    private void checkContainsChat(long chatId) {
        if (!chat.containsKey(chatId)) {
            throw new ChatNotFoundException("Чат не найден");
        }
    }
}
