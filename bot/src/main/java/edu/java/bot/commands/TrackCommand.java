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

        String[] split = text.split("\\s+");

        if (split.length == 1) {
            return new SendMessage(chatId, "Нужно ввести команду вида /track <link>");
        }

        String link = split[1];
        LinkParser linkParser = new LinkParser(link);

        if (!linkParser.checkLink()) {
            logger.info("Resource %s is not supported".formatted(link));
            return new SendMessage(chatId, "Данный ресурс не поддерживается!");
        }

        Optional<User> initiator = userService.findChatById(chatId);
        List<String> links;

        if (initiator.isPresent()) {
            links = initiator.get().getLinks();
            boolean add = userService.addLink(new User(chatId, links), link);

            if (add) {
                logger.info("Resource %s is already being tracked".formatted(link));
                return new SendMessage(chatId, "Ресурс %s ранее был добавлен в список отслеживаемых".formatted(link));
            }
        }

        logger.info("Resource %s is being successfully tracked".formatted(link));
        return new SendMessage(chatId, "Ресурс %s добавлен в список отслеживаемых".formatted(link));
    }
}
