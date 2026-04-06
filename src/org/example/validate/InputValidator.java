package org.example.validate;

import org.example.data.City;
import org.example.exceptions.InvalidDataException;

import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InputValidator {

    public static void validateUniqueIds(Collection<City> cities) throws InvalidDataException {
        Set<Long> ids = new HashSet<>();
        for (City city : cities) {
            if (!ids.add(city.getId())) {
                throw new InvalidDataException("Duplicate ID found: " + city.getId());
            }
        }
    }

    // === Coordinate Constraints ===
    private static final float MAX_X = 959f;
    private static final double MAX_Y = 613.0;

    // === Numeric Constraints ===
    private static final double MIN_AREA = 0.0;
    private static final int MIN_POPULATION = 0;
    private static final long MIN_ID = 0;

    //-------------------------------

    public static void validateId(Long id) throws InvalidDataException {
        if (id == null) {
            throw new InvalidDataException("ID cannot be null");
        }
        if (id <= MIN_ID) {
            throw new InvalidDataException("ID must be greater than 0 (received: " + id + ")");
        }
    }

    //----------------

    public static Float validateX(Float x) throws InvalidDataException {
        if (x == null) {
            throw new InvalidDataException("X coordinate cannot be empty");
        }

        try {
            if (x > MAX_X) {
                throw new InvalidDataException(
                        "X coordinate cannot exceed " + MAX_X + " (received: " + x + ")"
                );
            }
            return x;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("X coordinate must be a valid float number");
        }
    }

    public static Double validateY(Double y) throws InvalidDataException {
        if (y == null) {
            throw new InvalidDataException("Y coordinate cannot be empty");
        }

        try {
            if (y > MAX_Y) {
                throw new InvalidDataException(
                        "Y coordinate cannot exceed " + MAX_Y + " (received: " + y + ")"
                );
            }
            return y;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Y coordinate must be a valid double number");
        }
    }

    //-----------------------------------------

    public static void validateCoordinates(float x, double y) throws InvalidDataException {
        if (x > MAX_X) {
            throw new InvalidDataException(
                    "X coordinate cannot exceed " + MAX_X + " (received: " + x + ")"
            );
        }
        if (y > MAX_Y) {
            throw new InvalidDataException(
                    "Y coordinate cannot exceed " + MAX_Y + " (received: " + y + ")"
            );
        }
    }

    //------------------------------------------



    public static void validatePopulation(Integer population) throws InvalidDataException {
        if (population == null) {
            throw new InvalidDataException("Population cannot be empty");
        }

        try {
            if (population <= MIN_POPULATION) {
                throw new InvalidDataException("Population must be greater than 0 (received: " + population + ")");
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Population must be a valid integer number");
        }
    }


    //-----------------------------

    public static void validateArea(Double area) throws InvalidDataException {
        if (area == null) {
            throw new InvalidDataException("Area cannot be null");
        }
        if (area <= MIN_AREA) {
            throw new InvalidDataException("Area must be greater than 0 (received: " + area + ")");
        }
    }

    //-----------------------------------------



    public static String validateName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be empty");
        }
        return name.trim();
    }

    //-----------------------------------------

    public static java.util.Date validateCreationDate(String dateString) throws InvalidDataException {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new InvalidDataException("Creation date cannot be empty");
        }

        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            format.setLenient(false);
            return format.parse(dateString.trim());
        } catch (Exception e) {
            throw new InvalidDataException("Invalid date format. Expected: yyyy-MM-dd'T'HH:mm:ss");
        }
    }

    //-----------------------

    public static java.util.Date validateBirthday(String dateString) throws InvalidDataException {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new InvalidDataException("Birthday cannot be empty");
        }

        String s = dateString.trim();
        String[] patterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"};
        for (String pattern : patterns) {
            try {
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
                format.setLenient(false);
                return format.parse(s);
            } catch (Exception ignored) {
                // try next pattern
            }
        }
        throw new InvalidDataException("Invalid birthday format. Expected: yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss");
    }


    //---------------

    public static <T extends Enum<T>> T validateEnum(
            String value,
            Class<T> enumClass,
            String fieldName,
            boolean required) throws InvalidDataException {

        if (value == null || value.trim().isEmpty()) {
            if (required) {
                throw new InvalidDataException(fieldName + " cannot be empty");
            }
            return null;
        }

        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException(
                    "Invalid " + fieldName + ". Valid values: " + java.util.Arrays.toString(enumClass.getEnumConstants())
            );
        }
    }


    //-------------------------------

    public static void validateNotNull(Object obj, String fieldName) throws InvalidDataException {
        if (obj == null) {
            throw new InvalidDataException(fieldName + " cannot be null");
        }
    }





}