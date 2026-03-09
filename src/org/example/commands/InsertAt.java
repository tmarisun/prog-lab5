package org.example.commands;

import org.example.Application;
import org.example.service.ConsoleInputHandler;
import org.example.service.JsonCityReader;
import org.example.data.City;
import java.util.Scanner;
import java.util.Stack;

public class InsertAt implements Command {
    private final Application app;

    public InsertAt(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "insert_at";
    }

    @Override
    public String getDescription() {
        return "Insert an element at the specified position";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: insert_at <index>");
            return;
        }

        try {
            String[] tokens = args[1].trim().split("\\s+");
            int index = Integer.parseInt(tokens[0]);
            Stack<City> stack = app.getCityStack();

            if (index < 0 || index > stack.size()) {
                System.err.println("Index must be between 0 and " + stack.size());
                return;
            }

            City city;
            if (tokens.length >= 2 && tokens[1].toLowerCase().endsWith(".json")) {
                city = JsonCityReader.readSingleCity(tokens[1]);

                boolean idExists = Application.getCityStack().stream()
                        .anyMatch(c -> c.getId().equals(city.getId()));
                if (idExists) {
                    System.err.println("Cannot insert city: ID " + city.getId() + " already exists in the collection.");
                    return;
                }
            } else {
                city = ConsoleInputHandler.readCityFromConsole();
            }
            stack.add(index, city);
            System.out.println("City inserted at position " + index);

        } catch (NumberFormatException e) {
            System.err.println("Invalid index format.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}