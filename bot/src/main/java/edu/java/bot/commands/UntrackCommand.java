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
public class UntrackCommand implements Command {
    private final UserService userService;
    private final Logger logger = LogManager.getLogger();

    public UntrackCommand(UserService userService) {
        this.userService = userService;
    }

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
        Optional<User> user = userService.findChatById(chatId);

        if (user.isPresent()) {
            String[] splittedText = text.split("\\s+");

            if (splittedText.length == 1) {
                return new SendMessage(chatId, "Нужно ввести команду вида /untrack <link>");
            }

            String link = splittedText[1];
            List<String> links = user.get().getLinks();
            boolean containsLink = userService.deleteLink(new User(chatId, links), link);

            if (!containsLink) {
                logger.info("Resource %s has not been tracked before".formatted(link));
                return new SendMessage(chatId, "Ресурс %s ранее не отслеживался".formatted(link));
            }

            logger.info("Resource %s was successfully removed from the tracked".formatted(link));
            return new SendMessage(chatId, "Отслеживвание ресура %s прекращено".formatted(link));
        }

        return new SendMessage(chatId, "Для начала зарегистрируйтесь при помощи команды /start");
    }
}
