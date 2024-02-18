package edu.java.bot.parser;

public class LinkParser {
    private String link;

    public LinkParser(String link) {
        this.link = link;
    }

    public boolean checkLink() {
        return link.matches("https?://(github\\.com|stackoverflow\\.com)/.*");
    }
}
