package edu.java.bot.services;

import edu.java.bot.models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final HashMap<Long, User> users = new HashMap<>();

    public Optional<User> findChatById(long chatId) {
        if (users.containsKey(chatId)) {
            return Optional.of(users.get(chatId));
        }

        return Optional.empty();
    }

    public boolean register(long chatId) {
        if (!users.containsKey(chatId)) {
            addUser(new User(chatId, new ArrayList<>()));
            return false;
        }

        return true;
    }

    public boolean addLink(User user, String link) {
        List<String> links = new ArrayList<>(user.getLinks());

        if (!links.contains(link)) {
            links.add(link);
            updateLinks(user, links);
            return false;
        }

        return true;
    }

    public boolean deleteLink(User user, String link) {
        List<String> links = new ArrayList<>(user.getLinks());

        if (links.contains(link)) {
            links.remove(link);
            updateLinks(user, links);
            return true;
        }

        return false;
    }

    private void addUser(User user) {
        users.put(user.getChatId(), user);
    }

    private void updateLinks(User user, List<String> links) {
        user.setLinks(links);
        addUser(user);
    }
}
