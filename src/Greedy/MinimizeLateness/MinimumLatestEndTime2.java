package Greedy.MinimizeLateness;

import java.util.*;

public class MinimumLatestEndTime2 {

    // Given are the number of jobs n and two sequences of length n:
    // one with the initialization times, and one with the main computation times, in NO PARTICULAR ORDER.
    // Implement a greedy algorithm to determine the start times of all initializations
    // such that the latest end time is minimized.
    // RETURN the START TIME of the initialization of the last job according to this schedule.

    /**
     *  You should implement this method.
     *  @param n the number of jobs.
     *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
     *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
     *  @return The minimum latest end time.
     */
    public static int solve(int n, int[] t, int[] p) {
        List<Job> jobs = new ArrayList<>();
        for(int i = 1; i <= n; i++) jobs.add(new Job(t[i], p[i]));

        // sort according to largest duration, if same sort according to largest init
        Collections.sort(jobs);

        // sum initialization times
        int s = 0;
        for(Job j : jobs) s += j.init;

        // returning start time of last job
        return s - jobs.get(jobs.size() - 1).init;
    }

    static class Job implements Comparable<Job> {
        int init;
        int duration;

        public Job(int i, int d) {
            init = i;
            duration = d;
        }

        @Override
        public int compareTo(Job other) {
            if (this.duration - other.duration == 0) {
                if (this.init - other.init > 0) return -1;
                else if (this.init - other.init < 0) return 1;
                else return 0;
            } else if (this.duration - other.duration > 0) return -1;
            else return 1;
        }
    }
}

