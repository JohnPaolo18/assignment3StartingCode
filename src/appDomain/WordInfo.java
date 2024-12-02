package appDomain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents information about a word, including the files and line numbers where it occurs.
 * Implements Serializable for saving to disk and Comparable for tree sorting.
 */
public class WordInfo implements Serializable, Comparable<WordInfo> {
    private static final long serialVersionUID = 1L; // For consistent serialization
    private String word; // The word being tracked
    private Map<String, Set<Integer>> occurrences = new HashMap<>(); // Tracks files and line numbers where the word occurs

    /**
     * Constructor to initialize a WordInfo object with the specified word.
     * @param word The word being tracked.
     */
    public WordInfo(String word) {
        this.word = word;
    }

    /**
     * Adds an occurrence of the word in a specified file and line number.
     * @param filename The file where the word occurs.
     * @param lineNumber The line number where the word occurs.
     */
    public void addOccurrence(String filename, int lineNumber) {
        // Adds the line number to the set of occurrences for the given file
        this.occurrences.computeIfAbsent(filename, k -> new HashSet<>()).add(lineNumber);
    }

    /**
     * Gets the word being tracked.
     * @return The word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Gets the map of occurrences for the word.
     * @return A map where keys are filenames and values are sets of line numbers.
     */
    public Map<String, Set<Integer>> getOccurrences() {
        return occurrences;
    }

    /**
     * Compares this WordInfo object to another for sorting.
     * @param other The other WordInfo object to compare.
     * @return A negative, zero, or positive value based on lexicographical order of words.
     */
    @Override
    public int compareTo(WordInfo other) {
        return this.word.compareTo(other.word);
    }

    /**
     * Generates a hash code for the WordInfo object.
     * @return The hash code based on the word.
     */
    @Override
    public int hashCode() {
        return word.hashCode();
    }

    /**
     * Checks equality between this WordInfo object and another object.
     * @param obj The object to compare.
     * @return True if the other object is a WordInfo with the same word, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WordInfo wordInfo = (WordInfo) obj;
        return word.equals(wordInfo.word);
    }

    /**
     * Converts the WordInfo object to a readable string format.
     * @return A string representation of the word, including file and line occurrences.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(word).append(": ");
        occurrences.forEach((file, lines) -> {
            builder.append("\n    ").append(file).append(" on lines ").append(lines);
        });
        return builder.toString();
    }
}
