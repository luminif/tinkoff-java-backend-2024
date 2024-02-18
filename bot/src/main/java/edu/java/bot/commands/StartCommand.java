package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final UserService userService;
    private final Logger logger = LogManager.getLogger();

    public StartCommand(UserService userService) {
        this.userService = userService;
    }

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
        var text = new StringBuilder();
        long chatId = update.message().chat().id();

        if (userService.register(chatId)) {
            text.append("Вы уже зарегистрированы!");
            logger.info("User with chatID %d has already registered".formatted(chatId));
        } else {
            text.append("Вы зарегистрировались. Чтобы узнать доступные команды, напишите /help");
            logger.info("User with chatID %d has been successfully registered".formatted(chatId));
        }

        return new SendMessage(chatId, text.toString());
    }
}
