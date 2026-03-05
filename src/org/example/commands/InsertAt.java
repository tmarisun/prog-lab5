package org.example.commands;

import org.example.Application;
import org.example.service.ConsoleInputHandler;
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
            int index = Integer.parseInt(args[1]);
            Stack<City> stack = app.getCityStack();

            if (index < 0 || index > stack.size()) {
                System.err.println("Index must be between 0 and " + stack.size());
                return;
            }

            City city = ConsoleInputHandler.readCityFromConsole();
            stack.add(index, city);
            System.out.println("City inserted at position " + index);

        } catch (NumberFormatException e) {
            System.err.println("Invalid index format.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}