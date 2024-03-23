package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.api.components.LinkUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    private TelegramBot telegramBot;

    //TODO
    public void handle(LinkUpdateRequest linkUpdateRequest) {
        linkUpdateRequest.tgChatIds().forEach(id -> {
            telegramBot.execute(new SendMessage(
                    id,
                    "Обновление по ссылке:\n%s\n%s".formatted(linkUpdateRequest.url(), linkUpdateRequest.description()))
                );
        });
    }
}
