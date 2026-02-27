package org.example.manager;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;
import org.example.Application;

import java.util.Scanner;

public class ManagerCommands {
    private Map<String, Command> commands = new HashMap<>();

    public ManagerCommands(Application app) {
        addCommand(new Help(app));
        addCommand(new Info(app));
        addCommand(new Show(app));
        addCommand(new Add(app));
        addCommand(new Update(app));
        addCommand(new RemoveById(app));
        addCommand(new Clear(app));
        addCommand(new Save(app));
        addCommand(new ExecuteScript(app, this));
        addCommand(new Exit(app));
        addCommand(new InsertAt(app));
        addCommand(new AddIfMax(app));
        addCommand(new Sort(app));
        addCommand(new CountLessThanStandardOfLiving(app));
        addCommand(new FilterByGovernor(app));
        addCommand(new PrintFieldAscendingStandardOfLiving(app));
    }


    private void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void callCommand(Scanner scanner, String[] commandParts) {
        String commandName = commandParts[0].toLowerCase();

        Command command = commands.get(commandName);

        if (command != null) {
            try {
                command.execute(scanner, commandParts);
            } catch (Exception e) {
                System.err.println("Error when executing the command: " + e.getMessage());
            }
        } else {
            System.err.println("Unidentified command: " + commandName);
            System.err.println("Enter 'help' for reference.");
        }
    }

    public void printHelp() {
        System.out.println("Доступные команды:");
        commands.values().forEach(cmd ->
                System.out.printf("  %-40s : %s%n", cmd.getName(), cmd.getDescription())
        );
    }


}
