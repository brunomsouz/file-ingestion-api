package io.souz.fileingestionapi.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
