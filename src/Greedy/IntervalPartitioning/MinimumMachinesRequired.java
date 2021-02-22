package Greedy.IntervalPartitioning;

import java.util.Arrays;

public class MinimumMachinesRequired {

    //Suppose you have n jobs that all require processing.
    // For each job we are given the start and finishing time.
    // We need to determine the minimum number of machines required to run all jobs.
    // O(n)


    public static int solve(int[] start, int[]end) {
        Arrays.sort(start);
        Arrays.sort(end);
        int n = start.length;
        int i = 0;
        int j = 0;
        int cnt = 0;
        int m = 0;
        while (i < n && j < n)
            // job is compatible.
            if (start[i] < end[j]) {
                cnt++;
                m = Math.max(cnt, m); //update depth
                i++;
            } else {
                cnt--;
                j++;
            }

        return m;
     }
}
