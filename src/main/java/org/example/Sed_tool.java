package org.example;

import java.io.*;
import java.util.Scanner;


public class Sed_tool {
    public static void main(String[] args) {
        System.out.println("::::   Welcome to SED TOOL  ");
        System.out.println("Replace in text           :: ccsed s/<search>/<replace>/g <filename> ");
        System.out.println("output a range of lines   :: ccsed '<startLineNo>,<endLineNo>p' <filename> ");
        System.out.println("output a specific pattern :: ccsed -n /pattern/p <filename> ");
        System.out.println("support double spacing    :: ccsed G <filename>");
        System.out.println("edit in place             :: ccsed -i 's/<search>/<replace>/g' <filename>");
        System.out.println("Exit program              :: Exit ");

        while (true) {

            System.out.println("---------------------------------------------------------");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("THANKS :)");
                System.exit(1);
            }
            String[] partsInput = userInput.split(" ");

            boolean Contains_n = false;
            boolean Contains_G = false;
            int patternIndex = 0;
            if (userInput.contains("-n")) {
                Contains_n = true;
                patternIndex++;
            } else if (userInput.contains(("G"))) {
                Contains_G = true;
            } else if (userInput.contains("-i")) {
                patternIndex++;
            }

            String command = partsInput[patternIndex + 1];
            String filename = partsInput[patternIndex + 2];

            try (BufferedReader reader = new BufferedReader(new FileReader("src/test/java/" + filename));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/java/output.txt"))) {

                if (Contains_n) {
                    if (command.contains(",")) {
                        int currentLine = 1;
                        String lineRange = command.replaceAll("[\'\"]", "");
                        String[] rangeParts = lineRange.split(",");
                        if (rangeParts.length != 2) {
                            System.err.println("Invalid line range format. Use 'startLine,endLine'.");
                            System.exit(1);
                        }
                        String line;
                        int startLine = Integer.parseInt(rangeParts[0]);
                        int endLine = Integer.parseInt(rangeParts[1].replace("p", ""));

                        while ((line = reader.readLine()) != null) {
                            if (currentLine >= startLine && currentLine <= endLine) {
                                writer.write(line);
                                writer.newLine();
                            }
                            currentLine++;
                        }
                    } else {
                        String[] commandParts = command.split("/");
                        String search = commandParts[1];
                        String replace = commandParts[2];
                        String line;
                        while ((line = reader.readLine()) != null) {
                            line = line.replace(search, replace);
                            line = line.replace("\"", "");
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                } else if (Contains_G) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                        writer.newLine();
                    }
                } else {
                    String line;
                    String[] commandParts = command.split("/");
                    String search = commandParts[1];
                    String replace = commandParts[2];
                    while ((line = reader.readLine()) != null) {
                        line = line.replace(search, replace);
                        line = line.replace("\"", ""); // Remove double quotes
                        writer.write(line);
                        writer.newLine();
                    }
                }

                System.out.println("");
                System.out.println("\u001B[32mNOTE- Please check output.txt file for output\u001B[0m");
            } catch (FileNotFoundException e) {
                System.out.println("\u001B[31mError- Please enter correct file name \u001B[0m");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}