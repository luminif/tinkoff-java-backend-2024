package edu.java.bot;

import edu.java.bot.parser.LinkParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @ParameterizedTest
    @ValueSource(strings = {"https://github.com/sanyarnd/tinkoff-java-course-2023/",
        "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"})
    void correctFormat(String link) {
        assertTrue(LinkParser.checkLink(link));
    }

    @ParameterizedTest
    @ValueSource(strings = {".", "12345", "/", "https://stackoverflow.com/search?q=unsupported%20link"})
    void invalidFormat(String link) {
        assertFalse(LinkParser.checkLink(link));
    }
}
