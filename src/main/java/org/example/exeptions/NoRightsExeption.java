package org.example.exeptions;

public class NoRightsExeption extends RuntimeException {
    public NoRightsExeption(String message) {
        super(message);
    }
}
