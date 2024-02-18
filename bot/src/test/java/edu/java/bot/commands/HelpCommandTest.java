package edu.java.bot.commands;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {
    @Test
    void command() {
        Command helpCommand = new HelpCommand(new ArrayList<>());
        String actual = helpCommand.command();
        assertEquals("/help", actual);
    }

    @Test
    void description() {
        Command helpCommand = new HelpCommand(new ArrayList<>());
        String actual = helpCommand.description();
        assertEquals("показывает список доступных команд", actual);
    }
}
