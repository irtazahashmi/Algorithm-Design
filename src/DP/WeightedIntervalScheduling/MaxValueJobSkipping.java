package DP.WeightedIntervalScheduling;

import java.util.Arrays;

public class MaxValueJobSkipping {

    // given jobs with start and finish time, and value vi.
    // find max value obtainable from this job

    public static int solve(int n, int[] s, int[] f, int[] v) {
        // number of items to skip before i at i (not compatible with i)
        int[] skip = new int[n+1];
        Arrays.fill(skip, 0);

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j < i; j++) {
                if (f[j] > s[i]) {
                    //not compatible
                    skip[i]++;
                }
            }
        }

        //The maximum value obtainable from these jobs by skipping.
        int[] mem = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            mem[i] = Math.max(mem[i - 1], mem[i - 1 - skip[i]] + v[i]);
        }
        return mem[n];
    }
}
