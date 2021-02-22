package Greedy.MinimizeLateness;

import java.util.ArrayList;
import java.util.Collections;

public class Triathlon {

    // NOT PARALLEL -> PARALLEL

    //You are asked to compute in which order n contestants of a triathlon
    // should be sent on their way such as to minimize the (expected) completion
    // time, which is the earliest moment everyone is finished. The triathlon consists
    // of first swimming in the pool, then biking a certain distance, and finally running
    // a small distance. Biking and running can be done simultaneously, but the pool can
    // be used only by one contestant at a time.
    public static int solve(int[] swimming, int[] biking, int[]running) {
        ArrayList<Event> events=new ArrayList<>();
        for(int i = 0; i < swimming.length; i++)
            events.add(new Event(swimming[i],biking[i], running[i], biking[i]+running[i]));

        Collections.sort(events);

        int swimmingTotal = 0;
        for(Event e : events) swimmingTotal += e.swimmingTime;
        int latestTime = swimmingTotal + events.get(events.size() - 1).bikingRunning;
        return latestTime;
    }

    public static void main(String[] args) {
        // test 1
        int[] s = {6,9};
        int[] b = {9,4};
        int[] r = {6,7};

        //test 2
        int[] s2 = {7,6,5};
        int[] b2 = {5,6,7};
        int[] r2 = {8,8,8};

        //test 3
        int[] s3 = {5,10,8};
        int[] b3 = {10,5,7};
        int[] r3 = {20, 15, 25};

        System.out.println(solve(s, b, r)); //26
        System.out.println(solve(s2, b2, r2)); //31
        System.out.println(solve(s3, b3, r3)); //43
    }
}

class Event implements Comparable<Event> {
    int swimmingTime;
    int bikingTime;
    int runningTime;
    int bikingRunning;

    public Event(int s, int b, int r, int br) {
        swimmingTime=s;
        bikingTime=b;
        runningTime=r;
        bikingRunning=br;
    }

    @Override
    public int compareTo(Event o) {
        return o.bikingRunning - this.bikingRunning;
    }
}
