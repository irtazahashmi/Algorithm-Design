package DP.SegementedLeastSquares;

import java.util.ArrayList;
import java.util.List;

public class ShippingBundlesWithOutput {

    // n bundles
    // fixed shipping cost of c per bundle (fixed cost)
    // cost to ship [i][j] = i, i+1,...j together

    // minimize cost! (x should be sorted by x coordinate)

    public static int solve(int n, int c, int[][] costs) {
        int[] mem = new int[n+1];

        // base case
        mem[0] = 0;

        // go through each column
        for(int j = 1; j <= n; j++) {
            int min = Integer.MAX_VALUE;
            //find min cost in each column till j
            for(int i = 1; i <= j; i++) {
                min = Math.min(min, costs[i][j] + c + mem[i-1]);  //using recurrence relation
            }
            // store min in the mem
            mem[j] = min;
        }

        List<List<Integer>> packages = new ArrayList<>();
        printSolution(c, costs, mem, n, packages);
        System.out.println(packages);
        return mem[n];
    }

    //recover solution
    public static void printSolution(int c, int[][]costs, int mem[], int j, List<List<Integer>> packages) {
        if (j == 0) return;

        int minIndex = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;;
        for(int i = 1; i <= j; i++) {
            // if the bundle was chosen, output it, get the min cost and its index
            if (costs[i][j] + c + mem[i-1] < min) {
                min = costs[i][j] + c + mem[i-1];
                minIndex = i;
            }
        }
        List<Integer> aPackage = new ArrayList<>();
        // these bundles will go together
        for(int i = minIndex; i <= j; i++){
            aPackage.add(i);
        }

        packages.add(aPackage);
        printSolution(c, costs, mem, minIndex - 1, packages);
    }

    public static void main(String[] args) {
        //1&2, 3&4
        int m = 4;
        int c = 8;
        int[][] R = {{0, 0, 0, 0, 0},
                    {0, 0, 2, 100, 100},
                    {0, 0, 0, 2, 100},
                    {0, 0, 0, 0, 2},
                    {0, 0, 0, 0, 0}};
        solve(m,c,R);

        // test 2: 1,2,3,4 (all together)
//       int m = 4;
//       int c = 8;
//       int[][] R = { { 0, 0, 0, 0, 0 }, { 0, 0, 2, 3, 4 }, { 0, 0, 0, 2, 3 }, { 0, 0, 0, 0, 2 }, { 0, 0, 0, 0, 0 } };
//       solve(m,c,R);
    }
}
