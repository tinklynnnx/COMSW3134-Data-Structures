import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* Sources used:
https://stackoverflow.com/questions/28929643/understanding-how-bufferedreader-works-in-java
https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
https://www.geeksforgeeks.org/hashset-in-java/
https://www.geeksforgeeks.org/introduction-to-disjoint-set-data-structure-or-union-find-algorithm/
https://www.hackerearth.com/practice/notes/disjoint-set-union-union-find/
https://stackoverflow.com/questions/59841921/how-do-i-use-union-find-data-structure-to-group-strings
https://www.geeksforgeeks.org/split-string-java-examples/
https://stackoverflow.com/questions/16265693/how-to-use-bufferedreader-in-java
https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
*/
public class WordReplacer {
    //Stores word replacements: key is a word and value is its replacement
    //i.e. replacementWords("put","in") -> changes put to in
    //The map implementation (BST, RBT, or HashMap) is chosen later
    private static MyMap<String, String> replacementWords;

    /**
     * Main method that uses the input and replacement files and applies the word replacements
     *
     * @param args the command line arguments where:
     *             args[0] - input text file; args[1] - word replacements file; args[2] - type of data structure (bst|rbt|hash) used for storing replacements
     *             i.e. WordReplacer sample_input.txt sample_replacements.txt hash
     */
    public static void main(String[] args) {
        //The number of arguments supplied is incorrect: Throws usage error message
        //"Usage: java WordReplacer <input text file> <word replacements file> <bst|rbt|hash>"
        if (args.length != 3) {
            System.err.println("Usage: java WordReplacer <input text file> <word replacements file> <bst|rbt|hash>");
            System.exit(1);
        }
        String inputFile = args[0];
        String replacementsFile = args[1];
        String dataStructure = args[2];
        // convert inputType to all Lowercase in the case of a typo or that commander uses Uppercase
        String fixed = dataStructure.toLowerCase();
        // in the case that if "bst" is specified
        if (fixed.equals("bst")) {
            replacementWords = new BSTreeMap<>();
            // in the case that if "rbt" is specified
        } else if (fixed.equals("rbt")) {
            replacementWords = new RBTreeMap<>();
            // in the case that if "hash" is specified
        } else if (fixed.equals("hash")) {
            replacementWords = new MyHashMap<>();
        } else {
            // Handle invalid input types by printing inputType and exit
            // "Error: Invalid data structure '" + dataStructure + "' received."
            System.err.println("Error: Invalid data structure '" + dataStructure + "' received.");
            System.exit(1);
        }
        try {
            // https://stackoverflow.com/questions/16265693/how-to-use-bufferedreader-in-java
            // https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
            try (BufferedReader reader = new BufferedReader(new FileReader(replacementsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //https://www.geeksforgeeks.org/split-string-java-examples/
                    //https://stackoverflow.com/questions/41953388/java-split-and-trim-in-one-shot
                    String[] parts = line.split(" -> ", 2); // Split into at most 2 parts (word getting replace and what it replaces into)
                    String from = parts[0].trim();
                    String to = parts[1].trim();
                    //detect a Cycle within the given data
                    // Given example: Alice -> Bob -> Carly -> Alice
                    // "Error: Cycle detected when trying to add replacement rule: " + a + " -> " + b
                    if (union(from, to)) {
                        System.err.println("Error: Cycle detected when trying to add replacement rule: " + from + " -> " + to);
                        System.exit(1);
                    }
                    // Find the final replacement for the word
                    String finalReplacement = find(from);
                    replacementWords.put(from, finalReplacement);
                }
            }
            //Handle if word replacement file does not exist: Print unable to open error message with file name
            //"Error: Cannot open file '" + wordReplacementsFile + "' for input."
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot open file '" + replacementsFile + "' for input.");
            System.exit(1);
            //Handle if word replacement file does not read properly: Print i/o error message with file name
            //"Error: An I/O error occurred reading '" + wordReplacementsFile + "'."
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred reading '" + replacementsFile + "'.");
            System.exit(1);
        }

        try {
            // Process the input file and apply the replacements
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                StringBuilder output = new StringBuilder();
                StringBuilder currentWord = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    char ch = (char) c;
                    if (Character.isLetter(ch)) {
                        // If current character is a letter -> append it to the current word
                        currentWord.append(ch);
                    } else {
                        // If the current character is not a letter -> replace the word if it's not empty
                        if (currentWord.length() > 0) {
                            String replacedWord = find(currentWord.toString());
                            output.append(replacedWord);
                            currentWord.setLength(0); // Reset the current word builder
                        }
                        output.append(ch); // Append the non-letter character (e.g., space, punctuation, -, @, etc.)
                    }
                }
                if (currentWord.length() > 0) {
                    String replacedWord = find(currentWord.toString());
                    output.append(replacedWord);
                }
                // Check for any remaining word
                System.out.printf("%s\n", output.toString());
            }
            //Handle if input text file does not exist: Print unable to open error message with file name
            //"Error: Cannot open file '" + inputTextFile + "' for input."
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot open file '" + inputFile + "' for input.");
            System.exit(1);
            //Handle if input text file does not read properly: Print i/o error message with file name
            //"Error: An I/O error occurred reading '" + inputTextFile + "'."
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred reading '" + inputFile + "'.");
            System.exit(1);
        }
    }

    /**
     * uses union-find algorithm to efficiently find the root containing the specified word.
     *
     * @param word: word whose root is to be found
     * @return root of the path containing the word
     */
    public static String find(String word) {
        String current = word;
        String previous = replacementWords.get(current);
        while (previous != null && !previous.equals(current)) {
            current = previous;
            previous = replacementWords.get(current);
        }
        String root = current;
        current = word;
        // Path compression
        while (!current.equals(root)) {
            String next = replacementWords.get(current);
            replacementWords.put(current, root);
            current = next;
        }
        return root;
    }

    /**
     * Method to assist the detection of a cycle
     * Why Union?
     * https://www.geeksforgeeks.org/detect-cycle-in-graph-using-dsu/
     * https://stackoverflow.com/questions/61167751/can-we-detect-cycles-in-directed-graph-using-union-find-data-structure
     * **Given parameters to import only from a selected number of packages
     *
     * @param current First word in a path tested on, the word currently being visited
     * @param target  last word in a path, the word being searched for
     * @return true if 2 word are the same (current & target word), false otherwise.
     */
    public static boolean union(String current, String target) {
        // If both words already have the same root, a cycle is detected
        if (find(current).equals(find(target))) {
            return true; // Cycle detected
        }
        // Otherwise -> link root1 to root2
        replacementWords.put(find(current), find(target));
        return false;
    }

}