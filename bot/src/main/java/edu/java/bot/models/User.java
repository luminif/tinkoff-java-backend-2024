package edu.java.bot.models;

import java.util.List;

public class User {
    private long chatId;
    private List<String> links;

    public User(long chatId, List<String> links) {
        this.chatId = chatId;
        this.links = links;
    }

    public long getChatId() {
        return chatId;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
