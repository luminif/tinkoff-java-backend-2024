package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;
import edu.java.bot.parser.LinkParser;
import edu.java.bot.services.UserService;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final UserService userService;
    private final Logger logger = LogManager.getLogger();

    public TrackCommand(UserService userService) {
        this.userService = userService;
    }

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
        Optional<User> user = userService.findChatById(chatId);

        if (user.isPresent()) {
            String[] splittedText = text.split("\\s+");

            if (splittedText.length == 1) {
                return new SendMessage(chatId, "Нужно ввести команду вида /track <link>");
            }

            String link = splittedText[1];
            LinkParser linkParser = new LinkParser(link);

            if (!linkParser.checkLink()) {
                logger.info("Resource %s is not supported".formatted(link));
                return new SendMessage(chatId, "Данный ресурс не поддерживается!");
            }

            List<String> links = user.get().getLinks();
            boolean linkIsAdded = userService.addLink(new User(chatId, links), link);
            String responseMessage;

            if (linkIsAdded) {
                logger.info("Resource %s is already being tracked".formatted(link));
                responseMessage = "Ресурс %s ранее был добавлен в список отслеживаемых".formatted(link);
            } else {
                responseMessage = "Ресурс %s добавлен в список отслеживаемых".formatted(link);
                logger.info("Resource %s is being successfully tracked".formatted(link));
            }

            return new SendMessage(chatId, responseMessage);
        }

        return new SendMessage(chatId, "Для начала зарегистрируйтесь при помощи команды /start");
    }
}
