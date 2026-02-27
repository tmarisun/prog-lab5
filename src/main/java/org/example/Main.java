package org.example;

import java.io.FileNotFoundException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if(args.length!= 1){
            System.out.println("Usage: java Main <filename>");
            return;
        }

        String fileName = args[0];
        Application app = new Application(fileName);

    }
}
