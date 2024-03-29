package edu.java.bot.services;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.BotListener;
import edu.java.bot.api.components.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final BotListener bot;

    public void handle(LinkUpdateRequest linkUpdateRequest) {
        if (!linkUpdateRequest.description().isEmpty()) {
            linkUpdateRequest.tgChatIds().forEach(id -> {
                bot.execute(new SendMessage(
                    id,
                    "Обновление по ссылке:\n%s - %s"
                        .formatted(linkUpdateRequest.url(), linkUpdateRequest.description()))
                );
            });
        }
    }
}
