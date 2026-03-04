package org.example.commands;

import org.example.Application;
import java.util.Collections;
import java.util.Scanner;

public class Sort implements Command {
    private final Application app;

    public Sort(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public String getDescription() {
        return "Sort the collection in natural order";
    }

    @Override
    public void execute(String[] args) {
        Collections.sort(app.getCityStack());
        System.out.println("Collection sorted by ID (natural order).");
    }
}