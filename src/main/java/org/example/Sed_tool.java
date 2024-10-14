package org.example;

import java.io.*;
import java.util.Scanner;


public class Sed_tool {
    public static void main(String[] args) {
        System.out.println("::::   Welcome to SED TOOL  ");
        while (true) {

            System.out.println("Replace in text :: ccsed s/<search>/<replace>/g <filename> ");
            System.out.println("Exit program    :: Exit ");
            System.out.println("---------------------------------------------------------");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("THANKS :)");
                System.exit(1);
            }
            String[] partsInput = userInput.split(" ");

            String command = partsInput[1];
            String filename = partsInput[2];

            String[] commandParts = command.split("/");
            if (commandParts.length != 4 || !commandParts[0].equals("s") || !commandParts[3].equals("g")) {
                System.err.println("Invalid command format. Expected: s/<search>/<replace>/g");
                System.exit(1);
            }

            String search = commandParts[1];
            String replace = commandParts[2];
            try (BufferedReader reader = new BufferedReader(new FileReader("src/test/java/"+filename));
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/java/output.txt"))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.replace(search, replace);
                        line = line.replace("\"", ""); // Remove double quotes
                        writer.write(line);
                        writer.newLine();
                    }
                    System.out.println("");
                    System.out.println("\u001B[32mNOTE- Please check output.txt file for output\u001B[0m");
            } catch (FileNotFoundException e){
                System.out.println("\u001B[31mError- Please enter correct file name \u001B[0m");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}