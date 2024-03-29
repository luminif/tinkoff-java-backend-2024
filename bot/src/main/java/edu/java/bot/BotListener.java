package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.message.MessageProcessor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BotListener extends TelegramBot {
    private final MessageProcessor messageProcessor;

    public BotListener(ApplicationConfig config, MessageProcessor messageProcessor) {
        super(config.telegramToken());
        this.messageProcessor = messageProcessor;
        this.start();
    }

    private SetMyCommands createMenu() {
        List<Command> commands = messageProcessor.commands();
        List<BotCommand> botCommands = new ArrayList<>();
        commands.forEach(command -> botCommands.add(command.toApiCommand()));
        return new SetMyCommands(botCommands.toArray(new BotCommand[0]));
    }

    public SendMessage process(Update update) {
        return messageProcessor.process(update);
    }

    public void start() {
        execute(createMenu());
        setUpdatesListener(updates -> {
            updates.forEach(update -> execute(process(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
