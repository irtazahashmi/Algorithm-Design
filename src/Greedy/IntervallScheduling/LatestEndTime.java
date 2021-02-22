package Greedy.IntervallScheduling;

import java.util.*;

public class LatestEndTime {

    // Give the latest end time of schedule.
    // SAME GIVEN ORDER

    /**
     *  You should implement this method.
     *  @param n the number of jobs.
     *  @param t an array of size n+1, containing the initialization times t_1 through t_n. You should ignore t[0].
     *  @param p an array of size n+1, containing the computation times p_1 through p_n. You should ignore p[0].
     *  @param S an array of size n+1, containing at position S[i] the start time of the ith job that will run. I.e. S[1] contains the start time of the first job that will run.
     *  @param I an array of size n+1, containing at position I[i] the index j of the ith job that will run. I.e. If I[4] = 3, then the fourth job to run is the job with initialization time t[3] and processing time p[3].
     *  @return The latest end time given this schedule.
     */

    public static int solve(int n, int[] t, int[] p, int[] S, int[] I) {
        List<Job> jobs = new ArrayList<>();
        for(int i = 1; i <= n; i++) jobs.add(new Job(t[i], p[i]));

        int s = 0;

        for(int i = 1; i <= n; i++) {
            Job j = jobs.get(I[i] - 1); // get job using index. -1 because I has 0 has t[0] and jobs doesn't
            int time = j.init + j.processingTime + S[i];
            s = Math.max(time, s);
        }

        return s;
    }


    static class Job {
        int init;
        int processingTime;

        public Job(int i, int p) {
            init = i;
            processingTime = p;
        }
    }
}


