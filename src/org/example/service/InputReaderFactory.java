package org.example.service;

import java.util.Scanner;


public final class InputReaderFactory {

    private InputReaderFactory() {
    }

    /**
     * Если путь к файлу задан, возвращает JSON-ридер, иначе консольный ридер.
     */
    public static InputReader createReader(String filePath, Scanner scanner) {
        if (filePath != null && !filePath.trim().isEmpty()) {
            return new JsonFileInputReader(filePath.trim());
        }
        return new ConsoleInputReader(scanner);
    }
}

