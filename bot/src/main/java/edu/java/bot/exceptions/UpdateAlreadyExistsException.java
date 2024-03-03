package edu.java.bot.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UpdateAlreadyExistsException extends RuntimeException {
    private final String description;
    private final String code;

    public UpdateAlreadyExistsException(String message) {
        super(message);
        this.description = "Обновление уже добавлено";
        this.code = HttpStatus.BAD_REQUEST.toString();
    }
}
