package DP.WordSegmentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WordSegmentation {

    // given a string of x letters x1...xn
    // quality fuction gives a good value of xi,xi+1,...,xj
    // split such that sum of quality is max

    // O(n^2)

    public static int solve(int n, String s, int[][] quality) {
        int[] mem = new int[n+1];

        int[] pred = new int[n+1];
        Arrays.fill(pred, 0);

        mem[0] = 0;

        for(int j = 1; j <= n; j++) {
            int maxQuality = Integer.MIN_VALUE;
            for(int i = 1; i <= j; i++) {
                if (maxQuality < quality[i][j] + mem[i-1]){
                    maxQuality =  quality[i][j] + mem[i-1];
                    pred[j] = i - 1;
                }
            }
            mem[j] = maxQuality;
        }

        printQualityString(n, s, quality, mem);
        return mem[n];
    }

    //Recover solution using mem!
    public static void printQualityString(int n, String s, int[][] quality, int[] mem) {
        List<Integer> indexes = new ArrayList<>();
        getIndexes(n, quality, mem, indexes);

        ArrayList<String> words = new ArrayList<>();

        // add words
        for(int i = 0; i < indexes.size() - 1; i++) {
            words.add(s.substring(indexes.get(i), indexes.get(i+1)));
        }

        // add the remaining of the string!
        words.add(s.substring(indexes.get(indexes.size() - 1)));
        System.out.println(words);
    }

    public static void getIndexes(int n, int[][] quality, int[] mem, List<Integer> indexes) {
        if (n == 0) return;
        int i = n;
        while (i >= 1 && quality[i][n] + mem[i-1] != mem[n]) i--;
        getIndexes(i-1, quality, mem, indexes);
        indexes.add(i - 1); // index starts from 0, not 1!!! for 1 -> indexes.add(i);
    }


    public static void main(String[] args) {
        int n = 8;
        String s = "iloveyou";
        //ignore 1st row & 1st column
        int[][] quality = {{0, 0, 0, 0 ,0, 0, 0, 0, 0},
                           {0, 10, 0, 0 ,0, 0, 0, 0, 0},
                           {0, 0, 0, 0 ,0, 10, 0, 0, 0},
                           {0, 0, 0 ,0, 0, 0, 0, 0, 0},
                           {0, 0, 0 ,0, 0, 0, 0, 0, 0},
                           {0, 0, 0 ,0, 0, 0, 0, 0, 0},
                           {0, 0, 0 ,0, 0, 0, 0, 0, 10},
                           {0, 0, 0 ,0, 0, 0, 0, 0, 0},
                           {0, 0, 0 ,0, 0, 0, 0, 0, 0}};

        System.out.println(solve(n, s, quality));
    }
}


