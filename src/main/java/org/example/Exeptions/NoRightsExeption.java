package org.example.Exeptions;

public class NoRightsExeption extends RuntimeException {
    public NoRightsExeption(String message) {
        super(message);
    }
}
