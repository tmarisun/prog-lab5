package org.example.commands;

import org.example.Application;
import org.example.data.City;
import org.example.service.InputReader;
import org.example.service.InputReaderFactory;
import org.example.validate.CityValidator;

/**
 * Команда добавления города в коллекцию (с консоли или из JSON-файла, если указан аргумент).
 */
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
            String filePath = (args.length >= 2) ? args[1] : null;
            InputReader reader = InputReaderFactory.createReader(filePath, org.example.service.CityReader.scanner);
            City city = reader.readCity();

            if (city == null) {
                System.out.println("Cannot add element: invalid input data.");
                return;
            }

            CityValidator.validateCity(city);

            boolean idExists = false;
            for (City existing : Application.getCityStack()) {
                if (existing.getId().equals(city.getId())) {
                    idExists = true;
                    break;
                }
            }
            if (idExists) {
                System.out.println("Cannot add city: ID " + city.getId() + " already exists in the collection.");
                return;
            }

            app.addCity(city);
            if (!org.example.service.CityReader.isScriptMode()) {
                System.out.println("Successfully add: " + city.getId());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}