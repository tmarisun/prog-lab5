package org.example.commands;

import org.example.Application;
import org.example.service.ConsoleInputHandler;
import org.example.service.JsonCityReader;
import org.example.data.City;
import java.util.Scanner;

public class Add implements Command {
    private final Application app;

    public Add(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add new element to Collection";
    }

    @Override
    public void execute(String[] args) {
        try {
            City city;
            if (args.length >= 2 && args[1] != null && args[1].trim().toLowerCase().endsWith(".json")) {
                city = JsonCityReader.readSingleCity(args[1].trim());

                boolean idExists = Application.getCityStack().stream()
                        .anyMatch(c -> c.getId().equals(city.getId()));
                if (idExists) {
                    System.err.println("Cannot add city: ID " + city.getId() + " already exists in the collection.");
                    return;
                }
            } else {
                city = ConsoleInputHandler.readCityFromConsole();
            }
            app.addCity(city);
            System.out.println("Successfully add: " + city.getId());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}