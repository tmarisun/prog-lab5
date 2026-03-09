package org.example;

public class Main {

    public static void main(String[] args) {
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
            System.err.println("Error: " + e.getMessage());
        }
    }
}