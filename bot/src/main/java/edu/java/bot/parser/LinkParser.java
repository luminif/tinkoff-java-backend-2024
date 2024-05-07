package edu.java.bot.parser;

public class LinkParser {
    private String link;

    public static boolean checkLink(String link) {
        return link.matches(
            "^https://(github.com/([a-zA-Z0-9_]+)/([a-zA-Z0-9_-]+)(/)?$"
                + "|stackoverflow.com/questions/[0-9]+/[a-zA-Z-]+(/)?$)"
        );
    }
}
