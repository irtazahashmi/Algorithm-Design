package Greedy.SelectingBreakpoints;


// MINIMUM TOWERS REQUIRED SO THAT ALL HOUSES ARE COVERED

//Let's consider a long, quiet country road with houses scattered very sparsely along it.
// (We can picture the road as a long line segment, with an eastern endpoint and a western endpoint.)
// Further,let's suppose that despite the bucolic setting, the residents of all these houses are avid
// cell phone users. You want to place cell phone base stations at certain points along the road, so
// that every house is within 4 MILES of one of the base stations.
// Give an efficient algorithm that achieves this goal, using as few base stations as possible.

import java.util.ArrayList;
import java.util.Arrays;

public class MinimumStationsPossible {

    public static ArrayList<Integer> buildTowers(int[] houses) {
        // sort the houses such that 1...3....4....5
        Arrays.sort(houses);
        ArrayList<Integer> towers = new ArrayList<>();

        int dist = Integer.MAX_VALUE;
        int numberOfTowers = 0;
        for(int h : houses) {
            // if distance greater than 4 miles, add a tower.
            if (Math.abs(h - dist) > 4) {
                towers.add(h + 4);
                dist = h + 4;
                numberOfTowers++;
            }
        }

        return towers;
    }

    public static void main(String[] args) {
        int[] houses = {1, 20, 23, 25, 32, 37};
        System.out.println(buildTowers(houses));
    }
}
