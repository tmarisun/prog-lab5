package org.example.commands;

import org.example.Application;
import org.example.Scene;
import org.example.manager.ManagerCommands;
import org.example.service.CityReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExecuteScript implements Command {
    private final Application app;
    private final ManagerCommands managerCommands;

    private static final List<String> executingScripts = new ArrayList<>();

    public ExecuteScript(Application app, ManagerCommands managerCommands) {
        this.app = app;
        this.managerCommands = managerCommands;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "Execute a script from a file";
    }

    @Override
    public void execute(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            System.err.println("Usage: execute_script <file_name>");
            return;
        }

        String fileName = args[1].trim();
        File file = new File(fileName);

        if (!file.exists()) {
            System.err.println("File not found: " + fileName);
            return;
        }

        if (!file.canRead()) {
            System.err.println("Cannot read file: " + fileName);
            return;
        }

        String absolutePath = file.getAbsolutePath();

        if (executingScripts.contains(absolutePath)) {
            System.err.println("Recursion detected! Script cannot call itself: " + fileName);
            return;
        }

        executingScripts.add(absolutePath);
        Scanner fileScanner = new Scanner(file, "UTF-8");
        CityReader.setScanner(fileScanner);
        Scene.run();
        CityReader.setScanner(new Scanner(System.in));
    }
}