package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor implements UserMessageProcessor {
    private final List<Command> commands;

    public MessageProcessor(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.message().chat().id();

        for (var command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }

        return new SendMessage(chatId, "Данная команда не поддерживается");
    }
}
