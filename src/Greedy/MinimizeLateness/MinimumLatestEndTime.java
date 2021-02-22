package Greedy.MinimizeLateness;

import java.util.*;

public class MinimumLatestEndTime {

    // Minimum Latest End Time.
    // PARALLEL -> NOT PARALLEL

    // Account creation (parallel) but interviews take their own time (not parallel).
    // So sort on account creation time

    /**
     *  You should implement this method.
     *  @param n the number of participants.
     *  @param a an array of size n+1, containing the account creation a_1 through a_n. You should ignore a[0].
     *  @param b an array of size n+1, containing the interview times b_1 through b_n. You should ignore b[0].
     *  @return The minimum latest end time.
     */
    public static int boardgameTime(int n, int[] a, int[] b) {
        ArrayList<Job> jobs = new ArrayList<>();
        for(int i = 1; i <= n; i++) jobs.add(new Job(a[i], b[i]));

        // Sort according to creationTime. Fast creationTime -> Interview first
        Collections.sort(jobs);

        List<Integer> time = new ArrayList<>();
        // First add creation and interview time because parallel -> not parallel
        time.add(jobs.get(0).creationTime + jobs.get(0).interviewTime);
        int minLatest = time.get(0);

        for(int i = 1; i < jobs.size(); i++) {
            int latestTime = 0;
            // creation time done. Only interview (not parallel) time added to time.
            if (time.get(i - 1) > jobs.get(i).creationTime) latestTime = time.get(i - 1) + jobs.get(i).interviewTime;
            // creationTime + interview Time
            else latestTime = jobs.get(i).creationTime + jobs.get(i).interviewTime;
            time.add(latestTime);
            minLatest = Math.max(minLatest, latestTime);
        }

        return minLatest;
    }

    static class Job implements Comparable<Job> {
        int creationTime;
        int interviewTime;

        public Job(int c, int i) {
            creationTime = c;
            interviewTime = i;
        }

        @Override
        public int compareTo(Job other){
            if (this.creationTime - other.creationTime == 0) return this.interviewTime - other.interviewTime;
            else return this.creationTime - other.creationTime;
        }
    }
}




