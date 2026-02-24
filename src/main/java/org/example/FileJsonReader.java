package org.example;

import org.example.exeptions.FileNotFoundExeption;
import org.example.exeptions.InvalidDataException;
import org.example.exeptions.NoRightsExeption;
import org.example.information.*;
import org.example.validate.InputValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Stack;


public class FileJsonReader {


    private static Stack<City> loadCollection(String filename) throws FileNotFoundException, NoRightsExeption {

        File file = new File(filename);
        if(!file.exists()) {
            throw new FileNotFoundException("File : " + filename + " not found. Please check the file path.");
        }
        if(!file.canRead()) {
            throw new NoRightsExeption("File cannot be read:" + filename);
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
            throw new FileNotFoundExeption("File cannot be read:" + filename);
        }

        String jsonText = content.toString().trim();

        Stack<City> cities = parseCityFromJson(jsonText, filename);
        return cities;
    }

    public static Stack<City> getloadCollection(String filename) throws FileNotFoundException, NoRightsExeption {
        return loadCollection(filename);
    }


    private static Stack<City> parseCityFromJson(String jsonText, String filename) throws FileNotFoundException, NoRightsExeption {
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

    private static City checkCityFromJson(JSONObject jsonCity, String filename) throws NoRightsExeption, InvalidDataException {
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

    private static void checkIn(JSONObject jsonCity) throws NoRightsExeption {
        if(!jsonCity.has("id") || jsonCity.isNull("id")) {
            throw new NoRightsExeption("City id cannot be empty");
        }
        if(!jsonCity.has("name") || jsonCity.isNull("name")) {
            throw new NoRightsExeption("City name cannot be empty");
        }
        if(!jsonCity.has("coordinates") || jsonCity.isNull("coordinates")) {
            throw new NoRightsExeption("City coordinates cannot be empty");
        }
        if(!jsonCity.has("population") || jsonCity.isNull("population")) {
            throw new NoRightsExeption("City population cannot be empty");
        }
        if(!jsonCity.has("creationDate") || jsonCity.isNull("creationDate")) {
            throw new NoRightsExeption("City creationDate cannot be empty");
        }
        if(!jsonCity.has("area") || jsonCity.isNull("area")) {
            throw new NoRightsExeption("City area cannot be empty");
        }
        if(!jsonCity.has("government") ||  jsonCity.isNull("government")) {
            throw new NoRightsExeption("City government cannot be empty");
        }
    }
}
