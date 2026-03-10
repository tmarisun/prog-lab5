package org.example.commands;

import org.example.Application;
import org.example.data.City;
import org.example.data.Human;
import org.example.validate.InputValidator;

import java.text.SimpleDateFormat;
import java.util.Stack;

public class FilterByGovernor implements Command {

    public FilterByGovernor(Application app) {
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
    public void execute(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: filter_by_governor <birthday (yyyy-MM-dd)>");
            return;
        }

        try {
            java.util.Date birthday = InputValidator.validateBirthday(args[1].trim());
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Stack<City> stack = Application.getCityStack();
            boolean found = false;

            for (City city : stack) {
                Human governor = city.getGovernor();
                if (governor != null
                        && fmt.format(governor.birthday()).equals(fmt.format(birthday))) {
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