package DP.ShortestPath;

public class StraightGraphShortestPath {

    // Let 𝐺 be an undirected graph with n nodes.
    // A subset of the nodes is called an independent set if no two of them are joined by an edge.
    // if |vi - v| = 1, not independent.

    // find shortest independent path (min weight)

    public static int weight(int n, int[] nodes) {
        int[] mem = new int[n + 1];
        mem[0] = 0;
        mem[1] = nodes[1];
        for (int i = 2; i <= n; i++) {
            mem[i] = Math.max(mem[i-1], nodes[i] + mem[i-2]);
        }
        return mem[n];
    }
}
