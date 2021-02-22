package DP.Knapsack;

public class Recursive {

    // O(2^n) -> Brute force

    public static int knapsack(int n, int profit[], int[] weights, int W) {
        // base cases
        if (n == 0 || W == 0) return 0;

        // we are going over the weight, skip the item
        if (weights[n - 1] > W) return knapsack(n-1, profit, weights, W);

        // pick the max: including n or excluding n
        return Math.max(knapsack(n - 1, profit, weights, W - weights[n - 1]) + profit[n - 1],
                        knapsack(n-1, profit, weights, W));
    }

    public static void main(String[] args) {
        //Test 1: 220
        int n = 3;
        int profit[] = new int[] { 60, 100, 120 };
        int weights[] = new int[] { 10, 20, 30 };
        int W = 50;
        System.out.println(knapsack(n, profit, weights, W));
    }
}
