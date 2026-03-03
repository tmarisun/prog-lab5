package org.example.commands;

import org.example.Application;
import org.example.service.ConsoleInputHandler;
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
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(Scanner scanner, String[] args) {
        try {
            City city = ConsoleInputHandler.readCityFromConsole(null);
            app.addCity(city);
            System.out.println("Город добавлен с ID: " + city.getId());
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении: " + e.getMessage());
        }
    }
}