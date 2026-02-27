package org.example;

import org.example.information.City;

import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

import static org.example.ConsoleInputHandler.readCityFromConsole;

public class Application2 {

    public void run(Stack<City> cityStack) {
        System.out.println("City Management System initialized. Type 'help' for commands.");
        while (true) {
            System.out.print("\n> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1].trim() : "";

            try {
                switch (command) {
                    case "help":
                        printHelp();
                        break;
                    case "info":
                        printInfo(cityStack);
                        break;
                    case "show":
                        showCities(cityStack);
                        break;
                    case "add":
                        addCity(null, cityStack);
                        break;
                    case "update":
                        handleUpdate(args);
                        break;
                    case "remove_by_id":
                        handleRemoveById(args);
                        break;
                    case "clear":
                        cityStack.clear();
                        System.out.println("Collection cleared.");
                        break;
                    case "save":
                        saveCollection();
                        break;
                    case "execute_script":
                        executeScript(args);
                        break;
                    case "exit":
                        System.out.println("Exiting without saving...");
                        return;
                    case "insert_at":
                        handleInsertAt(args);
                        break;
                    case "add_if_max":
                        addIfMax();
                        break;
                    case "sort":
                        sortCollection();
                        break;
                    case "count_less_than_standard_of_living":
                        countLessThanStandard(args);
                        break;
                    case "filter_by_governor":
                        filterByGovernor(args);
                        break;
                    case "print_field_ascending_standard_of_living":
                        printStandardOfLivingAscending();
                        break;
                    default:
                        System.err.println("Unknown command. Type 'help' for available commands.");
                }
            } catch (Exception e) {
                System.err.println("Command execution error: " + e.getMessage());
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                Available commands:
                help                                            : Show this help message
                info                                            : Show collection metadata
                show                                            : Display all cities
                add                                             : Add a new city (interactive input)
                update id {element}                             : Update city with specified ID
                remove_by_id id                                 : Remove city by ID
                clear                                           : Clear the entire collection
                save                                            : Save collection to file
                execute_script file_name                        : Execute commands from a script file
                exit                                            : Exit the application (without saving)
                insert_at index {element}                       : Insert city at specified position
                add_if_max {element}                            : Add city only if it's the largest by ID
                sort                                            : Sort collection by ID (natural order)
                count_less_than_standard_of_living value        : Count cities with lower standard of living
                filter_by_governor birthday                     : Filter cities by governor's birthday (yyyy-MM-dd)
                print_field_ascending_standard_of_living        : Print standard of living values in ascending order
                """);
    }

    private void printInfo(Stack<City>cityStack) {
        System.out.println("Collection type: java.util.Stack");
        System.out.println("Initialization date: " + new Date());
        System.out.println("Number of elements: " + cityStack.size());
        int nextId = 0;
        for(City city : cityStack) {
            if(city.getId() > nextId) {
                nextId = city.getId();
            }
        }
        nextId++;
        System.out.println("Next auto-generated ID: " + nextId);
    }

    private void showCities(Stack<City>cityStack) {
        if (cityStack.isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        for (City city : cityStack) {
            System.out.println(city);
        }
    }

    private void addCity(Long predefinedId, Stack<City>cityStack) {
        try {
            City city = readCityFromConsole(predefinedId);
            cityStack.push(city);
            System.out.println("City added successfully (ID: " + city.getId() + ")");
        } catch (Exception e) {
            System.err.println("Failed to add city: " + e.getMessage());
        }
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.err.println("Value cannot be empty. Please try again.");
        }
    }

    private float readFloatWithMax(String prompt, float max) {
        while (true) {
            System.out.print(prompt);
            try {
                Scanner scanner = new Scanner(System.in);
                float value = Float.parseFloat(scanner.nextLine().trim());
                if (value <= max) return value;
                System.err.println("Value must not exceed " + max);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please enter a valid float.");
            }
        }
    }

}
