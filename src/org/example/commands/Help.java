package org.example.commands;

import org.example.Application;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Help implements Command {
    private final Application app;
    public final Map<String, Command> commands;

    public Help(Application app, Map<String, Command> commands) {
        this.app = app;
        this.commands = commands;
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
    public void execute(String[] args) throws FileNotFoundException {

    }

    public void execute(List<String> args) {
        if (args != null) throw new IllegalArgumentException("Command 'help' does not accept any arguments.");
        app.help(this.commands);
    }

}