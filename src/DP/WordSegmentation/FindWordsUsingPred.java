package DP.WordSegmentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindWordsUsingPred {

    // print the best words possible using preds!

    public static void printStringUsingPred(int n, String s, int[] pred) {
        // indexes that the words are gonna split
        List<Integer> splitIndexes = new ArrayList<>();
        int last = pred[n];
        while (last != 0) {
            splitIndexes.add(last);
            last = pred[last];
        }
        Collections.sort(splitIndexes);

        int start = 0;
        int splitIndex = 0;

        //final words
        List<String> words = new ArrayList<>();
        while (start <= n) {
            if (splitIndex == splitIndexes.size()) {
                words.add(s.substring(start, s.length()));
                break;
            }
            words.add(s.substring(start, splitIndexes.get(splitIndex)));
            start = splitIndexes.get(splitIndex);
            splitIndex++;
        }

        System.out.println(words);
    }
}
