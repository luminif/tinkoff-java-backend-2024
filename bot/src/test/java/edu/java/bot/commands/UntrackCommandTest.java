package edu.java.bot.commands;

import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UntrackCommandTest {
    @InjectMocks
    private UntrackCommand untrackCommand;

    @Test
    void command() {
        String actual = untrackCommand.command();
        assertEquals("/untrack", actual);
    }

    @Test
    void description() {
        String actual = untrackCommand.description();
        assertEquals("прекратить отслеживание", actual);
    }
}
