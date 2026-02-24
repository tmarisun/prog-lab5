package org.example.validate;

import org.example.exeptions.InvalidDataException;
import org.example.information.Coordinates;

public class CoordinatesValidator {
    public static void validate(Coordinates coordinates) throws InvalidDataException {
        if (coordinates == null) {
            throw new InvalidDataException("Coordinates cannot be null");
        }
        InputValidator.validateCoordinates(coordinates.getX(), coordinates.getY());
    }
}
