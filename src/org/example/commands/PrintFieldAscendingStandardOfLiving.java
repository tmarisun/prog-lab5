package org.example.commands;

import org.example.Application;
import org.example.data.City;
import org.example.data.StandardOfLiving;

import java.util.*;
import java.util.stream.Collectors;

public class PrintFieldAscendingStandardOfLiving implements Command {

    public PrintFieldAscendingStandardOfLiving(Application app) {
    }

    @Override
    public String getName() {
        return "print_field_ascending_standard_of_living";
    }

    @Override
    public String getDescription() {
        return "Print standardOfLiving values in ascending order";
    }

    @Override
    public void execute(String[] args) {
        Stack<City> stack = Application.getCityStack();

        List<StandardOfLiving> values = stack.stream()
                .map(City::getStandardOfLiving)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(StandardOfLiving::getRank))
                .toList();

        if (values.isEmpty()) {
            System.out.println("No standard of living values available.");
            return;
        }

        System.out.println("Standard of living values in ascending order:");
        for (StandardOfLiving sol : values) {
            System.out.println(sol);
        }
    }
}