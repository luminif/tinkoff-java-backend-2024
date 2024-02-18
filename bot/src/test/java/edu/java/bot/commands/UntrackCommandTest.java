package edu.java.bot.commands;

import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackCommandTest {
    private UserService userService;

    @Test
    void command() {
        Command untrackCommand = new UntrackCommand(userService);
        String actual = untrackCommand.command();
        assertEquals("/untrack", actual);
    }

    @Test
    void description() {
        Command untrackCommand = new UntrackCommand(userService);
        String actual = untrackCommand.description();
        assertEquals("прекратить отслеживание", actual);
    }
}
