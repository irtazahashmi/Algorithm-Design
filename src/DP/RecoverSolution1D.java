package DP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RecoverSolution1D {

    // given nodes, the result and the table, return the indexes that were picked using
    // back tracking

    public static List<Integer> solve(int n, int[] nodes, int result, int[] mem) {
      List<Integer> sol = new ArrayList<>();
      int currWeight = result;
      
      for(int i = n; i > 0; i--) {
        if (mem[i] == currWeight) {
          sol.add(i);
          currWeight -= nodes[i];
        }
      }
      
      Collections.reverse(sol);
      return sol;
    }

    public static void main(String[] args) {
        // Test 1: [1,4,7]
        int n = 7;
        int[] nodes = { 0, 1, 18, 18, 1, 18, 18, 1 };
        int[] mem = { 0, 1, 18, 18, 2, 20, 20, 3 };
        int optValue = 3;
        System.out.println(solve(n, nodes, optValue, mem));
    }
}
