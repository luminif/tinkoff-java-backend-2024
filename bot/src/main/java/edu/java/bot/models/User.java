package edu.java.bot.models;

import java.util.List;
import lombok.Getter;

@Getter
public class User {
    private final long chatId;
    private List<String> links;

    public User(long chatId, List<String> links) {
        this.chatId = chatId;
        this.links = links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
