package org.example;

import org.example.exeptions.NoRightsExeption;
import org.example.information.City;
import org.example.manager.ManagerCommands;

import java.io.FileNotFoundException;
import java.util.Stack;

public class Application {

    private Stack<City> cityStack;
    private String fileName;
    //private final ManagerServerQuery managerServer;
    private final ManagerCommands managerCommands;
    //private ManagerConnection managerConnection;

    public Application(String filename) throws FileNotFoundException, NoRightsExeption {
        this.fileName = filename;
        this.cityStack = FileJsonReader.getloadCollection(filename);
        this.managerCommands = new ManagerCommands(this);
    }


    public Stack<City> getCityStack() {
        return cityStack;
    }

    public String getFileName() {
        return fileName;
    }

    public long getNextId() {
        return cityStack.stream()
                .mapToLong(City::getId)
                .max()
                .orElse(0) + 1;
    }

    public void addCity(City city) {
        cityStack.push(city);
    }



}
