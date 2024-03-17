package edu.java.clients.handlers;

public interface ClientHandler {
    String EMPTY = "";

    boolean supports(String host);

    String getUpdate(String link);
}
