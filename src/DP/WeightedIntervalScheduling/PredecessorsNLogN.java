package DP.WeightedIntervalScheduling;

import java.util.*;

public class PredecessorsNLogN {

    // Predecessors in O(nlogn)

    public static int[] solve(int n, int[] s, int[] f) {
        ArrayList<Job> start = new ArrayList<>();
        ArrayList<Job> end = new ArrayList<>();
        int[] pred = new int[n+1];

        //index start with 1 because 0 empty always
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

        for(int i : pred) System.out.print(i + " ");
        return pred;
    }




    public static void main(String[] args) {
        // [0 -1 -1 -1 -1 2 3 1 4 ]
//        int n = 8;
//        int[] s = {0, 0,1,3,3,4,5,6,8};
//        int[] f = {0,6,4,5,8,7,9,10,11};
//        solve(n,s,f);


        //[0, 4, -1, -1, 2, 4]
        int n = 5;
        int[] s = {0, 5, 0, 1, 3, 5};
        int[] f = {0, 7, 2, 6, 4, 7};
        solve(n,s,f);
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
