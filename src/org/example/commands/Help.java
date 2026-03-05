package org.example.commands;

import org.example.Application;

import java.util.Scanner;

public class Help implements Command {
    private final Application app;

    public Help(Application app) {
        this.app = app;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Output help for available commands";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("""
        Available commands:
        help                                            : Show this help message
        info                                            : Show information about the collection
        show                                            : Show all elements of the collection
        add                                             : Add a new element to the collection
        update id {element}                             : Update an element by its ID
        remove_by_id id                                 : Remove an element by its ID
        clear                                           : Clear the collection
        save                                            : Save the collection to a file
        execute_script file_name                        : Execute a script from a file
        exit                                            : Exit the program (without saving)
        insert_at index {element}                       : Insert an element at the specified position
        add_if_max {element}                            : Add an element if its value is greater than the maximum
        sort                                            : Sort the collection in natural order
        count_less_than_standard_of_living value        : Count elements with standardOfLiving less than the specified value
        filter_by_governor birthday                     : Filter elements by governor's birthday
        print_field_ascending_standard_of_living        : Print standardOfLiving values in ascending order
        """);

    }
}