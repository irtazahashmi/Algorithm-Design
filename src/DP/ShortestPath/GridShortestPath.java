package DP.ShortestPath;

import java.util.Arrays;

public class GridShortestPath {

    // a grid, uses ladder to go up. No ladder to go down. Return min ladder required to go from s to t.
    // s = (0, 0) , t = (n-1, m-1)
    // O(mn) time and space

    //3 5 6
    //4 2 1     ---> 3 - 4 - 2 - 1 (min ladder height required is 1)

    public static int shortestPath(int n, int m, int[][] graph) {
        int[][] mem = new int[n][m];
        // everything infinity for all vertex
        for(int[] row : mem) Arrays.fill(row, Integer.MAX_VALUE);
        //base case
        mem[0][0] = 0;

        //go through grid n * m times
        for(int k = 0; k < n * m; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < m; j++) {

                    // base case
                    if (i == 0 && j == 0) continue;

                    //left min.
                    mem[i][j] = (j - 1 < 0) ? mem[i][j] : Math.min(mem[i][j], Math.max(mem[i][j-1], graph[i][j] - graph[i][j-1]));

                    //upper min
                    mem[i][j] = (i - 1 < 0) ? mem[i][j] : Math.min(mem[i][j], Math.max(mem[i-1][j], graph[i][j] - graph[i-1][j]));

                    //right min
                    mem[i][j] = (j + 1 >= m) ? mem[i][j] : Math.min(mem[i][j], Math.max(mem[i][j+1], graph[i][j]-graph[i][j+1]));

                    // lowest min
                    mem[i][j] = (i + 1 >= n) ? mem[i][j] : Math.min(mem[i][j], Math.max(mem[i+1][j], graph[i][j] - graph[i+1][j]));

                }
            }
        }
        return mem[n-1][m-1];
    }
}
