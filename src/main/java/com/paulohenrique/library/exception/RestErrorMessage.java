package com.paulohenrique.library.exception;

public class RestErrorMessage extends RuntimeException {
    public RestErrorMessage(String message) {
        super(message);
    }
}
