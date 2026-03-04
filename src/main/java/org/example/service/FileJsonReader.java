package org.example.service;

import org.example.exceptions.InvalidDataException;
import org.example.exceptions.NoRightsException;
import org.example.data.*;
import org.example.validate.InputValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


public class FileJsonReader {



    public static Stack<City> loadCollection(String filename) throws FileNotFoundException, NoRightsException {

        File file = new File(filename);
        if(!file.exists()) {
            throw new FileNotFoundException("File : " + filename + " not found. Please check the file path.");
        }
        if(!file.canRead()) {
            throw new NoRightsException("File cannot be read:" + filename);
        }

        StringBuilder content = new StringBuilder();
        try(FileInputStream fis = new FileInputStream(filename);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8")) {

            char[] buffer = new char[1024];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                content.append(buffer, 0, len);
            }
        }
        catch (Exception e) {
            throw new FileNotFoundException("File cannot be read:" + filename);
        }

        String jsonText = content.toString().trim();

        Stack<City> cityStack = parseCityFromJson(jsonText, filename);

        Set<Long> ids = new HashSet<>();
        for (City city : cityStack) {
            if (!ids.add(city.getId())) {
                throw new NoRightsException("Duplicate ID found: " + city.getId());
            }
        }

        return cityStack;
    }

    private static Stack<City> parseCityFromJson(String jsonText, String filename) throws FileNotFoundException, NoRightsException {
        Stack<City> cityStack = new Stack<>();

        if (jsonText.isEmpty()) {
            System.out.println("The file is empty. The collection will be empty.");
            return cityStack;
        }

        try{
            JSONArray jsonArray = new JSONArray(jsonText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCity = jsonArray.getJSONObject(i);
                City city = checkCityFromJson(jsonCity);
                cityStack.push(city);
            }
            System.out.println("Download " + cityStack.size() + "cities from the file" + filename);
        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
            System.err.println("empty collection is being");
            cityStack.clear();
        }

        return cityStack;
    }

    private static City checkCityFromJson(JSONObject jsonCity) throws NoRightsException, InvalidDataException {
        checkIn(jsonCity);

        Long id = InputValidator.validateId(jsonCity.getString("id"));
        String name = InputValidator.validateName(jsonCity.getString("name"));
        Double area = InputValidator.validateArea(jsonCity.getString("area"));
        int population = InputValidator.validatePopulation(jsonCity.getString("population"));

        JSONObject cordJson = jsonCity.getJSONObject("coordinates");
        float x = cordJson.getFloat("x");
        double y = cordJson.getFloat("y");
        InputValidator.validateCoordinates(x, y);
        Coordinates coordinates = new Coordinates(x, y);

        java.util.Date creationDate = InputValidator.validateCreationDate(
                jsonCity.getString("creationDate")
        );

        // Enum
        Government government = InputValidator.validateEnum(
                jsonCity.getString("government"),
                Government.class,
                "Government",
                true
        );

        Climate climate = Climate.valueOf(jsonCity.getString("climate").trim().toUpperCase());
        StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(jsonCity.getString("standardOfLiving").trim().toUpperCase());
        Human governor = new Human(creationDate);

        int metersAboveSeaLevel = Integer.parseInt(jsonCity.getString("metersAboveSeaLevel"));
        return new City(id, name, coordinates, creationDate, area, population,
                metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    private static void checkIn(JSONObject jsonCity) throws IllegalArgumentException {
        if(!jsonCity.has("id") || jsonCity.isNull("id")) {
            throw new IllegalArgumentException("City id cannot be empty");
        }
        if(!jsonCity.has("name") || jsonCity.isNull("name")) {
            throw new IllegalArgumentException("City name cannot be empty");
        }
        if(!jsonCity.has("coordinates") || jsonCity.isNull("coordinates")) {
            throw new IllegalArgumentException("City coordinates cannot be empty");
        }
        if(!jsonCity.has("population") || jsonCity.isNull("population")) {
            throw new IllegalArgumentException("City population cannot be empty");
        }
        if(!jsonCity.has("creationDate") || jsonCity.isNull("creationDate")) {
            throw new IllegalArgumentException("City creationDate cannot be empty");
        }
        if(!jsonCity.has("area") || jsonCity.isNull("area")) {
            throw new IllegalArgumentException("City area cannot be empty");
        }
        if(!jsonCity.has("government") ||  jsonCity.isNull("government")) {
            throw new IllegalArgumentException("City government cannot be empty");
        }
    }
}
