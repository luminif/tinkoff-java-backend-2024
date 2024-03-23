package edu.java.clients.handlers;

public interface ClientHandler {
    boolean supports(String host);

    String getUpdate(String link);
}
