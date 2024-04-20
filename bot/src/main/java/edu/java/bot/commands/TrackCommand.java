package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.api.components.AddLinkRequest;
import edu.java.bot.api.components.LinkResponse;
import edu.java.bot.clients.ScrapperWebClient;
import edu.java.bot.parser.LinkParser;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperWebClient scrapperWebClient;
    private final Logger logger = LogManager.getLogger();

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String[] segments = text.split("\\s+");

        if (segments.length == 1) {
            return new SendMessage(chatId, "Нужно ввести команду вида /track <link>");
        }

        String link = segments[1];

        if (!LinkParser.checkLink(link)) {
            logger.info("Resource %s is not supported".formatted(link));
            return new SendMessage(chatId, "Данный ресурс не поддерживается!");
        }

        List<LinkResponse> links = scrapperWebClient.getLinksRetry(chatId).links();
        boolean linkIsAdded = links.stream().anyMatch(uri -> uri.url().toString().equals(link));
        String responseMessage;

        if (linkIsAdded) {
            logger.info("Resource %s is already being tracked".formatted(link));
            responseMessage = "Ресурс %s ранее был добавлен в список отслеживаемых".formatted(link);
        } else {
            LinkResponse response = scrapperWebClient.addLinkRetry(chatId, new AddLinkRequest(URI.create(link)));
            logger.info(response);

            responseMessage = "Ресурс %s добавлен в список отслеживаемых".formatted(link);
            logger.info("Resource %s is being successfully tracked".formatted(link));
        }

        return new SendMessage(chatId, responseMessage);
    }
}
