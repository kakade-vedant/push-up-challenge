package com.vedant.kakade.push.up.challenges.exception;

public class IdModificationException extends Exception {
    public IdModificationException(String className) {
        super( className + "'s Id Modification Exception.");
    }
}
