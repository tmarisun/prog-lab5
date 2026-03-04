package org.example;

import lombok.Getter;
import org.example.exceptions.NoRightsException;
import org.example.data.City;
import org.example.manager.ManagerCommands;
import org.example.service.FileJsonReader;

import java.io.FileNotFoundException;
import java.util.Stack;

public class Application {

    @Getter
    private static Stack<City> cityStack;
    @Getter
    private String fileName;
    @Getter
    private ManagerCommands managerCommands;

    public Application(String filename) throws FileNotFoundException, NoRightsException {
        this.fileName = filename;
        cityStack = FileJsonReader.loadCollection(filename);
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



}
