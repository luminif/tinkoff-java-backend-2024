package edu.java.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest extends CommandTest {
    @InjectMocks
    private StartCommand startCommand;

    @Test
    void command() {
        String actual = startCommand.command();
        assertEquals("/start", actual);
    }

    @Test
    void description() {
        String actual = startCommand.description();
        assertEquals("запускает бота", actual);
    }

    @Test
    void successfulRegistration() {
        assertTrue(userService.register(chatId));
    }

    @Test
    void alreadyRegistered() {
        String actual = (String)startCommand.handle(update).getParameters().get("text");
        assertEquals("Вы уже зарегистрированы!", actual);
    }
}
