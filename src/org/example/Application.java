package org.example;

import lombok.Getter;
import org.example.commands.Command;
import org.example.exceptions.NoRightsException;
import org.example.data.City;
import org.example.manager.ManagerCommands;
import org.example.service.JsonFileLoader;
import org.example.validate.CityValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;

public class Application {

    @Getter
    private static Stack<City> cityStack;
    @Getter
    private String fileName;
    @Getter
    private ManagerCommands managerCommands;

    public Application(String filename) throws IOException, NoRightsException {
        this.fileName = filename;
        cityStack = JsonFileLoader.loadCollection(filename);
        for (City city : cityStack) {
            CityValidator.validateCity(city);
        }

        this.managerCommands = new ManagerCommands(this);
    }

    public static long getNextId() {
        return cityStack.stream()
                .mapToLong(City::getId)
                .max()
                .orElse(0) + 1;
    }

    public void addCity(City city) {
        cityStack.push(city);
    }

    public void help(Map<String, Command> commands){
        System.out.println("The list of commands available to you: ");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            Command value = entry.getValue();
            System.out.println(value.getDescription());
        }
    }



}