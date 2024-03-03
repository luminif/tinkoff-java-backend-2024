package edu.java.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LinkAlreadyAddedException extends RuntimeException {
    private final String description;
    private final String code;

    public LinkAlreadyAddedException(String message) {
        super(message);
        this.description = "Ссылка уже была добавлена";
        this.code = HttpStatus.BAD_REQUEST.toString();
    }
}
