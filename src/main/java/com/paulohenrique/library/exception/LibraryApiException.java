package com.paulohenrique.library.exception;

import org.springframework.http.HttpStatus;

public class LibraryApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public LibraryApiException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
