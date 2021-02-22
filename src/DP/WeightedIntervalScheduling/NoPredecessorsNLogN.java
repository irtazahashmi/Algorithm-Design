package DP.WeightedIntervalScheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NoPredecessorsNLogN {

    // NO PREDECESSORS.
    // Find max value of compatible jobs
    // O(nlogn)

    public static int solve(int n, int[] s, int[] f, int[] v) {
        int[] pred = findPred(n, s, f, v); // compute predecessors

        int[] mem = new int[n+1];
        mem[0] = 0;
        for(int i = 1; i <= n; i++) {
            int predecessor = Math.max(0, pred[i]); // if compatible
            mem[i] = Math.max(mem[i-1], mem[predecessor] + v[i]); // using the recursive formula
        }

        printScheduleIterative(n,s,f,v,pred, mem);
        return mem[n];
    }

    public static int[] findPred(int n, int[] s, int[] f, int[] v) {
        ArrayList<Job> start = new ArrayList<>();
        ArrayList<Job> end = new ArrayList<>();
        int[] pred = new int[n+1];

        for(int i = 1; i <= n; i++) {
            start.add(new Job(i, s[i]));
            end.add(new Job(i, f[i]));
        }

        // storing indexes by ascending time, else by index
        Collections.sort(start);
        Collections.sort(end);

        int j = 0; //keeping track of end times. Start with first end time
        for(int i = 0; i < start.size(); i++) {
            if (end.get(j).time > start.get(i).time) pred[start.get(i).index] = -1;
            else {
                //loop till we find biggest end time smaller than start time
                for(; j < end.size() - 1; j++) {
                    if (end.get(j+1).time > start.get(i).time) break;
                }
                pred[start.get(i).index] = end.get(j).index;
            }
        }

        return pred;
    }


    public static void printScheduleIterative(int n, int[] s, int[] f, int[] v, int[] p, int[]mem) {
        int j = n;
        while (j > 0) {
            int pred = Math.max(p[j], 0);
            // job was chosen
            if (v[j] + mem[pred] > mem[j-1]){
                System.out.println(s[j] + " to " + f[j] + " with value: " + v[j]);
                j = pred;
            } else{
                j--;
            }
        }
    }

    public static void main(String[] args) {
//        int n = 8;
//        int[] s = {0, 0,1,3,3,4,5,6,8};
//        int[] f = {0,6,4,5,8,7,9,10,11};
//        int[] v = {0,2,1,1,3,4,1,1,2};
//        solve(n,s,f,v);

        int n = 6;
        int[] s = { 0, 1, 0, 2 , 10, 18, 4};
        int[] f = { 0, 5, 8, 11 , 14, 23,25};
        int[] v = { 0, 8, 4, 2,   7 , 4,2};
        solve(n, s, f, v);
    }

    static class Job implements Comparable<Job>{
        int index;
        int time;

        public Job(int i, int t) {
            index = i;
            time = t;
        }

        @Override
        public int compareTo(Job o) {
            if (this.time - o.time == 0) return this.index - o.index;
            else return this.time - o.time;
        }
    }
}
