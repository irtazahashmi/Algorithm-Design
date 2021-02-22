package Greedy;

import java.util.ArrayList;

public class SubsequenceOfSequence {

    public static boolean subsequence(String[] sequence, String[] subsequence) {
        int i = 0;
        int j = 0;

        while (i < sequence.length && j < subsequence.length) {
            if (sequence[i].equals(sequence[i])) {
                i++;
                j++;
            } else {
                i++;
            }
        }

        if (j == subsequence.length) return true;
        else return false;
    }

    public static void main(String[] args) {
        String[] sequence = {"buy Amazon", "buy Yahoo", "buy eBay", "buy Yahoo", "buy Yahoo", "buy Oracle"};
        String[] sub = {"buy Yahoo", "buy eBay","buy Yahoo", "buy Oracle"};
        System.out.println(subsequence(sequence, sub));
    }
}
