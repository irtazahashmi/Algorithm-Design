package DP.SubsetSums;

public class NumberOfSubsets {

    // Given a set of non-negative integers, and a value sum,
    // determine how many subsets are equal to given sum.
    // O(nW) time and space

    public static int numberOfSubsetIterative(int n, int[] set, int W) {
        int[][] mem = new int[n + 1][W + 1];

        //fill the table
        for (int i = 0; i <= n; i++) mem[i][0] = 1; // sum = 0 -> empty set {} -> 1 set

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                // if number is too big and greater than W
                // then not in optimal solution, ignore it!
                if (set[i - 1] > w) mem[i][w] = mem[i - 1][w];
                    // add subsets, excluding and including n
                else mem[i][w] = mem[i - 1][w] + mem[i - 1][w - set[i - 1]];
            }
        }

        return mem[n][W];
    }

    // Brute force O(2^n)

    public static int numberOfSubsetRecursive(int n, int[] set, int W) {
        // sum = 0, empty set{} -> 1
        if (W == 0) return 1;
        // sum != 0 and empty set{} -> 0 set
        if (n == 0) return 0;

        // if number is too big and greater than W
        // then not in optimal solution, ignore it!
        if (set[n - 1] > W) return numberOfSubsetRecursive(n-1, set, W);

        // return the number of sets that can be made by excluding n and including n
        return numberOfSubsetRecursive(n-1, set, W) + numberOfSubsetRecursive(n-1, set, W - set[n-1]);
    }


    public static void main(String[] args) {
        // Test 1: 2 -> (3 + 4 + 2, 5 + 4)
        int set[] = {3, 34, 4, 12, 5, 2 };
        int W = 9;
        System.out.println(numberOfSubsetIterative(set.length, set, W));


        //Test 2: 2 -> (7 + 3 + 8, 5 + 3 + 2 + 8)
        int[] set2 = { 7, 3, 2, 5, 8 };
        int W2 = 18;
        System.out.println(numberOfSubsetIterative(set2.length, set2, W2));
    }
}
