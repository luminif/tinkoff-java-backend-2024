package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
import edu.java.bot.message.MessageProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotListener implements Bot {
    private TelegramBot telegramBot;
    private MessageProcessor messageProcessor;

    private SetMyCommands createMenu() {
        List<Command> commands = messageProcessor.commands();
        List<BotCommand> botCommands = new ArrayList<>();
        commands.forEach(command -> botCommands
            .add(new BotCommand(command.command(), command.description())));
        return new SetMyCommands(botCommands.toArray(new BotCommand[0]));
    }

    @Override
    public SendMessage process(Update update) {
        return messageProcessor.process(update);
    }

    @Override
    @Bean
    public void start() {
        telegramBot.execute(createMenu());
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(update -> telegramBot.execute(process(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
