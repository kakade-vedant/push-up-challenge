package com.vedant.kakade.push.up.challenges.exception;

public class ItemNotFoundException extends Exception{
    public ItemNotFoundException(String className) {
        super(className + "'s item not found exception");
    }
}
