package org.example.commands;

import org.example.Application;
import org.example.data.City;
import org.example.data.Human;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Stack;

public class FilterByGovernor implements Command {
    private final Application app;

    public FilterByGovernor(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "filter_by_governor";
    }

    @Override
    public String getDescription() {
        return "Filter elements by governor's birthday";
    }

    @Override
    public void execute(Scanner scanner, String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: filter_by_governor <birthday (yyyy-MM-dd)>");
            return;
        }

        try {
            LocalDate birthday = LocalDate.parse(args[1].trim());
            Stack<City> stack = app.getCityStack();
            boolean found = false;

            for (City city : stack) {
                Human governor = city.getGovernor();
                if (governor != null && governor.getBirthday().equals(birthday)) {
                    System.out.println(city);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No cities found with governor born on " + birthday);
            }

        } catch (Exception e) {
            System.err.println("Invalid date format. Use yyyy-MM-dd");
        }
    }
}