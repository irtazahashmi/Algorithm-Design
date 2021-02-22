package Greedy.MinimizeLateness;

import java.util.*;

public class MultipleProcessors {

  // m processors, n jobs, 1 hour processing time.
  // if processers equals m, use PRIORITY QUEUE!!!
  // lateness = startTime + 1 - deadline
  /**
   * @param n the number of jobs
   * @param m the number of processors
   * @param d the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
   * @return the minimised maximum lateness.
   */
  public static int solve(int n, int m, int[] d) {
    int maxLateness = Integer.MIN_VALUE;
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    ArrayList<Integer>deadlines = new ArrayList<>();
    for(int i = 1; i <= n; i++) deadlines.add(d[i]);
    Collections.sort(deadlines);

    //all processors
    for(int i = 0; i < m; i++) pq.add(0);

    int i = 0;
    while(i < deadlines.size()) {
      Integer start = pq.remove();
      Integer lateness = start + 1 - deadlines.get(i);
      pq.add(start + 1);
      maxLateness = Math.max(lateness, maxLateness);
      i++;
    }


    return maxLateness;
  }


//  public static int solve(int n, int m, int[] deadlines) {
//    ArrayList<Integer> jobs = new ArrayList<>();
//    for(int i = 1; i <= n; i++) jobs.add(deadlines[i]);
//
//    //sort on deadline
//    Collections.sort(jobs);
//
//    int start = 0;
//    int processor = 1;
//    int maxLateness = Integer.MIN_VALUE;
//
//    for(int i = 0; i < n; i++) {
//      int lateness = start + 1 - jobs.get(i);
//      if (lateness >= maxLateness) {
//        maxLateness = lateness;
//      }
//
//      //If not all processors are in use, use them.
//      if (processor + 1 <= m) {
//        processor++;
//      } else {
//      	//else use one processor and increment startTime as one job takes 1 hour
//        start++;
//        processor = 1;
//      }
//    }
//
//    return maxLateness;
//  }
}
