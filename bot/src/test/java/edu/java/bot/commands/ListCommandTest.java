package edu.java.bot.commands;

import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {
    UserService userService;

    @Test
    void command() {
        Command listCommand = new ListCommand(userService);
        String actual = listCommand.command();
        assertEquals("/list", actual);
    }

    @Test
    void description() {
        Command listCommand = new ListCommand(userService);
        String actual = listCommand.description();
        assertEquals("показывает список отслеживаемых ресурсов", actual);
    }
}
