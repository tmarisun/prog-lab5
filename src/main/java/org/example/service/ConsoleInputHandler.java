package org.example.service;

import org.example.Application;
import org.example.data.City;
import org.example.data.Coordinates;
import org.example.data.Human;
import org.example.data.Climate;
import org.example.data.Government;
import org.example.data.StandardOfLiving;
import org.example.validate.CityValidator;
import org.example.exceptions.InvalidDataException;
import org.example.validate.CoordinatesValidator;
import org.example.validate.InputValidator;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static org.example.validate.InputValidator.*;

public class ConsoleInputHandler {

    public static City readCityFromConsole(Long predefinedId) throws InvalidDataException {
        Scanner scanner = new Scanner(System.in);
        final int MAX_ATTEMPTS = 100;
        int attempts = 0;
        City city = new City();

        while(attempts < MAX_ATTEMPTS) {
            System.out.println("Entering city data (attempt " + (attempts + 1) + ") ===");
            try {
                city.setName(readName(scanner));
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the coordinates:");
            try {
                float x = readCoordinateX(scanner);
                double y = readCoordinateY(scanner);
                Coordinates coordinates = new Coordinates(x, y);
                CoordinatesValidator.validateCoordinates(coordinates);
                city.setCoordinates(coordinates);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS) {
            try {
                double area = readDoubleArea(scanner);
                InputValidator.validateArea(area);
                city.setArea(area);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS) {
            try{
                int population = readIntPollution(scanner);
                InputValidator.validatePopulation(population);
                city.setPopulation(population);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS){
            try{
                Climate climate = readEnumClimate(scanner);
                city.setClimate(climate);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS){
            try{
                int metersAboveSeaLevel = readSeaLevel(scanner);
                city.setMetersAboveSeaLevel(metersAboveSeaLevel);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS){
            try {
                Government government = readEnumGovernment(scanner);
                city.setGovernment(government);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }

        }

        while(attempts < MAX_ATTEMPTS){
            try{
                StandardOfLiving standardOfLiving = readEnumStandard(scanner);
                city.setStandardOfLiving(standardOfLiving);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        while(attempts < MAX_ATTEMPTS){
            try{
                Human governor = null;
                if (readYesNo(scanner)) {
                    java.util.Date birthday = readDate(scanner);
                    governor = new Human(birthday);
                }
                city.setGovernor(governor);
                break;
            }
            catch(InvalidDataException e) {
                System.err.println("Error validation: " + e.getMessage());
                System.out.println("Try to enter the data again.\n");
                attempts++;
            }
        }

        long id = Application.getNextId();
        city.setId(id);

        if(attempts == MAX_ATTEMPTS) {
            System.out.println("You've used a lot of input attempts, repeat after 10 minutes.");
            Runtime.getRuntime().exit(0);
        }

        System.out.println("The data is accepted!");
        return city;
    }


    private static String readName(Scanner scanner) throws InvalidDataException {
        System.out.print("Enter the name of the city: ");
        String scn = scanner.nextLine().trim();
        scn = validateName(scn);
        return scn;
    }

    private static float readCoordinateX(Scanner scanner) throws InvalidDataException {
        System.out.print("  X: ");
        String scn = scanner.nextLine().trim();
        return validateX(scn);
    }

    private static int readSeaLevel(Scanner scanner) throws InvalidDataException {
        System.out.print("Enter the height above sea level: ");
        String scn = scanner.nextLine().trim();
        return Integer.parseInt(scn);
    }

    private static double readCoordinateY(Scanner scanner) throws InvalidDataException {
        System.out.print("  Y: ");
        String scn = scanner.nextLine().trim();
        return validateY(scn);
    }

    private static int readIntPollution(Scanner scanner) throws InvalidDataException {
        System.out.print("Enter the city's population: ");
        String scn = scanner.nextLine().trim();
        return InputValidator.validatePopulation(scn);
    }

    private static double readDoubleArea(Scanner scanner) throws InvalidDataException {
        System.out.println("Enter the area of the city: ");
        String scn = scanner.nextLine().trim();
        return InputValidator.validateArea(scn);
    }

    private static Climate readEnumClimate(Scanner scanner) throws InvalidDataException {
        System.out.println("Available values: " + Arrays.toString(Climate.values()));
        System.out.println("Select the climate (Enter to skip): ");
        String climateInput = scanner.nextLine().trim();

        return InputValidator.validateEnum(
                climateInput.isEmpty() ? null : climateInput,
                Climate.class,
                "climate",
                false
        );
    }

    private static Government readEnumGovernment(Scanner scanner) throws InvalidDataException {
        System.out.println("Available values: " + Arrays.toString(Government.values()));
        System.out.println("Choose a form of government: ");
        String govInput = scanner.nextLine().trim();

        return InputValidator.validateEnum(
                govInput,
                Government.class,
                "government",
                true
        );
    }

    private static StandardOfLiving readEnumStandard(Scanner scanner) throws InvalidDataException {
        System.out.println("Available values: " + Arrays.toString(StandardOfLiving.values()));
        System.out.print("Select the standard of living (Enter to skip): ");
        String input = scanner.nextLine().trim();

        return InputValidator.validateEnum(
                input.isEmpty() ? null : input,
                StandardOfLiving.class,
                "StandardOfLiving",
                false
        );

    }

    private static boolean readYesNo(Scanner scanner) {
        System.out.print("Add a governor? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes") || input.equals("да");
    }

    private static Date readDate(Scanner scanner) throws InvalidDataException {
        System.out.print("Введите дату рождения (yyyy-MM-dd): ");
        return (InputValidator.validateCreationDate(scanner.nextLine().trim()));
    }
}