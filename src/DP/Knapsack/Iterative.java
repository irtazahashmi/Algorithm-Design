package DP.Knapsack;

public class Iterative {

    // O(nW) runtime and space

    public static int knapsack(int n, int profit[], int[] weights, int W) {
        int[][] mem = new int[n+1][W+1];

        // base case
        for(int i = 0; i <= n; i++) mem[i][0] = 0; // if W = 0, we cant take any object -> 0 profit
        for(int w = 0; w <= W; w++) mem[0][w] = 0; // if n = 0, cant choose any object -> 0 profit

        for(int i = 1; i <= n; i++) {
            for(int w = 1; w <= W; w++) {
                // if curr weight above w, skip the item!
                if (weights[i - 1] > w) mem[i][w] = mem[i - 1][w];
                // else return max of: excluding n, including n.
                else mem[i][w] = Math.max(mem[i - 1][w], mem[i-1][w - weights[i - 1]] + profit[i-1]);
            }
        }

        System.out.println(mem[7][10]);

        printChosenItems(n, profit, weights, W, mem);
       return mem[n][W];
    }

    public static void printChosenItems(int n, int[] profit, int[] weights, int W, int[][] mem) {
        //print the chosen items by backtracking
        while (n > 0) {
            // we added another item to knapsack
            if (mem[n][W] != mem[n-1][W]) {
                System.out.println("Item Profit: " + profit[n-1]);
                W -= weights[n - 1];
            }
            n--;
        }
    }

    public static void main(String[] args) {
        //Test 1: 220. Item chosen: 2, 3
//        int n = 3;
//        int profit[] = new int[] { 60, 100, 120 };
//        int weights[] = new int[] { 10, 20, 30 };
//        int W = 50;
//        System.out.println(knapsack(n, profit, weights, W));

        //Test 2: 8
        int n2 = 8;
        int profit2[] = new int[] {4,5,3,5,3,9,7,11 };
        int weights2[] = new int[] {8,5,12,11,1,14,3,3};
        int W2 = 15;
        System.out.println(knapsack(n2, profit2, weights2, W2));

    }
}
