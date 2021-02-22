package DP.WeightedIntervalScheduling;

import java.util.Arrays;

public class Recursive {

    // Brute force. Exponential time. O(2^n)
    // Naive way to find max compatible jobs.

    public static int recursive(int n, int[]s, int[]f, int[]value, int pred[]) {
        if (n == 0) return 0;
        int predecessor = Math.max(0, pred[n]);
        return Math.max(recursive(n - 1,s,f,value,pred), recursive(predecessor,s,f,value,pred) + value[n]);
    }

    public static void main(String[] args) {
        // Test 1: Expected 7
        int[] s = { 0, 1, 3, 0 , 4, 3, 5, 6, 8};
        int[] f = { 0, 4, 5, 6 , 7, 8,9,10,11};
        int[] v = { 0, 2, 1, 1 , 3,4,1,1,2};
        int[] p = { 0, -1, -1, -1, 1, -1, 2, 3, 5};
        int n = 8;
        System.out.println(recursive(n, s, f, v, p));

        // Test 2: Expected 10
        int[] s2 = { 0, 0, 1, 3 };
        int[] f2 = { 0, 3, 4, 8 };
        int[] v2 = { 0, 3, 5, 7 };
        int[] p2 = { 0, -1, -1, 1 };
        int n2 = 3;
        System.out.println(recursive(n2, s2, f2, v2, p2));
    }
}
