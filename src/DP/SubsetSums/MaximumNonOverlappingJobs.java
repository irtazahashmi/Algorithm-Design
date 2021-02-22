package DP.SubsetSums;

public class MaximumNonOverlappingJobs {

    //8)
    //There are n jobs, each with a deadline di and a required processing time ti,
    // and all jobs are available to be scheduled starting at time s.
    // For a job i to be done, it needs to be assigned a period from si ≥ s to fi = si + ti,
    // and different jobs should be assigned non overlapping intervals.
    // As usual, such an assignment of times will be called a schedule.

    // In particular, we consider the case in which each job must either be DONE BY its DEADLINE or NOT AT ALL.

    // We’ll say that a subset J of the jobs is schedulable if there is a schedule for the jobs in J so
    // that each of them finishes by its deadline.
    // Your problem is to select a schedulable subset of maximum possible size and give a schedule
    // for this subset that allows each job to finish by its deadline.

    // subset problem
    // should be INCREASING deadline

    public static int maxNonOverlappingJobs(int n, int t[], int deadline[]) {
        int D = deadline[n]; // max deadline
        int[][] mem = new int[n+1][D+1];

        for(int d = 0; d <= D; d++) mem[0][d] = 0; // for i = 0, 0 jobs scheduled

        for(int i = 1; i <= n; i++) {
            for(int d = 0; d <= D; d++) {
                // i not in optimal solution because the time taken to do the job is above deadline
                if (t[i] > d) mem[i][d] = mem[i - 1][d];

                // i scheduled last so all jobs before n should be finished by d - t[i]
                // if (deadline[i] > or < d) mem[i][d] = mem[i - 1][d]
                else mem[i][d] = Math.max(mem[i-1][d], 1 + mem[i-1][d - t[i]]);
            }
        }

        return mem[n][D];
    }

    public static void main(String[] args) {
        int n = 4;
        int[] t = {0,6,4,3,7};
        int deadline[] = {0,1,1,3,7};
        System.out.println(maxNonOverlappingJobs(n, t, deadline));
    }
}
