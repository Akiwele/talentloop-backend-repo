package com.stacy.talentloop.Exceptions;

public class ExpiredAuthTokenException extends RuntimeException {
    public ExpiredAuthTokenException(String msg) {
        super(msg);
    }
}
