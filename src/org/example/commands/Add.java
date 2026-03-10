package org.example.commands;

import org.example.Application;
import org.example.service.CityReader;
import org.example.data.City;
import org.example.validate.CityValidator;

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
            City city = CityReader.readCity();
            CityValidator.validateCity(city);
            app.addCity(city);
            System.out.println("Successfully add: " + city.getId());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}