package Greedy.IntervallScheduling;

import java.io.InputStream;
import java.util.*;

public class HowLongCourtWillBeOpen {

    // MULTIPLE RESOURCES, GIVEN ORDER, how long court will be open (1 hour break between cases).

    // cases courtrooms
    // 5 2

    // caseName duration
    // Butz 16
    // Fey 8
    // Powers 7
    // Edgeworth 5
    // Skye 16

    // Time      0          8 9       16 17        22           33
    // Court 1   |--------------------|  |-----------------------|
    // Court 2   |----------| |-------|  |----------|

    public static String run(InputStream in) {
        return new HowLongCourtWillBeOpen().solve(in);
    }

    private int numCases;
    private int numCourtRooms;
    private long[] caseTimes;

    public String solve(InputStream in) {
        parseInput(in);
        return Long.toString(computeLastFinish());
    }

    /**
     *  Return when will the court close and last case will finish.
     */
    private long computeLastFinish() {
        PriorityQueue pq = new PriorityQueue<>();

        // court rooms
        for(int i = 0; i < numCourtRooms; i++) pq.add(0L);

        long time = 0L;
        for(int i = 0; i < numCases; i++) {
            long court = (long) pq.poll(); // returns available courtroom
            pq.add(court + caseTimes[i] + 1); // 1hr break time
            time = Math.max(time, court + caseTimes[i]);
        }

        return time;
    }


    /**
     *  This method parses the input from an inputstream. You should not need to modify this method.
     */
    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);
        this.numCases = sc.nextInt();
        this.numCourtRooms = sc.nextInt();
        this.caseTimes = new long[this.numCases];
        for (int i = 0; i < this.numCases; i++) {
            sc.next();
            this.caseTimes[i] = sc.nextLong();
        }
        sc.close();
    }
}
