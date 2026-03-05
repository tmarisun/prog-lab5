package org.example.validate;

import org.example.exceptions.InvalidDataException;
import org.example.data.Coordinates;

public class CoordinatesValidator {
    public static void validateCoordinates(Coordinates coordinates) throws InvalidDataException {
        if (coordinates == null) {
            throw new InvalidDataException("Coordinates cannot be null");
        }
        InputValidator.validateCoordinates(coordinates.getX(), coordinates.getY());
    }
}