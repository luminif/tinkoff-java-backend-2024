package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperWebClient;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperWebClient scrapperWebClient;
    private final Logger logger = LogManager.getLogger();

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "запускает бота";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        scrapperWebClient.registerChatRetry(chatId);
        logger.info("User with chatID %d has been successfully registered".formatted(chatId));
        return new SendMessage(chatId, "Вы зарегистрировались. Чтобы узнать доступные команды, используйте /help");
    }
}
