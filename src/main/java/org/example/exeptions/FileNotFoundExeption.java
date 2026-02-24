package org.example.exeptions;

public class FileNotFoundExeption extends RuntimeException {
    public FileNotFoundExeption(String message) {
        super(message);
    }
}
