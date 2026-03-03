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
        try {
            Application app = new Application(fileName);
            Scene scene = new Scene(app);
            scene.run();
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
