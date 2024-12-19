package io.souz.fileingestionapi.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends AbstractException {

    public InternalServerError(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
