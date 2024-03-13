package com.manning.sbip.ch04.exception;

public class InvalidVerificationCode extends RuntimeException{
    public InvalidVerificationCode() {
    }

    public InvalidVerificationCode(String message) {
        super(message);
    }

    public InvalidVerificationCode(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVerificationCode(Throwable cause) {
        super(cause);
    }

    public InvalidVerificationCode(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
