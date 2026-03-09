package org.example.commands;

import org.example.Application;
import org.example.manager.ManagerCommands;

public class Help implements Command {
    private final Application app;
    private final ManagerCommands manager;

    public Help(Application app, ManagerCommands manager) {
        this.app = app;
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Output help for available commands";
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            System.err.println("Command 'help' does not accept any arguments.");
            return;
        }

        System.out.println("Available commands:");
        for (Command command : manager.getAllCommands()) {
            System.out.printf("  %-40s : %s%n", command.getName(), command.getDescription());
        }
    }
}