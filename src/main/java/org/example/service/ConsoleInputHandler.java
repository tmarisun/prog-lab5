package org.example.service;

import org.example.data.City;
import org.example.data.Coordinates;
import org.example.data.Human;
import org.example.data.Climate;
import org.example.data.Government;
import org.example.data.StandardOfLiving;
import org.example.validate.CityValidator;
import org.example.exceptions.InvalidDataException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class ConsoleInputHandler {

    public static City readCityFromConsole(Long predefinedId) throws InvalidDataException {
        Scanner scanner = new Scanner(System.in);
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            try {
                System.out.println("=== Entering city data (attempt " + (attempts + 1) + ") ===");

                String name = readString(scanner, "Enter the name of the city: ");

                System.out.println("Enter the coordinates:");
                float x = readFloat(scanner, "  X: ");
                double y = readDouble(scanner, "  Y: ");
                Coordinates coordinates = new Coordinates(x, y);

                double area = readDouble(scanner, "Enter the area of the city: ");
                int population = readInt(scanner, "Enter the city's population: ");
                int metersAboveSeaLevel = readInt(scanner, "Enter the height above sea level: ");

                Climate climate = readEnum(scanner, "Select the climate (Enter to skip): ",
                        Climate.values(), true);
                Government government = readEnum(scanner, "Choose a form of government: ",
                        Government.values(), false);
                StandardOfLiving standardOfLiving = readEnum(scanner,
                        "Select the standard of living (Enter to skip): ",
                        StandardOfLiving.values(), true);

                Human governor = null;
                if (readYesNo(scanner, "Add a governor? (y/n): ")) {
                    LocalDate birthday = readDate(scanner, "Введите дату рождения (yyyy-MM-dd): ");
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


    private static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static float readFloat(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return Float.parseFloat(scanner.nextLine().trim());
    }

    private static double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static <T extends Enum<T>> T readEnum(Scanner scanner, String prompt,
                                                  T[] values, boolean nullable) {
        System.out.println(prompt);
        for (int i = 0; i < values.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, values[i].name());
        }
        if (nullable) {
            System.out.println("  0. Skip (null)");
        }

        System.out.print("Your choice: ");
        String input = scanner.nextLine().trim();

        if (nullable && (input.isEmpty() || input.equals("0"))) {
            return null;
        }

        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < values.length) {
                return values[index];
            }
        } catch (NumberFormatException ignored) {}

        try {
            return Enum.valueOf((Class<T>) values[0].getClass(), input.toUpperCase());
        } catch (IllegalArgumentException ignored) {}

        return null;
    }

    private static boolean readYesNo(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes") || input.equals("да");
    }

    private static LocalDate readDate(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return LocalDate.parse(scanner.nextLine().trim());
    }
}