package org.example;

import org.example.Exeptions.FileNotFoundExeption;
import org.example.Exeptions.NoRightsExeption;
import org.example.information.*;
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


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

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

    private static City checkCityFromJson(JSONObject jsonCity, String filename) throws NoRightsExeption {
        checkIn(jsonCity);
        Long id = getLongValue(jsonCity, "id");
        if(id <= 0) throw new NoRightsExeption("Id cannot be less than zero");

        String name = getStringValue(jsonCity, "name");
        if(name.isEmpty()) throw new NoRightsExeption("Name cannot be null");

        Double area = jsonCity.getDouble("area");
        int population = jsonCity.getInt("population");
        if (area <= 0) throw new NoRightsExeption("Area must be greater than 0");
        if (population <= 0) throw new NoRightsExeption("Population must be greater than 0");

        int metersAboveSeaLevel = jsonCity.has("metersAboveSeaLevel") && !jsonCity.isNull("metersAboveSeaLevel")
                ? jsonCity.getInt("metersAboveSeaLevel") : 0;

        org.json.JSONObject coordJson = jsonCity.getJSONObject("coordinates");
        if (!coordJson.has("x") || coordJson.isNull("x") || !coordJson.has("y") || coordJson.isNull("y"))
            throw new NoRightsExeption("Invalid coordinates");
        float x = coordJson.getFloat("x");
        double y = coordJson.getDouble("y");
        if (x > 959 || y > 613) throw new NoRightsExeption("Coordinates exceed maximum values (x≤959, y≤613)");
        Coordinates coordinates = new Coordinates(x, y);

        Date creationDate;
        try {
            creationDate = DATE_FORMAT.parse(jsonCity.getString("creationDate"));
        } catch (Exception e) {
            throw new NoRightsExeption("Invalid creationDate format. Expected ISO 8601 format.");
        }

        Climate climate = jsonCity.has("climate") && !jsonCity.isNull("climate")
                ? Climate.valueOf(jsonCity.getString("climate").trim().toUpperCase()) : null;
        Government government = Government.valueOf(jsonCity.getString("government").trim().toUpperCase());
        StandardOfLiving standardOfLiving = jsonCity.has("standardOfLiving") && !jsonCity.isNull("standardOfLiving")
                ? StandardOfLiving.valueOf(jsonCity.getString("standardOfLiving").trim().toUpperCase()) : null;
        Human governor = null;

        if (jsonCity.has("governor") && !jsonCity.isNull("governor")) {
            org.json.JSONObject govJson = jsonCity.getJSONObject("governor");
            if (govJson.has("birthday") && !govJson.isNull("birthday")) {
                try {
                    LocalDate birthday = LocalDate.parse(govJson.getString("birthday"));
                    governor = new Human(birthday);
                } catch (DateTimeParseException e) {
                    throw new NoRightsExeption("Invalid governor birthday format. Expected yyyy-MM-dd");
                }
            }
        }

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
    private static long getLongValue(JSONObject obj, String field) throws NoRightsExeption {
        try {
            return obj.getLong(field);
        } catch (Exception e) {
            throw new NoRightsExeption(field + " Can be only type Long. Mistake: " + e.getMessage());
        }
    }
    private  static String getStringValue(JSONObject obj, String field) throws NoRightsExeption {
        try {
            return obj.getString(field);
        }
        catch (Exception e) {
            throw new NoRightsExeption(field + " Can be only type String. Mistake: " + e.getMessage());
        }
    }
}
