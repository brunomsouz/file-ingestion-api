package io.souz.fileingestionapi.exception;

import io.souz.fileingestionapi.util.MessageFormatter;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected AbstractException(String message, HttpStatus httpStatus) {
        super(MessageFormatter.getMessageForLocale(message));
        this.httpStatus = httpStatus;
    }

}
