package Greedy.SelectingBreakpoints;

public class MinimumTrucksNeeded {

    //the minimal number of trucks required to ship the packages in the GIVEN ORDER

    // If we can carry at most 48 weight units in one truck (𝑊=48) then the following
    // shipment of boxes should result in an output of 3 (trucks needed).
    // 41 29 12 26 -> 3

    /**
     * @param n the number of packages
     * @param weights the weights of all packages 1 through n. Note that weights[0] should be ignored!
     * @param maxWeight the maximum weight a truck can carry
     * @return the minimal number of trucks required to ship the packages _in the given order_.
     */
    public static int minAmountOfTrucks(int n, int[] weights, int maxWeight) {
        if (n == 0) return 0;

        int weight = 0;
        int trucks = 1;

        for(int i = 1; i < weights.length; i++) {
            weight += weights[i];
            if (weight > maxWeight) {
                weight = weights[i];
                trucks++;
            }
        }

        return trucks;
    }
}
