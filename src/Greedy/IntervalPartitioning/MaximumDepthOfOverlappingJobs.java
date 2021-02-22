package Greedy.IntervalPartitioning;

import java.io.*;
import java.util.*;

public class MaximumDepthOfOverlappingJobs {

    public static String run(InputStream in) {
        return new MaximumDepthOfOverlappingJobs().solve(in);
    }

    // Given start time and processing time, find the minimum number of resources needed (DEPTH)
    // of the schedule. O(n^2)
    // 6
    // 6 1
    // 3 5
    // 1 2
    // 6 3
    // 8 3
    // 1 4

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);

        int n = sc.nextInt();
        ArrayList<JobIntervalPartitioning> jobs = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            int start = sc.nextInt();
            int end = start + sc.nextInt();
            jobs.add(new JobIntervalPartitioning(start, end));
        }

        sc.close();
        Collections.sort(jobs);

        int depth = 0;
        for(int i = 1; i < jobs.size(); i++) {
            int c = 1;
            for(int j = 0; j < i; j++) {
                if (jobs.get(j).end > jobs.get(i).start) c++;

                if (c > depth) depth = c;
            }
        }

        return depth +  "";
    }
}


class JobIntervalPartitioning implements Comparable<JobIntervalPartitioning>{
    int start;
    int end;

    public JobIntervalPartitioning(int s, int e){
        start = s;
        end = e;
    }

    @Override
    public int compareTo(JobIntervalPartitioning other){
        return this.start - other.start;
    }
}



