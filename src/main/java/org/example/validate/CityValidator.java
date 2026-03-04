package org.example.validate;

import org.example.data.City;
import org.example.exceptions.InvalidDataException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CityValidator {

    public static void validateCity(City city) throws InvalidDataException {
        InputValidator.validateId(city.getId());
        InputValidator.validateName(city.getName());
        InputValidator.validateArea(city.getArea());
        InputValidator.validatePopulation(city.getPopulation());
        InputValidator.validateNotNull(city.getCoordinates(), "Coordinates");
        InputValidator.validateNotNull(city.getCreationDate(), "Creation date");
        InputValidator.validateNotNull(city.getGovernment(), "Government");

        CoordinatesValidator.validate(city.getCoordinates());

        InputValidator.validateUniqueIds((Collection<City>) city);
    }


}
