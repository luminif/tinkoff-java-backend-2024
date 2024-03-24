package edu.java.bot.services;

import edu.java.bot.Bot;
import edu.java.bot.api.components.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final Bot bot;

    public void handle(LinkUpdateRequest linkUpdateRequest) {
        linkUpdateRequest.tgChatIds().forEach(id -> {
            bot.execute(id, "Обновление по ссылке:\n%s\n%s"
                .formatted(linkUpdateRequest.url(), linkUpdateRequest.description()));
        });
    }
}
