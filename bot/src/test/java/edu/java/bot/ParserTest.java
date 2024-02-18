package edu.java.bot;

import edu.java.bot.parser.LinkParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    void invalidFormat() {
        LinkParser linkParser = new LinkParser(".");
        assertFalse(linkParser.checkLink());
    }

    @Test
    void correctFormat() {
        LinkParser linkParser = new LinkParser("https://github.com/sanyarnd/tinkoff-java-course-2023/");
        assertTrue(linkParser.checkLink());
    }
}
