package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.api.components.LinkResponse;
import edu.java.bot.api.components.RemoveLinkRequest;
import edu.java.bot.clients.ScrapperWebClient;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final ScrapperWebClient scrapperWebClient;
    private final Logger logger = LogManager.getLogger();

    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();

        String[] segments = text.split("\\s+");

        if (segments.length == 1) {
            return new SendMessage(chatId, "Нужно ввести команду вида /untrack <link>");
        }

        String link = segments[1];
        List<LinkResponse> links = scrapperWebClient.getLinksRetry(chatId).links();
        boolean containsLink = links.stream().anyMatch(uri -> uri.url().toString().equals(link));

        if (!containsLink) {
            logger.info("Resource %s has not been tracked before".formatted(link));
            return new SendMessage(chatId, "Ресурс %s ранее не отслеживался".formatted(link));
        }

        LinkResponse response = scrapperWebClient.removeLinkRetry(chatId, new RemoveLinkRequest(URI.create(link)));
        logger.info(response);

        logger.info("Resource %s was successfully removed from the tracked".formatted(link));
        return new SendMessage(chatId, "Отслеживвание ресура %s прекращено".formatted(link));
    }
}
