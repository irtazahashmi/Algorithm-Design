package DP;

import java.util.Arrays;

public class HighwayBillboard {

    // Highway of [0, M] miles
    // Billboard at xi will generate ri revenue
    // Space between billboards should be > 5 miles
    // maximize revenue!


    public static void solve(int M, int n, int[] x, int[] r) {
        int mem[] = new int[M+1];
        Arrays.fill(mem, 0);
        mem[0] = 0;

        int nextBoard = 0;

        for(int i = 1; i <= M; i++) {
            // boards finished
            if (nextBoard >= n) mem[i] = mem[i-1];
            else {
                // Board doesn't belong to the pathway -> not in [0,M] interval
                if (x[nextBoard] != i) mem[i] = mem[i - 1];
                else {
                    // Have one board only as we are within 5 miles
                    if (i <= 5) mem[i] = Math.max(r[nextBoard], mem[i-1]);
                    else { // remove prev bad revenue billboards
                        mem[i] = Math.max(r[nextBoard] + mem[i - 5 - 1], mem[i-1]);
                        nextBoard++;
                    }
                }
            }
        }

        printBoards(M, x, r, mem, mem[M]);
        System.out.println("Max Value: " + mem[M]);
    }

    public static void printBoards(int M, int[]x, int[]r, int[] mem, int result) {
        while (M > 0){
            // chose another board
            if (mem[M] == result && mem[M] != mem[M-1]) {
                System.out.println("Board: " + M);
                int index = 0;
                for(int i = 0; i < x.length; i++) {
                    if (x[i] == M) index = i;
                }
                result -= r[index];
            }
            M--;
        }
    }


    public static void main(String[] args) {
        // Test 1: 10. (6, 12)
        int M = 20;
        int n = 4;
        int x[] = {6, 7, 12, 14};
        int r[] = {5, 6, 5, 1};
        solve(M, n, x, r);
    }
}
