package edu.java.clients.handlers;

import edu.java.entities.Link;

public interface ClientHandler {
    boolean supports(String host);

    String getUpdate(Link link);
}
