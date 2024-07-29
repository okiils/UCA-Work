/*Owen Kiilsgaard
*Object-Oriented Software Development with Java
*Why are all of these weird characters in the ParadiseLost file?
*/
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class WordCounter{
    //Let me cook
    public static void main(String[] args) {
        // Get ingredients from the store
        List<String> books = readTextFromFile();

        //Grocery List
        List<Map<String, Integer>> frequencies = new ArrayList<>();
        for (String book : books) {
            //Prep ingredients
            Map<String, Integer> wordFrequencyMap = getWordFrequencies(book);
            frequencies.add(wordFrequencyMap);
        }
        //Keeping flammable things away from the burner
        List<String> stopWords = readStopWords();
        
        // In the pan
        for (int i = 0; i < frequencies.size(); i++) {
            System.out.println("Book " + (i + 1) + " Top 20 Words:");
            printTopWords(frequencies.get(i), stopWords);
            System.out.println();
        }

        //random lines
        Random random = new Random();
        List<String> randomLines = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            randomLines.add(getRandomLine(books, random));
        }

        // scrambled like eggs
        List<String> scrambledLines = new ArrayList<>();
        for (String line : randomLines) {
            scrambledLines.add(scrambleWords(line));
        }

        // plate the eggs
        System.out.println("Scrambled Lines:");
        for (String line : scrambledLines) {
            System.out.println(line);
        }
    }
    //retrieve words
    private static List<String> readTextFromFile() {
        List<String> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ParadiseLost.txt"))) {
            StringBuilder book = new StringBuilder();
            String line;
            boolean isInBook = false;
            //weird formatting
            while ((line = br.readLine()) != null) {
                if (!isInBook && line.startsWith("Book")) {
                    isInBook = true;
                    book.append(line).append("\n");
                } else if (isInBook && line.startsWith("Book")) {
                    books.add(book.toString());
                    book.setLength(0);
                    book.append(line).append("\n");
                } else {
                    book.append(line).append("\n");
                }
            }
            books.add(book.toString()); // Add the last book
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static Map<String, Integer> getWordFrequencies(String text) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        String[] words = text.split("\\s+");
        //remove caps
        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!word.isEmpty()) {
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        return wordFrequencyMap;
    }

    private static List<String> readStopWords() {
        List<String> stopWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("stopWords.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.addAll(Arrays.asList(line.split(",")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopWords;
    }

    private static void printTopWords(Map<String, Integer> wordFrequencyMap, List<String> stopWords) {
        List<Map.Entry<String, Integer>> sortedEntries = wordFrequencyMap.entrySet()
                .stream()
                //filter the key
                .filter(entry -> !stopWords.contains(entry.getKey().toLowerCase()))
                //sort based off the value
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(20)
                //turn into list
                .toList();
        //print entries in map
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static String getRandomLine(List<String> books, Random random) {
        String randomLine = "";

        while (randomLine.isEmpty()) {
            int bookIndex = random.nextInt(books.size());
            String book = books.get(bookIndex);
            String[] lines = book.split("\n");
            int lineIndex = random.nextInt(lines.length);
            randomLine = lines[lineIndex].trim();
        }
        return randomLine;
    }

    private static String scrambleWords(String line) {
        String[] words = line.split("\\s+");
        List<String> scrambledWords = new ArrayList<>();
        // all important 2-letter words  in the stop words file
        for (String word : words) {
            if (word.length() > 3) {
                char[] characters = word.substring(1, word.length() - 1).toCharArray();
                List<Character> charList = new ArrayList<>();
                for (char c : characters) {
                    charList.add(c);
                }
                //Like this?
                Collections.shuffle(charList);
                charList.addFirst(word.charAt(0));
                charList.add(word.charAt(word.length() - 1));
                scrambledWords.add(charList.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()));
            } else {
                scrambledWords.add(word);
            }
        }
        return String.join(" ", scrambledWords);
    }
}