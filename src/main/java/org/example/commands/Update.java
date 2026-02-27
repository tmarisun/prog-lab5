package org.example.commands;

import org.example.Application;
import org.example.ConsoleInputHandler;
import org.example.exeptions.InvalidDataException;
import org.example.information.City;
import org.example.validate.InputValidator;

import java.util.Scanner;
import java.util.Stack;

public class Update implements Command {
    private final Application app;

    public Update(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Update an element by its ID";
    }

    @Override
    public void execute(Scanner scanner, String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: update <id>");
            return;
        }

        try {
            long id = Long.parseLong(args[1]);
            InputValidator.validateId(id);

            Stack<City> CityStack = app.getCityStack();
            City found = null;

            for (City city : CityStack) {
                if (city.getId() == id) {
                    found = city;
                    break;
                }
            }

            if (found == null) {
                System.err.println("City with ID " + id + " not found.");
                return;
            }

            City newCity = ConsoleInputHandler.readCityFromConsole();
            CityStack.remove(found);
            CityStack.push(newCity);
            System.out.println("City updated successfully.");

        } catch (NumberFormatException | InvalidDataException e) {
            System.err.println("Invalid ID format.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}