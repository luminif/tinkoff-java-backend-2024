package edu.java.bot.commands;

import edu.java.bot.models.User;
import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrackCommandList {
    @InjectMocks
    private TrackCommand trackCommand;

    @Test
    void command() {
        String actual = trackCommand.command();
        assertEquals("/track", actual);
    }

    @Test
    void description() {
        String actual = trackCommand.description();
        assertEquals("начать отслеживание", actual);
    }
}
