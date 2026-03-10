package org.example.commands;

import org.example.Application;
import org.example.service.JsonFileSaver;

public class Save implements Command {
    private final Application app;

    public Save(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Save the collection to a file";
    }

    @Override
    public void execute(String[] args) {
        try {
            JsonFileSaver.saveCitiesToFile(Application.getCityStack(), app.getFileName());
            System.out.println("Collection saved to " + app.getFileName());
        } catch (Exception e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }
}