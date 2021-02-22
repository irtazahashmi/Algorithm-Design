package DP.SubsetSums;

public class MaximumValueToWSubset {

    // Given a set of non-negative integers, and a value sum W
    // determine the max value -> sum of values <= W
    // O(nW)

    // Also gives the subset


    public static int maxSubsetSumIterative(int n, int[] set, int W) {
        int mem[][] = new int[n+1][W+1];

        for(int i = 0; i <= W; i++) mem[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (set[i-1] > w) mem[i][w] = mem[i-1][w];
                else mem[i][w] =  Math.max(mem[i-1][w - set[i-1]] + set[i-1], mem[i-1][w]);
            }
        }

        printItemValues(n, W, set, mem);
        return mem[n][W];
    }

    public static void printItemValues(int n, int W, int[] values, int[][] mem ){
        int maxValueAllowed = W;

        System.out.print("Items Values: ");
        while(maxValueAllowed > 0 && n > 0) {
            // if item chosen print it out!
            if (mem[n-1][W] != mem[n][W]) {
                if (maxValueAllowed >= values[n-1]) {
                    System.out.print(values[n - 1] + " ");
                    maxValueAllowed -= values[n - 1];
                }
            }
            n--;
        }

        System.out.println();
    }

    // Brute force (2^n)
    public static int maxSubsetSumRecursive(int n, int[] set, int W) {
        if (W == 0 || n ==0) return 0;

        if(set[n-1] > W) return maxSubsetSumRecursive(n-1, set, W);

        else {
            return Math.max(set[n-1] + maxSubsetSumRecursive(n-1, set, W - set[n-1]) ,
                    maxSubsetSumRecursive(n-1,set, W));
        }
    }

    public static void main(String[] args) {
//        Test 1: 11
        int[] set = {2, 2, 2, 2, 2, 1};
        int W = 12;
        System.out.println(maxSubsetSumIterative(set.length, set, W));

        //Test 2: 12
        int[] set2 = {3, 34, 4, 12, 5, 2};
        int W2 = 13;
        System.out.println(maxSubsetSumIterative(set2.length, set2, W2));

        //Test 3: 18
        int[] set3 = {4, 2, 10, 12};
        int W3 = 20;
        System.out.println(maxSubsetSumIterative(set3.length, set3, W3));
    }

}
