package org.example.commands;

import org.example.Application;
import java.util.Scanner;

public class Exit implements Command {
    private final Application app;

    public Exit(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Exit the program (without saving)";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Exiting without saving...");
        System.exit(0);
    }
}