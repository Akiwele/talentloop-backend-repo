package com.stacy.talentloop.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String userDoesNotExist) {
        super(userDoesNotExist);
    }
}
