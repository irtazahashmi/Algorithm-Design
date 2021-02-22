package DP.WeightedIntervalScheduling;

import java.util.Arrays;

public class PredecessorsQuadratic {

    // Predecessors in O(n^2)
    // WORKS ONLY IF START TIME IN INCREASING ORDER!

    public static int[] predecessorsQuadratic(int n, int[] s, int[] f) {
        int[] p = new int[n + 1];
        Arrays.fill(p, -1);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                // If job i starts after job j is finished, we know job j is its predecessor
                if (s[i] >= f[j]) {
                    p[i] = j;
                }
            }
        }

        p[0] = 0;
        for(int i : p) System.out.print(i + " ");
        return p;
    }

    public static void main(String[] args) {
        // [0, -1, -1, 1]
//        int n = 3;
//        int[] s = { 0, 0, 1, 3 }; // sorted!
//        int[] f = { 0, 3, 4, 8 };
//        predecessorsQuadratic(n,s,f);
    }
}
