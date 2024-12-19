package io.souz.fileingestionapi.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
