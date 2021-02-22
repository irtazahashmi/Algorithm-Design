package DP.WeightedIntervalScheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NoPredecessorsQuadratic {

    // NO PREDECESSORS COMPUTED.
    // SORTED by ascending finishing time!
    // Find max value of compatible jobs
    // O(n^2)


    public static int solve(int n, int[] s, int[] f, int[] v) {
        int[] pred = predecessorsQuadratic(n, s, f, v); // compute predecessors

        int[] mem = new int[n+1];
        mem[0] = 0;
        for(int i = 1; i <= n; i++) {
            int predecessor = Math.max(0, pred[i]); // if compatible
            mem[i] = Math.max(mem[i-1], mem[predecessor] + v[i]); // using the recursive formula
        }
        return mem[n];
    }

    public static int[] predecessorsQuadratic(int n, int[] s, int[] f, int[] v) {
        int[] pred = new int[n + 1];
        // everyone has no preds
        Arrays.fill(pred, -1);

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j < i; j++) {
                //if no overlap, update index
                if (f[j] <= s[i]) pred[i] = j;
            }
        }

        return pred;
    }
}
