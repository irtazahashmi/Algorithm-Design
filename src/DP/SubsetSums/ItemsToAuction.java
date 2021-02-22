package DP.SubsetSums;

import java.util.*;

public class ItemsToAuction {

    // n items with vi value each
    // max V allowed
    // auction items such that sum of v <= V

    public static void solve(int n, int V, int[] values) {
        int mem[][] = new int[n+1][V+1];

        for(int v = 0; v <= V; v++) mem[0][v] = v; // if 0 item chosen
        for(int i = 0; i <= n; i++) mem[i][0] = 0; // if w = 0, 0 items can be chosen -> 0 profit.

        for(int i = 1; i <= n; i++) {
            for(int v = 1; v <= V; v++) {
                // IGNORE i = 0, that's why we start from values[i] instead of values[i-1]!!!
                if (values[i] > v) mem[i][v] = mem[i-1][v];
                else mem[i][v] = Math.min(mem[i-1][v], mem[i-1][v - values[i]]);
            }
        }

        printAuctionedItems(n, V, values, mem);
    }

    public static void printAuctionedItems(int n, int V, int[] values, int[][] M ){
        List<Integer> res = new ArrayList<>();
        int maxValueAllowed = V;

        while(maxValueAllowed > 0 && n > 0) {
            if (M[n-1][V] != M[n][V]) {
                res.add(n);
                maxValueAllowed -= values[n];
            }
            n--;
        }
        System.out.println("Auctioned Items: " + res);
    }

    public static void main(String[] args) {
        int n = 3;
        int V = 5;
        //ignore values[0]!!!
        int[] values = {0, 3, 4, 2 };
        solve(n, V, values);
    }
}
