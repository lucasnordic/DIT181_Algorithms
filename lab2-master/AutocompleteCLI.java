/******************************************************************************
 *  Compilation:  javac AutocompleteCLI.java
 *  Execution:    java  AutocompleteCLI dictionary.txt max-matches
 *  Dependencies: Autocomplete.java Term.java
 *    
 *  @author Peter Ljunglöf
 *  @author Christian Sattler
 *    
 *  Interactive program to demonstrate the Autocomplete class.
 *
 *     * Reads a list of terms and weights from a file, specified as a
 *       program argument.
 *
 *     * When the user enters a string, it displays the top max-matches
 *       terms that start with the text that the user typed.
 *
 ******************************************************************************/

import java.util.Scanner;
import java.util.Arrays;

import java.nio.file.Files;
import java.nio.file.Paths;

class AutocompleteCLI {
    public static String dictfile = "";

    public static void main(String[] args) {
        // If not enough program arguments are given, display the usage.
        if (args.length < 2) {
            System.err.println("Usage: you have to provide two program arguments:");
            System.err.println("  (1) the path to a dictionary file");
            System.err.println("  (2) the maximum number of matches that will be displayed");
            System.exit(1);
        }

        Term[] dictionary = null;
        int max_matches = 0;
        String[] prefixes = null;

        while (true) {
            // Load dictionary file specified in first program argument.
            // OLD: String dictfile = args[0];
            String dictfile = dictChoice();
            try {
                dictionary = Files.lines(Paths.get(dictfile))
                        .map(line -> line.trim().split("\\s+"))
                        .map(pair -> new Term(pair[1], Long.valueOf(pair[0])))
                        .toArray(size -> new Term[size]);
            } catch (Exception e) {
                System.err.println("I failed to read the dictionary file.");
                e.printStackTrace();
                System.exit(1);
            }

            // Parse maximum number of matches specified in second program argument.
            try {
                max_matches = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.err.println("I failed to parse the maximum number of matches to display.");
                e.printStackTrace();
                System.exit(1);
            }

            // Print some help.
            System.out.println("Loaded dictionary " + dictfile + " containing " + dictionary.length + " words");
            System.out.println("Maximum number of matches to display: " + max_matches);

            Autocomplete autocomplete = new Autocomplete(dictionary);

            // The main REPL (read-eval-print loop)
            System.out.println("Enter search prefixes line-by-line.");
            System.out.println();
            Scanner input = new Scanner(System.in);

            int counter = 0;
            while (true) {
                if (counter <= 0) {
                    System.out.println("Enter a prefix, or enter 1337 to go back to the menu");
                }
                counter++;

                // Read prefix from input line, exit if there is no more input.
                if (!input.hasNextLine())
                    break;
                String prefix = input.nextLine();

                if (prefix.equals("1337")) {
                    break;
                }

                // Print the number of matches, find all matches, and print the top-most ones.
                int nrMatches = autocomplete.numberOfMatches(prefix);
                System.out.println("Number of matches for prefix " + prefix + ": " + nrMatches);
                Term[] results = autocomplete.allMatches(prefix);
                for (int i = 0; i < Math.min(max_matches, results.length); i++)
                    System.out.println(results[i]);
                System.out.println("");
            }
        }
    }

    public static String dictChoice() {
        String chosenDict = "";

        System.out.print("Which Dictionary do you want to load? " +
                "\n[1]cities" +
                "\n[2]gp2011" +
                "\n[3]nordsamiska" +
                "\n[4]romaner" +
                "\n[5]wiktionary" +
                "\n\nChoice:"
                );

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        while(choice < 1 || choice > 5) {
            System.out.print("Please type a number between 1-5:");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        switch (choice){
            case 1 -> chosenDict = "dictionaries/cities.txt";
            case 2 -> chosenDict = "dictionaries/gp2011.txt";
            case 3 -> chosenDict = "dictionaries/nordsamiska.txt";
            case 4 -> chosenDict = "dictionaries/romaner.txt";
            case 5 -> chosenDict = "dictionaries/wiktionary.txt";
            default -> System.out.println("What");
        }

        return chosenDict;
    }
}
