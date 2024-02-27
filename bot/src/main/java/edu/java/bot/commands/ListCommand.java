package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;
import edu.java.bot.services.UserService;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final UserService userService;
    private final Logger logger = LogManager.getLogger();
    private final StringBuilder text = new StringBuilder();

    public ListCommand(UserService userService) {
        this.userService = userService;
    }

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
        Optional<User> user = userService.findChatById(chatId);

        if (user.isEmpty()) {
            return new SendMessage(chatId, "Для начала зарегистрируйтесь при помощи команды /start");
        }

        List<String> links = user.get().getLinks();

        if (links.isEmpty()) {
            logger.info("There are no tracked resources");
            return new SendMessage(chatId, "Нет отслеживаемых ресурсов");
        }

        text.setLength(0);
        text.append("Список отслеживаемых ресурсов: ").append("\n");
        links.forEach(link -> text.append(link).append("\n"));
        logger.info("List of resources has been successfully displayed");

        return new SendMessage(chatId, text.toString());
    }
}
