package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;
    private StringBuilder text;

    private void addCommandsToText() {
        this.text = new StringBuilder();
        commands.forEach(command -> {
            text.append(command.command()).append(" ")
                .append(command.description()).append("\n");
        });
    }

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        addCommandsToText();
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "показывает список доступных команд";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), text.toString());
    }
}
