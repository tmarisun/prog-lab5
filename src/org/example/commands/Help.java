package org.example.commands;

import org.example.Application;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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
    public void execute(String[] args) throws FileNotFoundException {
        app.help();
    }

}