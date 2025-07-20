package com.stacy.talentloop.Exceptions;

public class InvalidAuthTokenException extends RuntimeException {
    public InvalidAuthTokenException(String msg) {
        super(msg);
    }
}
