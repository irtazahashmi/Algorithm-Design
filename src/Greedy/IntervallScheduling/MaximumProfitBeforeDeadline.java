package Greedy.IntervallScheduling;

import java.util.*;

public class MaximumProfitBeforeDeadline {
    //Given a set of N jobs where each job i has a deadline and profit associated to it.
    // Each job takes 1 unit of time to complete and only one job can be scheduled at a
    // time. We earn the profit IF AND ONLY IF the job is completed by its deadline.
    // The task is to find the maximum profit and the number of jobs done.

    public static int[] JobScheduling(Job arr[], int n){
        ArrayList<MyJob> jobs = new ArrayList<>();
        for(Job j : arr) jobs.add(new MyJob(j.deadline, j.profit));
        int count = 0;
        int maxProf = 0;

        Collections.sort(jobs);

        int res[] = new int[n];
        boolean visited[] = new boolean[n];
        Arrays.fill(visited, false);

        for(int i = 0; i < jobs.size(); i++) {
            for(int j = Math.min(n, jobs.get(i).deadline) - 1; j >= 0; j--) {
                if (!visited[j]) {
                    visited[j] = true;
                    res[j] = i;
                    break;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if(visited[i]) {
                count++;
                maxProf += jobs.get(res[i]).profit;
            }
        }

        int[] ans = new int[2];
        ans[0] = count;
        ans[1] = maxProf;
        return ans;
    }

    static class Job{
        int deadline;
        int profit;
    }
}

class MyJob implements Comparable<MyJob>{
    int deadline;
    int profit;

    public MyJob(int d, int p) {
        deadline = d;
        profit = p;
    }

    @Override
    public int compareTo(MyJob o) {
        return o.profit - this.profit;
    }
}