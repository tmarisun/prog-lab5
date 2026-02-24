package org.example;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static void main(String[] args) {
        if(args.length!= 1){
            System.out.println("Usage: java Main <filename>");
            return;
        }

        String filename = args[0];
        try {
            Scene scene = new Scene(filename);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
}
