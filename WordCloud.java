import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;



/**
 * Created by Megan on 12/13/16.
 */
public class WordCloud {

    static WordCountTable wordCountTable = new WordCountTable();
    static String[] toUse;

    public static void main(String[] args) {

        try {

            Scanner alice = new Scanner(new FileReader("wonderland.txt"));

            while (alice.hasNext()) {

                toUse = alice.nextLine().split("\\W+");

                for (int i = 0; i < toUse.length; i++) {

                    String word = toUse[i];
                    if (!wordCountTable.containsKey(word)) {
                        wordCountTable.set(word, 1);
                    } else wordCountTable.set(word, (wordCountTable.get(word) + 1));
                }
            }

            Scanner commonWords = new Scanner(new FileReader("1-1000.txt")); //1,000 most common English words

            while (commonWords.hasNext()) {
                wordCountTable.set(commonWords.nextLine(), 0);
            }


        } catch (FileNotFoundException e) {
            System.out.print("File Not Found");
        }


        //Sorting

        String[] words = new String[wordCountTable.keys.length];
        int[] counts = new int[wordCountTable.values.length];

        System.arraycopy(wordCountTable.keys, 0, words, 0, wordCountTable.keys.length);
        System.arraycopy(wordCountTable.values, 0, counts, 0, wordCountTable.values.length);


//        System.out.println(Arrays.toString(counts));
//        System.out.println(Arrays.toString(words));


        Merge.sort(counts, words);
        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
        frame.setVisible(true);
        display.updateDisplay(words, counts);


//        System.out.println(Arrays.toString(counts));
//        System.out.println(Arrays.toString(words));


        }
}


