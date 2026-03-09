package org.example.commands;

import org.example.Application;
import org.example.service.ConsoleInputHandler;
import org.example.service.JsonCityReader;
import org.example.data.City;
import java.util.Scanner;
import java.util.Stack;

public class AddIfMax implements Command {
    private final Application app;

    public AddIfMax(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "Add an element if its value is greater than the maximum";
    }

    @Override
    public void execute(String[] args) {
        try {
            City candidate;
            if (args.length >= 2 && args[1] != null && args[1].trim().toLowerCase().endsWith(".json")) {
                candidate = JsonCityReader.readSingleCity(args[1].trim());

                boolean idExists = Application.getCityStack().stream()
                        .anyMatch(c -> c.getId().equals(candidate.getId()));
                if (idExists) {
                    System.err.println("Cannot add city: ID " + candidate.getId() + " already exists in the collection.");
                    return;
                }
            } else {
                candidate = ConsoleInputHandler.readCityFromConsole();
            }
            Stack<City> stack = app.getCityStack();

            if (stack.isEmpty() || candidate.compareTo(stack.stream().max(City::compareTo).get()) > 0) {
                stack.push(candidate);
                System.out.println("City added (ID: " + candidate.getId() + ")");
            } else {
                System.out.println("City not added - does not exceed maximum ID.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}