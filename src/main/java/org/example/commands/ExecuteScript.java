package org.example.commands;

import org.example.Application;
import org.example.manager.ManagerCommands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExecuteScript implements Command {
    private final Application app;
    private final ManagerCommands managerCommands;

    // Защита от рекурсии (чтобы скрипт не вызывал сам себя бесконечно)
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
    public void execute(Scanner scanner, String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: execute_script <file_name>");
            return;
        }

        String fileName = args[1].trim();
        File file = new File(fileName);

        // Проверка существования файла
        if (!file.exists()) {
            System.err.println("File not found: " + fileName);
            return;
        }

        if (!file.canRead()) {
            System.err.println("Cannot read file: " + fileName);
            return;
        }

        // Получаем абсолютный путь для защиты от рекурсии
        String absolutePath = file.getAbsolutePath();

        // Проверка на рекурсию
        if (executingScripts.contains(absolutePath)) {
            System.err.println("Recursion detected! Script cannot call itself: " + fileName);
            return;
        }

        executingScripts.add(absolutePath);

        try (Scanner fileScanner = new Scanner(file, "UTF-8")) {
            System.out.println("Executing script: " + fileName);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                // Пропускаем пустые строки и комментарии
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                System.out.println("$ " + line);

                // Разбиваем команду на части
                String[] commandParts = line.split("\\s+", 2);

                managerCommands.callCommand(scanner, commandParts);
            }

            System.out.println("Script execution completed: " + fileName);

        } catch (FileNotFoundException e) {
            System.err.println("Script file not found: " + fileName);
        } catch (Exception e) {
            System.err.println("Error executing script: " + e.getMessage());
        } finally {
            executingScripts.remove(absolutePath);
        }
    }
}