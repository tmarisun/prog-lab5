package org.example.manager;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;
import org.example.Application;

import java.util.Scanner;

public class ManagerCommands {
    private final Map<String, Command> commands = new HashMap<>();

    public ManagerCommands(Application app) {
        register(new Help(app, this));
        register(new Info(app));
        register(new Show(app));
        register(new Add(app));
        register(new Update(app));
        register(new RemoveById(app));
        register(new Clear(app));
        register(new Save(app));
        register(new ExecuteScript(app, this));
        register(new Exit(app));
        register(new InsertAt(app));
        register(new AddIfMax(app));
        register(new Sort(app));
        register(new CountLessThanStandardOfLiving(app));
        register(new FilterByGovernor(app));
        register(new PrintFieldAscendingStandardOfLiving(app));
    }

    private void register(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public Iterable<Command> getAllCommands() {
        return commands.values();
    }

    public void callCommand(String[] commandParts) {
        if (commandParts.length == 0) {
            return;
        }

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