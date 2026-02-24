package org.example;

import org.example.Exeptions.FileNotFoundExeption;
import org.example.Exeptions.NoRightsExeption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;

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
