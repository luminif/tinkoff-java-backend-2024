package edu.java.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChatNotFoundException extends RuntimeException {
    private final String description;
    private final String code;

    public ChatNotFoundException(String message) {
        super(message);
        this.description = "Чат не существует";
        this.code = HttpStatus.BAD_REQUEST.toString();
    }
}
