package org.example.commands;

import java.io.FileNotFoundException;


public interface Command {

    String getName();

    String getDescription();

    void execute(String[] args) throws FileNotFoundException;
}