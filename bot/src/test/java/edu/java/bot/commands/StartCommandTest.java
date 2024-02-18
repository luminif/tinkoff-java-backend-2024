package edu.java.bot.commands;

import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartCommandTest {
    private UserService userService;

    @Test
    void command() {
        Command startCommand = new StartCommand(userService);
        String actual = startCommand.command();
        assertEquals("/start", actual);
    }

    @Test
    void description() {
        Command startCommand = new StartCommand(userService);
        String actual = startCommand.description();
        assertEquals("запускает бота", actual);
    }
}
