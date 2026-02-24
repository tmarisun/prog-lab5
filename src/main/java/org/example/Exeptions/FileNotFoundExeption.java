package org.example.Exeptions;

public class FileNotFoundExeption extends RuntimeException {
    public FileNotFoundExeption(String message) {
        super(message);
    }
}
