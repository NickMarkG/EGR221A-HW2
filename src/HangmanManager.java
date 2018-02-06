/*
        @Author Nicholas Gaultney, Mikyung Han
        Created: 1/26/18
        Last_Updated: 1/29/18

"Whether you eat or drink, or in everything that
you do, do it for the glory of God."
*/

/*
HangmanManager is a class that is utilized by HangmanMain. Its purpose is to store, test and locate values
which are given by the user in HangmanMain. This class cheats by not choosing a word, but instead choosing every word
with the guessed letter sequence.
 */

import java.util.*;

public class HangmanManager {
    private int wordLength;
    private int guessCount;
    private List<Character> guesses;
    private Collection<String> possibleWords;
    private StringBuilder currentPattern;

    // Initializes the HangmanManager variables
    public HangmanManager(Collection<String> dictionary, int length, int max){
        // Ensures that valid starting conditions are met
        if (length < 1 || max < 0){throw new IllegalArgumentException("Check length or max input");}                                                   // Ensures that valid starting conditions are met

        wordLength = length;
        guessCount = max;
        // Initialize an ArrayList with 26 nulls to represent alphabetical ordering
        guesses = new ArrayList<>(Collections.nCopies(26, null));
        possibleWords = new TreeSet<>();
        currentPattern = new StringBuilder("");

        // Make a pattern of dashes w/o spaces
        for (int i = 0; i < length; i++){                                                                                   // Updates currentPatter with length number of '-'
            currentPattern.append('-');
        }

        // Add all words of the requested length from the dictionary
        for(String word : dictionary) {
            //System.out.println(word.length());
            if(word.length() == wordLength) {possibleWords.add(word);}                                                     // Loops through dictionary and takes words of requested length
        }
    }

    public Set<String> words(){
        return new TreeSet<>(possibleWords);
    }

    public int guessesLeft(){
        return guessCount;
    }

    // This method takes the values from 'guesses' and adds them to a TreeSet
    public SortedSet<Character> guesses(){
        SortedSet<Character> sortedGuesses = new TreeSet<>();       // FIXME: Collection interface?

        for(Character index : this.guesses){
            if (index == null){ continue; }                                                                             // Comparison prevents null values from being added to the SortedSet
            sortedGuesses.add(index);
        }

        return sortedGuesses;
    }

    // Returns current guesses with spaces in between the letters
    public String pattern(){
        // Creates a temporary String with the first letter of currentPattern
        StringBuilder patternWithSpaces = new StringBuilder();
        patternWithSpaces.append(currentPattern.charAt(0));

        // Loops through currentPattern characters and appends them to patternWithSpaces along with separating spaces
        for(int i = 1; i < currentPattern.length(); i++){
            patternWithSpaces.append(" ");
            patternWithSpaces.append(currentPattern.charAt(i));
        }

        return patternWithSpaces.toString();
    }

    // Finds all instances of a char in a String and returns a string in "-c--" format
    //where '-' is any non-'c' char
    private String wordToKey(String word, char character){
        String key = "";

        for(int i = 0; i < word.length(); i++){
            //(word.charAt(i) == character) ? (key += character) : (key += '-');    TODO: Figure out why this is a bug

            if(word.charAt(i) == character){
                key += character;
            } else {
                key += '-';
            }
        }
        return key;
    }

    // Finds the key with the largest LinkedList size value and returns its key
    private String findMaxValue(Map<String, LinkedList> map){
        /*
        String firstEntryKey;
        {
            Iterator entries = map.entrySet().iterator();
            Map.Entry firstEntry = (Map.Entry) entries.next();
            firstEntryKey = (String) firstEntry.getKey();
        }*/

        Set<String> keys = new TreeSet<>(map.keySet());
        String maxKey = keys.iterator().next();
        int max = 0;

        for(Map.Entry<String, LinkedList> entry : map.entrySet()){                                                      // Iterates through TreeMap and compares value sizes
            LinkedList<String> value = entry.getValue();            // FIXME: going to iterate through once redundantly

            if (value.size() > max){
                maxKey = entry.getKey();
                max = value.size();
            }
        }

        return maxKey;
    }

    // Finds all occurrences of a letter in a given word
    private int countOccurrences(String word, char letter){
        int occurrences = 0;

        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == letter) {
                occurrences += 1;
            }
        }
        return occurrences;
    }

    // Merges the current pattern of discovered letters with a new one by replacing '-' with given char
    private void updatePattern(String newPattern){
        for(int i = 0; i < currentPattern.length(); i++){
            if (currentPattern.charAt(i) != newPattern.charAt(i) && newPattern.charAt(i) != '-') {         //FIXME: Redundant loop through string?
                currentPattern.setCharAt(i, newPattern.charAt(i));
            }
        }
    }

    // Identifies and indexes all words in possibleWords that contain the passes char passed by 'guess'
    public int record(char guess){

        if (guessCount > 0 && !(possibleWords.isEmpty())) {                                                             // Verifies that the game is not over
            this.guesses.set((guess - 97), guess);                                                                      // Places guess in 'guesses' based on alphabetical position

            Map<String, LinkedList> guessEvaluations = new TreeMap<>();         // TODO: Change from LinkedList to TreeSet

            // loop through possibleWords and create a pattern equivalent key based on letter guess location
            for(String word : this.possibleWords){
                String key = wordToKey(word, guess);

                guessEvaluations.putIfAbsent(key, new LinkedList<String>());    // TODO: Verify that this is not inefficient
                guessEvaluations.get(key).add(word);                                                                    // access the LinkedList value and adds word to it
            }

            String mostValuesKey = this.findMaxValue(guessEvaluations);
            possibleWords.clear();
            possibleWords.addAll(guessEvaluations.get(mostValuesKey));

            int letterCount = countOccurrences(mostValuesKey, guess);

            if (letterCount == 0){
                guessCount -= 1;
            } else {
                updatePattern(mostValuesKey);
            }

            return letterCount;
        } else {
            throw new IllegalStateException();
        }
    }
}
