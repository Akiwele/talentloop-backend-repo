package com.stacy.talentloop.Exceptions;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException(String msg){
        super(msg);
    }
}
