package edu.java.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LinkNotFoundException extends RuntimeException {
    private final String description;
    private final String code;

    public LinkNotFoundException(String message) {
        super(message);
        this.description = "Такой ссылки нет";
        this.code = HttpStatus.BAD_REQUEST.toString();
    }
}
