package org.example.commands;

import org.example.Application;
import java.util.Scanner;

public class Clear implements Command {
    private final Application app;

    public Clear(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear the collection";
    }

    @Override
    public void execute(Scanner scanner, String[] args) {
        app.getCityStack().clear();
        System.out.println("Collection cleared.");
    }
}