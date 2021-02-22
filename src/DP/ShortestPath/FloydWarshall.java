package DP.ShortestPath;

import java.text.CollationElementIterator;
import java.util.*;

public class FloydWarshall {
    final static int INF = 9999;

    // O(V^3) runtime, O(V^3) space
    // prints shortest path as well

    public static void floydWarshall(int V, int[][] graph) {
        int[][] mem = new int[V][V];
        // track preds to to print the shortest path
        int pred[][] = new int[V][V];

        //init the solution matrix as the weights of graph
        for(int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
            	// if (i == j) mem[i][j] = 0; need this?
                mem[i][j] = graph[i][j];

                // init pred
                if (graph[i][j] != INF && i != j) pred[i][j] = i; 
                else pred[i][j] = -1;
            }
        }

        //taking each vertex as intermediate vertex k
        for(int k = 0; k < V; k++) {
            // start vertex
            for(int i = 0; i < V; i++) {
                // end vertex
                for(int j = 0; j < V; j++) {
                	// is k is intermediate vertex in shortest path from i to j?
                    if(mem[i][k] + mem[k][j] < mem[i][j]){ 
                        mem[i][j] = mem[i][k] + mem[k][j];
                        pred[i][j] = pred[k][j];
                    }
                }
            }
        }

        // detecting negative cycle
        for(int i = 0; i < V; i++) {
            if (mem[i][i] < 0) System.out.println("negative cycle!");
        }

        // print resultant matrix
        for(int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(mem[i][j] + " ");
            }
            System.out.println();
        }

        // print shortest path from 0 to 2
        System.out.println();
        printPath(pred, 0, 2);
    }

    //shortest path print
    public static void printPath(int[][] pred, int start, int end) {
        if(start < 0 || end < 0 || start >= pred.length || end >= pred.length)
            throw new IllegalArgumentException();

        ArrayList<Integer> path = new ArrayList<>();
        path.add(end);

        while (start != end) {
            end = pred[start][end];
            if (end == -1) return;// no pred
            path.add(end);
        }

        Collections.reverse(path);
        System.out.println(path);
    }

    public static void main(String[] args) {
        // Test 1: Path from 0 to 2: [0, 1, 2]
        int V = 4;
        int graph[][] =
                {{0, 5,  INF, 10},
                {INF, 0,   3,  INF},
                {INF, INF, 0,   1},
                {INF, INF, INF, 0} };
        floydWarshall(V, graph);


        //Test 2: Path from 0 to 2: [0, 4, 3, 2]
//        int V = 5;
//        int graph[][] =  {{0, 3, 8, INF, -4 },
//                          {INF, 0, INF, 1, 7 },
//                          {INF, 4, 0, INF, INF},
//                          { 2, INF, -5, 0, INF},
//                          {INF, INF, INF, 6, 0 }};
//        floydWarshall(V, graph);
    }
}
