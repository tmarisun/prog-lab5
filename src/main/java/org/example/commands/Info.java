package org.example.commands;

import org.example.Application;
import java.util.Date;
import java.util.Scanner;

public class Info implements Command {
    private final Application app;

    public Info(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Show information about the collection";
    }

    @Override
    public void execute(Scanner scanner, String[] args) {
        System.out.println("Collection type: java.util.Stack");
        System.out.println("Initialization date: " + new Date());
        System.out.println("Number of elements: " + app.getCityStack().size());
        System.out.println("Next auto-generated ID: " + app.getNextId());
    }
}