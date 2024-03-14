package edu.java.bot.parser;

public class LinkParser {
    private String link;

    public static boolean checkLink(String link) {
        return link.matches("https?://(github\\.com|stackoverflow\\.com)/.*");
    }
}
