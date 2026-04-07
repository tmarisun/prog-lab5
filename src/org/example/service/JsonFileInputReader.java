package org.example.service;

import org.example.data.City;
import org.example.data.Climate;
import org.example.data.Coordinates;
import org.example.data.Government;
import org.example.data.Human;
import org.example.data.StandardOfLiving;
import org.example.validate.InputValidator;
import org.example.validate.CityValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static org.example.service.JsonFileLoader.loadCollection;

/**
 * Ридер для ввода City из JSON-файла.
 */
public class JsonFileInputReader implements InputReader {
    private final String filePath;

    public JsonFileInputReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public City readCity() {
        try {
            Stack<City> city = loadCollection(filePath);
            CityValidator.validateCity(city.get(0));
            return city.get(0);
        } catch (Exception e) {
            System.err.println("JSON input error: " + e.getMessage());
            return null;
        }
    }
}

