package DP.RNASecondaryStructure;

import java.util.Arrays;

public class MaxBasePairsRecursive {

    // brute force (Nussinov)

    public static int maxMatchPairs(int n, String string) {
        int[][] mem = new int[n][n];
        for(int[] row: mem) Arrays.fill(row, -1);
        int res = maxMatchPairs(string, 0, string.length() - 1, mem);
        return res;
    }

    private static int maxMatchPairs(String string, int i, int j, int[][] mem) {
        // if cell not empty, reuse
        if (mem[i][j] != -1) return mem[i][j];
        //base case. not feasible due to sharp turns.
        if (i >= j-4) {
            mem[i][j] = 0;
            return 0;
        }

        int maxPairs = 0;
        //choosing the max between bottom and left solution
        maxPairs = Math.max(maxPairs,
                Math.max(maxMatchPairs(string, i + 1, j, mem), maxMatchPairs(string, i, j - 1, mem)));

        // if pair, choose between max(prev, 1 + left bottom)
        if (isPair(string.charAt(i), string.charAt(j)))
            maxPairs = Math.max(maxPairs, 1 + maxMatchPairs(string, i + 1, j - 1, mem));

        //choose the max pairings between two intervals i to k and k + 1 to j.
        // no crossing as i < k < j.
        for (int k = i; k < j - 4; k++)
            maxPairs = Math.max(maxPairs, maxMatchPairs(string, i, k, mem) + maxMatchPairs(string, k + 1, j, mem));

        mem[i][j] = maxPairs;
        return maxPairs;
    }

    public static boolean isPair(char a, char b) {
        return ((a == 'A' && b == 'U')
                || (a == 'U' && b == 'A')
                || (a == 'C' && b == 'G')
                || (a == 'G' && b == 'C'));
    }

    public static void main(String[] args) {
        // Test 1: 2
        String seq = "ACCGGUAGU";
        int n = seq.length();
        System.out.println(maxMatchPairs(n, seq));

        //Test 2: 5
        String seq2 = "ACAUGAUGGCCAUGU";
        int n2 = seq2.length();
        System.out.println(maxMatchPairs(n2, seq2));
    }
}
