package org.example.manager;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;
import org.example.Application;

import java.util.Scanner;

public class ManagerCommands {
    private Map<String, Command> commands = new HashMap<>();

    public ManagerCommands(Application app) {
        commands.put("Help", new Help(app));
        commands.put("Info", new Info(app));
        commands.put("Show", new Show(app));
        commands.put("Add", new Add(app));
        commands.put("Uodate", new Update(app));
        commands.put("RemoveById", new RemoveById(app));
        commands.put("Clear", new Clear(app));
        commands.put("Save", new Save(app));
        commands.put("ExecuteScript", new ExecuteScript(app, this));
        commands.put("Exit", new Exit(app));
        commands.put("InsertAt", new InsertAt(app));
        commands.put("AddIfMax", new AddIfMax(app));
        commands.put("Sort", new Sort(app));
        commands.put("CountLessThanStandardOfLiving", new CountLessThanStandardOfLiving(app));
        commands.put("FilterByGovernor", new FilterByGovernor(app));
        commands.put("PrintFieldAscendingStandardOfLiving", new PrintFieldAscendingStandardOfLiving(app));
    }

    public void callCommand(String[] commandParts) {
        String commandName = commandParts[0].toLowerCase();

        Command command = commands.get(commandName);

        if (command != null) {
            try {
                command.execute(commandParts);
            } catch (Exception e) {
                System.err.println("Error when executing the command: " + e.getMessage());
            }
        } else {
            System.err.println("Unidentified command: " + commandName);
            System.err.println("Enter 'help' for reference.");
        }
    }


}
