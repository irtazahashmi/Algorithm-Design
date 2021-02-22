package DP.ShortestPath;

import java.util.Arrays;

public class StraightGraphLongestPath {

    // Let 𝐺 be an undirected graph with n nodes.
    // Ordered graph

    // 1) each node goes from lower to higher index
    // 2) every node except last one has atleast one outgoing edge

    // number of nodes = length of path. Longest path?



    // mem[0...n] = infinity
    // mem[0] = 0
    // mem[1] = 1

    //for i <- 1...n
    //  int max = 0;
        // while there is an edge from (j, i)
            // if mem[j] != inf
                // max = max(max, mem[j] + 1)
        // mem[i] = max

    // return mem[n]
}
