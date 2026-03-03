package org.example.service;

import org.example.exceptions.FileNotFoundException;
import org.example.exceptions.InvalidDataException;
import org.example.exceptions.NoRightsException;
import org.example.data.*;
import org.example.validate.InputValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


public class FileJsonReader {



    private static Stack<City> loadCollection(String filename) throws FileNotFoundException, NoRightsException {

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

    public static Stack<City> reloadCollection(String filename) throws FileNotFoundException, NoRightsException {
        return loadCollection(filename);
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
                City city = checkCityFromJson(jsonCity, filename);
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

    private static City checkCityFromJson(JSONObject jsonCity, String filename) throws NoRightsException, InvalidDataException {
        checkIn(jsonCity);

        Long id = InputValidator.validateId(jsonCity.getLong("id"));
        String name = InputValidator.validateName(jsonCity.getString("name"));
        Double area = InputValidator.validateArea(jsonCity.getDouble("area"));
        int population = InputValidator.validatePopulation(jsonCity.getInt("population"));

        JSONObject coordJson = jsonCity.getJSONObject("coordinates");
        float x = coordJson.getFloat("x");
        double y = coordJson.getFloat("y");
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
        Human governor = null;
        int metersAboveSeaLevel = Integer.parseInt(jsonCity.getInt("metersAboveSeaLevel"));
        return new City(id, name, coordinates, creationDate, area, population,
                metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    private static void checkIn(JSONObject jsonCity) throws NoRightsException {
        if(!jsonCity.has("id") || jsonCity.isNull("id")) {
            throw new NoRightsException("City id cannot be empty");
        }
        if(!jsonCity.has("name") || jsonCity.isNull("name")) {
            throw new NoRightsException("City name cannot be empty");
        }
        if(!jsonCity.has("coordinates") || jsonCity.isNull("coordinates")) {
            throw new NoRightsException("City coordinates cannot be empty");
        }
        if(!jsonCity.has("population") || jsonCity.isNull("population")) {
            throw new NoRightsException("City population cannot be empty");
        }
        if(!jsonCity.has("creationDate") || jsonCity.isNull("creationDate")) {
            throw new NoRightsException("City creationDate cannot be empty");
        }
        if(!jsonCity.has("area") || jsonCity.isNull("area")) {
            throw new NoRightsException("City area cannot be empty");
        }
        if(!jsonCity.has("government") ||  jsonCity.isNull("government")) {
            throw new NoRightsException("City government cannot be empty");
        }
    }
}
