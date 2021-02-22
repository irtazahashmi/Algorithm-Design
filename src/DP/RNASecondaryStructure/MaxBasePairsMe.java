package DP.RNASecondaryStructure;

import java.util.Arrays;

public class MaxBasePairsMe {

    // O(n^3)

    public static int maxBasePairs(int n, String sequence) {
        int[][] mem = new int[n][n];
        for(int[] row : mem) Arrays.fill(row, -1);

        for(int k = 0; k < n ; k++) {
            for(int i = 0 ; i < n - k; i++) {
                int j = i + k;

                if (i >= j-4) mem[i][j] = 0; // not feasible pairs due to sharp turns
                else {

                    // choose max(left, bottom)
                    mem[i][j] = Math.max(mem[i][j-1], mem[i+1][j]);

                    for(int t = i; t < j - 4; t++) {
                        // if pair, get the max(prev, 1 + left bottom)
                        if (isPair(sequence.charAt(t), sequence.charAt(j))) {
                            mem[i][j] = Math.max(mem[i][j], 1 + mem[i+1][j-1]);
                        }
                    }

                    // if i < k < j (not crossing). get the max of(prev, (i to k) + (k + 1 to j) sub-problem
                    if (i < k && k < j) mem[i][j] = Math.max(mem[i][j], mem[i][k] + mem[k+1][j]);
                }
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(mem[i][j] + " ");
            }
            System.out.println();
        }

        printRNAPairs(sequence, mem, 0, n - 1);
        return mem[0][n-1];
    }

    public static boolean isPair(char a, char b) {
        return ((a == 'A' && b == 'U')
                || (a == 'U' && b == 'A')
                || (a == 'C' && b == 'G')
                || (a == 'G' && b == 'C'));
    }

    public static void printRNAPairs(String string, int[][] mem, int i, int j) {
        while (i < j){
            if (mem[i][j] != mem[i+1][j-1]) {
                System.out.println(string.charAt(i) + " " + string.charAt(j));
            }
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        //Test 1: 2
//        String seq = "ACCGGUAGU";
//        System.out.println("Answer: " + maxBasePairs(seq.length(), seq));

        //Test 2: 5
        String seq2 = "ACAUGAUGGCCAUGU";
        int n2 = seq2.length();
        System.out.println("Answer: " + maxBasePairs(n2, seq2));
    }
}
