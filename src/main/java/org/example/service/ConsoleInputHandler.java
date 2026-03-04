package org.example.service;

import org.example.data.City;
import org.example.data.Coordinates;
import org.example.data.Human;
import org.example.data.Climate;
import org.example.data.Government;
import org.example.data.StandardOfLiving;
import org.example.validate.CityValidator;
import org.example.exceptions.InvalidDataException;
import org.example.validate.InputValidator;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static org.example.validate.InputValidator.*;

public class ConsoleInputHandler {

    public static City readCityFromConsole(Long predefinedId) throws InvalidDataException {
        Scanner scanner = new Scanner(System.in);
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            try {
                System.out.println("=== Entering city data (attempt " + (attempts + 1) + ") ===");

                String name = readString(scanner);

                System.out.println("Enter the coordinates:");
                float x = readFloat(scanner);
                double y = readDouble(scanner);
                Coordinates coordinates = new Coordinates(x, y);

                double area = readDoubleArea(scanner);
                int population = readIntPollution(scanner);
                int metersAboveSeaLevel = readIntLevel(scanner);

                Climate climate = readEnumClimate(scanner);
                Government government = readEnumGovernment(scanner);
                StandardOfLiving standardOfLiving = readEnumStandard(scanner
                );

                Human governor = null;
                if (readYesNo(scanner)) {
                    java.util.Date birthday = readDate(scanner);
                    governor = new Human(birthday);
                }

                long id = (predefinedId != null) ? predefinedId : System.currentTimeMillis();
                Date creationDate = new Date();

                City city = new City(id, name, coordinates, creationDate, area, population,
                        metersAboveSeaLevel, climate, government, standardOfLiving, governor);

                CityValidator.validateCity(city);

                System.out.println("The data is accepted!");
                return city;

            } catch (InvalidDataException e) {
                attempts++;
                System.err.println("Error validation: " + e.getMessage());
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("Try to enter the data again.\n");
                } else {
                    throw new InvalidDataException("Exceeded the number of input attempts");
                }
            } catch (Exception e) {
                attempts++;
                System.err.println("Error: " + e.getMessage());
                if (attempts >= MAX_ATTEMPTS) {
                    throw new InvalidDataException("Couldn't enter correct data");
                }
            }
        }

        throw new InvalidDataException("Unknown input error");
    }


    private static String readString(Scanner scanner) throws InvalidDataException {
        System.out.print("Enter the name of the city: ");
        String scn = scanner.nextLine().trim();
        scn = validateName(scn);
        return scn;
    }

    private static float readFloat(Scanner scanner) throws InvalidDataException {
        System.out.print("  X: ");
        String scn = scanner.nextLine().trim();
        return validateX(scn);
    }

    private static int readIntLevel(Scanner scanner) throws InvalidDataException {
        System.out.print("Enter the height above sea level: ");
        String scn = scanner.nextLine().trim();
        return Integer.parseInt(scn);
    }

    private static double readDouble(Scanner scanner) throws InvalidDataException {
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