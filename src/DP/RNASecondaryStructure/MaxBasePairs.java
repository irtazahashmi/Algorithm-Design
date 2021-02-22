package DP.RNASecondaryStructure;

public class MaxBasePairs {

    // O(n^3) total time

    // n = the number of bases.
    // b = RNA string in char array
    // p = if i and j is a pair

    public static int maxBasePairs(int n, char[] b, boolean[][] p) {
        int[][] mem = new int[n + 1][n + 1];

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= j - 1; i++) { // makes sure i <= j
                int max = -1;
                for (int t = i; t < j - 4; t++) {  // i <= t <= j - 4
                    // divide into two sub problems: i to t-1 & t + 1 to j-1
                    if (p[t][j]) max = Math.max(max, 1 + mem[i][t - 1] + mem[t + 1][j - 1]);
                }
                //max(dont create a base pair, pair bj with bt at least 4 away)
                mem[i][j] = Math.max(mem[i][j - 1], max);
            }
        }

        printRNAPairs(b, mem, 1, n);
        return mem[1][n];
    }

    public static void printRNAPairs(char[] b, int[][] mem, int i, int j) {
        while (i < j){
            if (mem[i][j] != mem[i+1][j-1]) {
                System.out.println(b[i] + " " + b[j]);
            }
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        //Test 1: 0 (not 4 apart)
        int n = 8;
        char[] b = "\0AUCGAUCG".toCharArray();
        boolean[][] p = new boolean[n + 1][n + 1];
        p[1][3] = true;
        System.out.println(maxBasePairs(n, b, p));


//        test 2: 2
        int n2 = 8;
        char[] b2 = "\0CAUCGAUCG".toCharArray();
        boolean[][] p2 = new boolean[n + 1][n + 1];
        p2[1][8] = true; //(C, C)
        p2[2][7] = true; //(A, U)
        System.out.println(maxBasePairs(n2, b2, p2));
    }
}
