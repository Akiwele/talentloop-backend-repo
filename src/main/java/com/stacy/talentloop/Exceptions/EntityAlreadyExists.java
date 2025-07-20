package com.stacy.talentloop.Exceptions;

public class EntityAlreadyExists extends RuntimeException {
    public EntityAlreadyExists(String msg){
        super(msg);
    }
}
