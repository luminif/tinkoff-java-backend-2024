package edu.java.bot.commands;

import edu.java.bot.api.components.LinkResponse;
import edu.java.bot.api.components.ListLinksResponse;
import edu.java.bot.models.User;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        when(scrapperWebClient.getLinksRetry(chatId)).thenReturn(new ListLinksResponse(new ArrayList<>(), 0));
        String actual = (String)listCommand.handle(update).getParameters().get("text");
        assertEquals("Нет отслеживаемых ресурсов", actual);
    }

    @Test
    void nonEmptyList() {
        when(scrapperWebClient.getLinksRetry(chatId))
            .thenReturn(new ListLinksResponse(List.of(new LinkResponse(7L, URI.create("github.com/luminif"))), 1));
        String actual = (String)listCommand.handle(update).getParameters().get("text");
        assertEquals("Список отслеживаемых ресурсов: \ngithub.com/luminif\n", actual);
    }
}
