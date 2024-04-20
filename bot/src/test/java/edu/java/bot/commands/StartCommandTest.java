package edu.java.bot.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
        when(scrapperWebClient.registerChatRetry(chatId)).thenReturn("вы зарегались");
        String actual = (String)startCommand.handle(update).getParameters().get("text");
        assertEquals(actual, "Вы зарегистрировались. Чтобы узнать доступные команды, используйте /help");
    }
}
