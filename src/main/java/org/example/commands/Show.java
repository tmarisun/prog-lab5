package org.example.commands;

import org.example.Application;
import org.example.data.City;
import java.util.Scanner;

public class Show implements Command {
    private final Application app;

    public Show(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Show all elements of the collection";
    }

    @Override
    public void execute(String[] args) {
        if (app.getCityStack().isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        for (City city : app.getCityStack()) {
            System.out.println(city);
        }
    }
}