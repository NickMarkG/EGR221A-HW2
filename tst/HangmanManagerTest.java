import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;
/*
 Created by InteliJ Idea
         *User:Nicholas Gaultney
         *Date:2/5/2018
         *Time:4:14PM
         *Contact:nmgaultney@gmail.com
*/
public class HangmanManagerTest {

    @Test
    public void constructorTest1(){

        /*Scanner file = new Scanner(new File("/Resources/dictionary2.txt"));
        List<String> dictionary = new ArrayList<String>();
        while (file.hasNext())
            dictionary.add(file.next().toLowerCase());

        List<String> dictionary2 = Collections.unmodifiableList(dictionary);*/

        Collection<String> collection =
                new ArrayList<String>(Arrays.asList(new String[] { "ally", "beta", "cool", "deal", "else", "flew", "good", "hope", "ibex" }));

        HangmanManager hangman = new HangmanManager(collection, 4, 5);

        assertEquals(5, hangman.guessesLeft());

    }

    @Test
    public void guessesTest(){
        Collection<String> collection =
                new ArrayList<String>(Arrays.asList(new String[] { "ally", "beta", "cool", "deal", "else", "flew", "good", "hope", "ibex" }));

        HangmanManager hangman = new HangmanManager(collection, 4, 5);
        Collection<String> emptyArray = new TreeSet<>();

        assertEquals(emptyArray, hangman.guesses());

        SortedSet<String> set = new TreeSet<>();
        set.add("a");
        hangman.record('a');

        //assertEquals(set, hangman.guesses());
    }

    @Test
    public void patternTest(){
        Collection<String> collection =
                new ArrayList<String>(Arrays.asList(new String[] { "ally", "beta", "cool", "deal", "else", "flew", "good", "hope", "ibex" }));

        HangmanManager hangman = new HangmanManager(collection, 4, 5);

        assertNotSame("----", hangman.pattern());
        assertNotSame(" ----", hangman.pattern());
        assertNotSame("---- ", hangman.pattern());
        assertNotSame(" ---- ", hangman.pattern());
        assertEquals("- - - -", hangman.pattern());

    }

    @Test
    public void wordToKeyTest(){

    }

    @Test
    public void findMaxValueTest(){

    }

    @Test
    public void countOccurrencesTest(){

    }

    @Test
    public void updatePatternTest(){
        Collection<String> collection =
                new ArrayList<String>(Arrays.asList(new String[] { "ally", "beta", "cool", "deal", "else", "flew", "good", "hope", "ibex" }));

        HangmanManager hangman = new HangmanManager(collection, 4, 5);


    }

    @Test
    public void recordTest(){
        Collection<String> collection =
                new ArrayList<String>(Arrays.asList(new String[] { "ally", "beta", "cool", "deal", "else", "flew", "good", "hope", "ibex" }));

        HangmanManager hangman = new HangmanManager(collection, 4, 5);

        assertEquals(0, hangman.record('a'));
        assertEquals(0, hangman.record('e'));
        assertEquals(0, hangman.record('i'));
        assertEquals(2, hangman.record('o'));
        assertEquals(0, hangman.record('g'));
        assertEquals(1, hangman.record('c'));
        assertEquals(1, hangman.record('l'));
    }
}