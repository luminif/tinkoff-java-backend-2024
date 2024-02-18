package edu.java.bot.commands;

import edu.java.bot.services.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackCommandList {
    private UserService userService;

    @Test
    void command() {
        Command trackCommand = new TrackCommand(userService);
        String actual = trackCommand.command();
        assertEquals("/track", actual);
    }

    @Test
    void description() {
        Command trackCommand = new TrackCommand(userService);
        String actual = trackCommand.description();
        assertEquals("начать отслеживание", actual);
    }
}
