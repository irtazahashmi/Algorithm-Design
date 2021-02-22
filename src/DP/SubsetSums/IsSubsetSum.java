package DP.SubsetSums;

public class IsSubsetSum {

    // Given a set of non-negative integers, and a value sum,
    // determine if there is a subset of the given set with sum equal to given sum.

    // O(nW) time and space -> pseudo-polynomial

    public static boolean isSubsetSumIterative(int n, int[] set, int W) {
        boolean[][] mem = new boolean[n + 1][W + 1];

        // base case
        for(int i = 0; i <= n; i++) mem[i][0] = true; // sum = 0 -> empty set {} -> true

        for(int i = 1; i <= n; i++) {
            for(int w = 1; w <= W; w++) {

                // if number is too big and greater than W
                // then not in optimal solution, ignore it!
                if (set[i - 1] > w) mem[i][w] = mem[i-1][w];
                // else check if W can be reached excluding i or including i
                else mem[i][w] = mem[i - 1][w] || mem[i - 1][w - set[i - 1]];
            }
        }

        printOneSet(n, set, W, mem);
        return mem[n][W];
    }

    //Prints one of the sets that is equal to the sum
    public static void printOneSet(int n, int[] set, int W, boolean mem[][]) {
        int currWeight = W;
        while(currWeight > 0 && n > 0) {
            // if chosen
            if (mem[n][currWeight]) {
                // not chosen
                if (!mem[n-1][currWeight]) {
                    System.out.println("Number Chosen: " + set[n - 1]); // n-1 because set size = n and mem size = n + 1
                    currWeight -= set[n - 1];
                }
            }
            n--;
        }
    }

    // Brute force
    // Running time is O(2^n)
    public static boolean isSubsetSumRecursive(int n, int[] set, int W) {
        // base cases
        // if sum == 0, we have an empty set {} ->  true.
        if (W == 0) return true;
        // if sum != 0 and we have empty set {} -> false
        if (n == 0) return false;

        // if number is too big and greater than W
        // then not in optimal solution, ignore it!
        if (set[n - 1] > W) return isSubsetSumRecursive( n - 1, set, W);

        //check if the W can be reached with excluding last element or including last element
        return isSubsetSumRecursive( n - 1, set, W) || isSubsetSumRecursive( n - 1, set, W - set[n - 1]);
    }

    public static void main(String[] args) {
        // Test 1: True
        int set[] = {7, 3, 2, 5, 8 };
        int W = 14;
//        System.out.println(isSubsetSumRecursive(set.length, set, W));
        System.out.println(isSubsetSumIterative(set.length, set, W));

        //Test 2: False
        int[] set2 = {4, 4, 3, 5, 8 };
        int W2 = 18;
//        System.out.println(isSubsetSumRecursive(set2.length, set2, W2));
//        System.out.println(isSubsetSumIterative(set2.length, set2, W2));

        //Test 3: True
        int[] set3 = { 7, 3, 2, 5, 8 };
        int W3 = 18;
//        System.out.println(isSubsetSumRecursive(set3.length, set3, W3));
//        System.out.println(isSubsetSumIterative(set3.length, set3, W3));

        //Test 4: True
        int set4[] = {3, 34, 4, 12, 5, 2};
        int W4 = 9;
//        System.out.println(isSubsetSumRecursive(set4.length, set4, W4));
//        System.out.println(isSubsetSumIterative(set4.length, set4, W4));
    }

}
