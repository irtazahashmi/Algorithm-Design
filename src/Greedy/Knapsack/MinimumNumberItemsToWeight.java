package Greedy.Knapsack;

import java.util.*;

public class MinimumNumberItemsToWeight {

    // Return the minimum number of items we need to get to the weight we want to get to.

    /**
     * @param n the number of item categories
     * @param w the weight we want to achieve with as few items as possible.
     * @param num the number of items in each category c_1 through c_n stored in num[1] through num[n] (NOTE: you should ignore num[0]!)
     * @param weight the weight of items in each category c_1 through c_n stored in weight[1] through weight[n] (NOTE: you should ignore weight[0]!)
     * @return minimum number of items needed to get to the required weight
     */
    public static int run(int n, int w, int[] num, int[] weight) {
        return new MinimumNumberItemsToWeight().solve(n, w, num, weight);
    }

    public int solve(int n, int w, int[] num, int[] weight) {
        ArrayList<Integer> itemWeights = new ArrayList<>();

        // if n = 2, w = 3 -> 3 3
        for(int i = 1; i <= n; i++) {
            int numberOfItems = num[i];
            while(numberOfItems != 0) {
                itemWeights.add(weight[i]);
                numberOfItems--;
            }
        }

        // Sort by maximum weight to get min items needed
        Collections.sort(itemWeights);
        Collections.reverse(itemWeights);

        int itemsNeeded = 0;
        int currWeight = 0;

        for(int i = 0; i < itemWeights.size(); i++) {
            //item too heavy -> SKIP
            if (currWeight + itemWeights.get(i) > w) continue;

            currWeight += itemWeights.get(i);
            if (currWeight <= w) {
                itemsNeeded++;
            }
        }


        return itemsNeeded;
    }
}




