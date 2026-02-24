package org.example;

import org.example.exeptions.NoRightsExeption;
import org.example.information.City;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Scene {

    private Stack<City> cityStack;
    private String filename;

    public Scene(String filename) throws FileNotFoundException, NoRightsExeption {
        this.filename = filename;
        this.cityStack = FileJsonReader.getloadCollection(filename);
        Set<Long> ids = new HashSet<>();
        for (City city : cityStack) {
            if (!ids.add(city.getId())) {
                throw new NoRightsExeption("Duplicate ID found: " + city.getId());
            }
        }


    }




}
