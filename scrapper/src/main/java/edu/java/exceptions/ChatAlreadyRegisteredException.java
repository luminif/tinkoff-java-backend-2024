package edu.java.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChatAlreadyRegisteredException extends RuntimeException {
    private final String description;
    private final String code;

    public ChatAlreadyRegisteredException(String message) {
        super(message);
        this.description = "Чат уже был зарегистрирован";
        this.code = HttpStatus.BAD_REQUEST.toString();
    }
}
