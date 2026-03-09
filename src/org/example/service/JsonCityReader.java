package org.example.service;

import org.example.data.City;
import org.example.data.Climate;
import org.example.data.Coordinates;
import org.example.data.Government;
import org.example.data.Human;
import org.example.data.StandardOfLiving;
import org.example.exceptions.InvalidDataException;
import org.example.validate.InputValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class JsonCityReader {


    public static City readSingleCity(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IllegalArgumentException("JSON file not found: " + filename);
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException("Cannot read JSON file: " + filename);
        }

        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(fis, "UTF-8")) {
            char[] buffer = new char[1024];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                content.append(buffer, 0, len);
            }
        }

        String jsonText = content.toString().trim();
        if (jsonText.isEmpty()) {
            throw new IllegalArgumentException("JSON file is empty: " + filename);
        }

        JSONObject jsonCity;
        if (jsonText.startsWith("[")) {
            JSONArray arr = new JSONArray(jsonText);
            if (arr.isEmpty()) {
                throw new IllegalArgumentException("JSON array is empty in file: " + filename);
            }
            jsonCity = arr.getJSONObject(0);
        } else {
            jsonCity = new JSONObject(jsonText);
        }

        return parseCity(jsonCity);
    }

    private static City parseCity(JSONObject jsonCity) throws InvalidDataException {
        checkRequiredFields(jsonCity);

        Long id = InputValidator.validateId(jsonCity.getString("id"));
        String name = InputValidator.validateName(jsonCity.getString("name"));
        Double area = InputValidator.validateArea(jsonCity.getString("area"));
        int population = InputValidator.validatePopulation(jsonCity.getString("population"));

        JSONObject cordJson = jsonCity.getJSONObject("coordinates");
        float x = cordJson.getFloat("x");
        double y = cordJson.getDouble("y");
        InputValidator.validateCoordinates(x, y);
        Coordinates coordinates = new Coordinates(x, y);

        java.util.Date creationDate = InputValidator.validateCreationDate(
                jsonCity.getString("creationDate")
        );

        Government government = InputValidator.validateEnum(
                jsonCity.getString("government"),
                Government.class,
                "Government",
                true
        );

        String climateRaw = jsonCity.has("climate") && !jsonCity.isNull("climate")
                ? jsonCity.getString("climate")
                : null;
        Climate climate = InputValidator.validateEnum(
                climateRaw,
                Climate.class,
                "climate",
                false
        );

        String solRaw = jsonCity.has("standardOfLiving") && !jsonCity.isNull("standardOfLiving")
                ? jsonCity.getString("standardOfLiving")
                : null;
        StandardOfLiving standardOfLiving = InputValidator.validateEnum(
                solRaw,
                StandardOfLiving.class,
                "standardOfLiving",
                false
        );

        Human governor = null;
        if (jsonCity.has("governor") && !jsonCity.isNull("governor")) {
            JSONObject govJson = jsonCity.getJSONObject("governor");
            String birthdayStr = govJson.optString("birthday", null);
            java.util.Date birthday = InputValidator.validateBirthday(birthdayStr);
            governor = new Human(birthday);
        }
        int metersAboveSeaLevel = Integer.parseInt(jsonCity.getString("metersAboveSeaLevel"));

        return new City(id, name, coordinates, creationDate, area, population,
                metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    private static void checkRequiredFields(JSONObject jsonCity) {
        if (!jsonCity.has("id") || jsonCity.isNull("id")) {
            throw new IllegalArgumentException("City id cannot be empty");
        }
        if (!jsonCity.has("name") || jsonCity.isNull("name")) {
            throw new IllegalArgumentException("City name cannot be empty");
        }
        if (!jsonCity.has("coordinates") || jsonCity.isNull("coordinates")) {
            throw new IllegalArgumentException("City coordinates cannot be empty");
        }
        if (!jsonCity.has("population") || jsonCity.isNull("population")) {
            throw new IllegalArgumentException("City population cannot be empty");
        }
        if (!jsonCity.has("creationDate") || jsonCity.isNull("creationDate")) {
            throw new IllegalArgumentException("City creationDate cannot be empty");
        }
        if (!jsonCity.has("area") || jsonCity.isNull("area")) {
            throw new IllegalArgumentException("City area cannot be empty");
        }
        if (!jsonCity.has("government") || jsonCity.isNull("government")) {
            throw new IllegalArgumentException("City government cannot be empty");
        }
    }
}

