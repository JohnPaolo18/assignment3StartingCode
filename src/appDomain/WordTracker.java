package appDomain;

import implementations.BSTree;
import utilities.Iterator;
import java.io.*;

/**
 * WordTracker program tracks word occurrences in one or more text files.
 * It stores the results in a binary search tree and allows generating reports in various formats.
 */
public class WordTracker {
    // Binary Search Tree to store WordInfo objects
    private static BSTree<WordInfo> tree = new BSTree<>();

    /**
     * Main method for running the WordTracker program.
     * @param args Command-line arguments specifying files to process, report format, and optional output file.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WordTracker <file1> <file2> ... -pf/-pl/-po [-f <output.txt>]");
            return;
        }

        String commandOption = null;  // Stores the report format (-pf, -pl, -po)
        String outputFile = null;    // Optional output file for the report

        // Process command-line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].equals("-pf") || args[i].equals("-pl") || args[i].equals("-po")) {
                    commandOption = args[i];
                } else if (args[i].equals("-f") && i + 1 < args.length) {
                    outputFile = args[i + 1];
                    i++; // Skip the next argument since it's the file name
                }
            } else {
                // Process input file
                processFile(args[i]);
            }
        }

        // Validate that a report format is provided
        if (commandOption == null) {
            System.out.println("Error: No valid command option provided (-pf, -pl, -po).");
            return;
        }

        // Save the updated tree to a file
        serializeTree();

        // Generate the report
        if (outputFile != null) {
            generateReportToFile(commandOption, outputFile);
        } else {
            generateReportToConsole(commandOption);
        }
    }

    /**
     * Reads a file and processes its content to add words and their occurrences to the BSTree.
     * @param filename The name of the file to process.
     */
    private static void processFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Split the line into words using non-word characters as delimiters
                String[] words = line.split("\\W+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        word = word.toLowerCase(); // Normalize to lowercase
                        WordInfo newInfo = new WordInfo(word);
                        WordInfo existingInfo = findWordInfo(word);

                        if (existingInfo == null) {
                            // Word is not in the tree, so add it
                            newInfo.addOccurrence(filename, lineNumber);
                            tree.add(newInfo);
                        } else {
                            // Word exists, so update its occurrences
                            existingInfo.addOccurrence(filename, lineNumber);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Searches the BSTree for a WordInfo object with the specified word.
     * @param word The word to search for.
     * @return The WordInfo object if found, or null if not found.
     */
    private static WordInfo findWordInfo(String word) {
        Iterator<WordInfo> iterator = tree.inorderIterator();
        while (iterator.hasNext()) {
            WordInfo info = iterator.next();
            if (info.getWord().equals(word)) {
                return info;
            }
        }
        return null;
    }

    /**
     * Restores the BSTree from a serialized file (repository.ser) if it exists.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private static void deserializeTree() {
        File file = new File("repository.ser");

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                tree = (BSTree<WordInfo>) ois.readObject();
                System.out.println("Tree restored from repository.ser.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error deserializing tree: " + e.getMessage());
            }
        } else {
            System.out.println("No existing repository found. Starting with an empty tree.");
        }
    }

    /**
     * Saves the BSTree to a serialized file (repository.ser).
     */
    private static void serializeTree() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("repository.ser"))) {
            oos.writeObject(tree);
            System.out.println("Tree saved to repository.ser.");
        } catch (IOException e) {
            System.err.println("Error serializing tree: " + e.getMessage());
        }
    }

    /**
     * Outputs the report to the console based on the specified format.
     * @param commandOption The report format (-pf, -pl, -po).
     */
    private static void generateReportToConsole(String commandOption) {
        switch (commandOption) {
            case "-pf":
                printWordsAndFiles(System.out);
                break;
            case "-pl":
                printWordsFilesAndLines(System.out);
                break;
            case "-po":
                printWordsFilesLinesAndOccurrences(System.out);
                break;
            default:
                System.out.println("Invalid command option.");
        }
    }

    /**
     * Writes the report to a file based on the specified format.
     * @param commandOption The report format (-pf, -pl, -po).
     * @param outputFile The file to write the report to.
     */
    private static void generateReportToFile(String commandOption, String outputFile) {
        try (PrintStream out = new PrintStream(new FileOutputStream(outputFile))) {
            switch (commandOption) {
                case "-pf":
                    printWordsAndFiles(out);
                    break;
                case "-pl":
                    printWordsFilesAndLines(out);
                    break;
                case "-po":
                    printWordsFilesLinesAndOccurrences(out);
                    break;
                default:
                    System.out.println("Invalid command option.");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Prints words and the files they appear in.
     * @param out The output stream to print to.
     */
    private static void printWordsAndFiles(PrintStream out) {
        Iterator<WordInfo> iterator = tree.inorderIterator();
        while (iterator.hasNext()) {
            WordInfo wordInfo = iterator.next();
            out.println("Key : ===" + wordInfo.getWord() + "=== found in files: " +
                    String.join(", ", wordInfo.getOccurrences().keySet()));
        }
    }

    /**
     * Prints words, the files they appear in, and the lines they are on.
     * @param out The output stream to print to.
     */
    private static void printWordsFilesAndLines(PrintStream out) {
        Iterator<WordInfo> iterator = tree.inorderIterator();
        while (iterator.hasNext()) {
            WordInfo wordInfo = iterator.next();
            out.println("Key : ===" + wordInfo.getWord() + "=== ");
            wordInfo.getOccurrences().forEach((file, lines) -> {
                out.println("    found in file: " + file + " on lines: " +
                        String.join(", ", lines.stream().map(String::valueOf).toArray(String[]::new)));
            });
        }
    }

    /**
     * Prints words, the files they appear in, the lines they are on, and the number of occurrences.
     * @param out The output stream to print to.
     */
    private static void printWordsFilesLinesAndOccurrences(PrintStream out) {
        Iterator<WordInfo> iterator = tree.inorderIterator();
        while (iterator.hasNext()) {
            WordInfo wordInfo = iterator.next();
            out.println("Key : ===" + wordInfo.getWord() + "=== ");
            wordInfo.getOccurrences().forEach((file, lines) -> {
                out.println("    found in file: " + file + " on lines: " +
                        String.join(", ", lines.stream().map(String::valueOf).toArray(String[]::new)));
            });
        }
    }
}
