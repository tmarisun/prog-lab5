package org.example.service;

import org.example.information.City;
import org.example.information.Climate;
import org.example.information.Government;
import org.example.information.Human;
import org.example.information.StandardOfLiving;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Stack;
import java.util.TimeZone;

/**
 * Класс для сохранения коллекции городов в файл в формате JSON.
 * Использует BufferedWriter и OutputStreamWriter с кодировкой UTF-8.
 */
public class JsonWriter {

    /** Формат даты для совместимости с JsonParser */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Сохраняет коллекцию городов в указанный файл в формате JSON.
     *
     * @param cities   коллекция городов для сохранения
     * @param filename путь к файлу для записи
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public static void saveCitiesToFile(Stack<City> cities, String filename) throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (City city : cities) {
            jsonArray.put(cityToJson(city));
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            writer.write(jsonArray.toString(2)); // Pretty-print с отступом 2 пробела
            writer.newLine();
        }
    }

    /**
     * Преобразует объект City в JSONObject для сериализации в JSON.
     *
     * @param city объект City для преобразования
     * @return JSONObject, представляющий город
     */
    private static JSONObject cityToJson(City city) {
        JSONObject json = new JSONObject();

        // Обязательные поля
        json.put("id", city.getId());
        json.put("name", city.getName());
        json.put("creationDate", DATE_FORMAT.format(city.getCreationDate()));
        json.put("area", city.getArea());
        json.put("population", city.getPopulation());
        json.put("metersAboveSeaLevel", city.getMetersAboveSeaLevel());
        json.put("government", city.getGovernment().name());

        // Coordinates (вложенный объект)
        JSONObject coordJson = new JSONObject();
        coordJson.put("x", city.getCoordinates().getX());
        coordJson.put("y", city.getCoordinates().getY());
        json.put("coordinates", coordJson);

        // Опциональные enum-поля (могут быть null)
        if (city.getClimate() != null) {
            json.put("climate", city.getClimate().name());
        }
        if (city.getStandardOfLiving() != null) {
            json.put("standardOfLiving", city.getStandardOfLiving().name());
        }

        // Опциональный объект Human (может быть null)
        if (city.getGovernor() != null) {
            JSONObject govJson = new JSONObject();
            govJson.put("birthday", city.getGovernor().getBirthday().toString()); // yyyy-MM-dd
            json.put("governor", govJson);
        }

        return json;
    }
}