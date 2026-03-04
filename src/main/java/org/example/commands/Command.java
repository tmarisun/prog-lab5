package org.example.commands;

public interface Command {

    String getName();
    String getDescription();

    void execute(String[] args);
}
