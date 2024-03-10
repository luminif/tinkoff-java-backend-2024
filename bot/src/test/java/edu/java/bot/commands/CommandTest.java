package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import static org.mockito.Mockito.lenient;

public class CommandTest {
    protected final long chatId = 1337;

    @Spy
    protected UserService userService;

    @Mock
    protected Update update;

    @Mock
    protected Message message;

    @Mock
    protected Chat chat;

    @BeforeEach
    protected void setUp() {
        lenient().doReturn(message).when(update).message();
        lenient().doReturn(chat).when(message).chat();
        lenient().doReturn(chatId).when(chat).id();
        userService.register(chatId);
    }
}
