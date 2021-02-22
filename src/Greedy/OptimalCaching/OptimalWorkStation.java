package Greedy.OptimalCaching;

import java.util.*;

public class OptimalWorkStation {

    // n researchers, start and duration time, m minutes before machine closes
    // Can you tell the operator how many unlockings she may save by asking the
    // researchers not to lock their workstations when they leave and assigning
    // arriving researchers to workstations in an optimal way?
    // You may assume that there are always enough workstations available.

    /**
     * @param n number of researchers
     * @param m number of processes
     * @param start start times of jobs 1 through n. NB: you should ignore start[0]
     * @param duration duration of jobs 1 through n. NB: you should ignore duration[0]
     * @return the number of unlocks that can be saved.
     */
    public static int solve(int n, int m, int[] start, int[] duration) {
        ArrayList<JobOWS> jobs = new ArrayList<>();
        for(int i = 1; i <= n; i++) jobs.add(new JobOWS(start[i], start[i] + duration[i]));

        // Sort jobs on start time
        Collections.sort(jobs);

        // returns the machine with smallest locked time
        PriorityQueue<Machine> machines = new PriorityQueue<>();

        int saves = 0;

        for(JobOWS j: jobs) {
            while(!machines.isEmpty()) {

                // Machine is not available (in use)
                if (machines.peek().available > j.start) break;

                Machine mach = machines.poll();
                // save when the next researcher comes between (available-locked)
                if (mach.locked >= j.start) {
                    saves++;
                    break;
                }
            }
            //new machine with new ava-lock with m min gap.
            machines.add(new Machine(j.end, j.end + m));
        }

        return saves;
    }
}

class JobOWS implements Comparable<JobOWS>{
    //start - end
    int start;
    int end;

    public JobOWS(int s, int e) {
        start = s;
        end = e;
    }

    @Override
    public int compareTo(JobOWS o) {
        return this.start - o.start;
    }
}

class Machine implements Comparable<Machine>{
    // available - locked
    int available;
    int locked;

    public Machine(int a, int l) {
        available = a;
        locked = l;
    }

    //return smallest locked time
    @Override
    public int compareTo(Machine o) {
        return this.locked - o.locked;
    }
}

