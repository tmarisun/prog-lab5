package org.example.commands;

import java.util.Scanner;

public interface Command {

    String getName();
    String getDescription();

    void execute(Scanner scanner, String[] args);
}
