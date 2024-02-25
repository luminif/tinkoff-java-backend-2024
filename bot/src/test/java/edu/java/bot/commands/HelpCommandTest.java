package edu.java.bot.commands;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {
    @InjectMocks
    private HelpCommand helpCommand;

    @Test
    void command() {
        String actual = helpCommand.command();
        assertEquals("/help", actual);
    }

    @Test
    void description() {
        String actual = helpCommand.description();
        assertEquals("показывает список доступных команд", actual);
    }
}
