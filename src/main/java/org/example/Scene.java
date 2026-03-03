package org.example;

import org.example.manager.ManagerCommands;

import java.util.Scanner;

public class Scene {
    private final Application app;
    private final ManagerCommands manager;
    private final Scanner scanner;

    public Scene(Application app) {
        this.app = app;
        this.manager = app.getManagerCommands();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("The program is running. Enter 'help' for help.");
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            manager.callCommand(scanner, parts);

            if (parts[0].equalsIgnoreCase("exit")) break;
        }
    }
}