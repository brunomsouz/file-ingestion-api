package io.souz.fileingestionapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Error> abstractExceptionHandler(AbstractException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), ex.getHttpStatus());
    }

    public record Error(String message) { }

}
