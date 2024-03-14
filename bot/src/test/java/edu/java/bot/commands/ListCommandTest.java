package edu.java.bot.commands;

import edu.java.bot.models.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest extends CommandTest {
    @InjectMocks
    private ListCommand listCommand;

    @Test
    void command() {
        String actual = listCommand.command();
        assertEquals("/list", actual);
    }

    @Test
    void description() {
        String actual = listCommand.description();
        assertEquals("показывает список отслеживаемых ресурсов", actual);
    }

    @Test
    void emptyLinkList() {
        String actual = (String)listCommand.handle(update).getParameters().get("text");
        assertEquals("Нет отслеживаемых ресурсов", actual);
    }

    @Test
    void nonEmptyList() {
        userService.addLink(new User(chatId, List.of()), "github.com/luminif");
        String actual = (String)listCommand.handle(update).getParameters().get("text");
        assertEquals("Список отслеживаемых ресурсов: \ngithub.com/luminif\n", actual);
    }
}
