package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.api.components.LinkResponse;
import edu.java.bot.clients.ScrapperWebClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperWebClient scrapperWebClient;
    private final Logger logger = LogManager.getLogger();
    private final StringBuilder text = new StringBuilder();

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показывает список отслеживаемых ресурсов";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        List<LinkResponse> links = scrapperWebClient.getLinks(chatId).links();

        if (links.isEmpty()) {
            logger.info("There are no tracked resources");
            return new SendMessage(chatId, "Нет отслеживаемых ресурсов");
        }

        text.setLength(0);
        text.append("Список отслеживаемых ресурсов: ").append("\n");
        links.forEach(link -> text.append(link.url().toString()).append("\n"));
        logger.info("List of resources has been successfully displayed");

        return new SendMessage(chatId, text.toString());
    }
}
