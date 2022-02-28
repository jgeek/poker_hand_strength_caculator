package com.evolution.bootcamp.assignment.poker.exception;

public class BadFormatException extends RuntimeException {
    public BadFormatException(String type, String value) {
        super(String.format("%s is bad format %s", value, type));
    }
}
