package DP.ShortestPath;

import java.util.ArrayList;

public class BellmanFordAll {

    // finds shortest path to all other vertices and also DETECTS NEGATIVE CYCLE!
    // O(VE) if |E| <= |V|^2, else O(n^3)
    // O(V) space!


    public static void bellmanFord(int V, int E, ArrayList<Edge> edges, int s) {
        // opt solution
        int dist[] = new int[V];

        // init all distances to infinity
        for(int i = 0; i < V; i++) dist[i] = Integer.MAX_VALUE;
        dist[s] = 0;

        // relaxing all edges V - 1 times
        for(int i = 0; i < V - 1; i++) {
            for(int j = 0; j < E; j++) {
                int u = edges.get(j).from;
                int v = edges.get(j).to;
                int cost = edges.get(j).cost;
                // check if d[u] + cvw < dist[v], if yes update dist[v] (u -> v)
                if (dist[u] + cost < dist[v] && dist[u] != Integer.MAX_VALUE)
                    dist[v] = dist[u] + cost;
            }
        }

        // if we have negative cycle, then bellmanFord doesn't work
        for (int j = 0; j < E; ++j) {
            int u = edges.get(j).from;
            int v = edges.get(j).to;
            int cost = edges.get(j).cost;
            // negative cycle detected. Even after V-1 relaxation, the dist[u] is changing!
            if (dist[u] + cost < dist[v] && dist[u] != Integer.MAX_VALUE) {
                System.out.println("Negative Cycle!");
                return;
            }
        }

        // prints min distance from s to all other v
        for (int i = 0; i < V; i++) System.out.println(dist[i]);
    }

    public static void main(String[] args) {
        //Test 1: from source to other nodes distance: (0, -1, 2, -2, 1)
        int V = 5;
        int E = 8;
        ArrayList<Edge> edges = new ArrayList<>();
        Edge e1 = new Edge(0, 1, -1);
        Edge e2 = new Edge(0, 2, 4);
        Edge e3 = new Edge(1, 2, 3);
        Edge e4 = new Edge(1, 3, 2);
        Edge e5 = new Edge(1, 4, 2);
        Edge e6 = new Edge(3, 2, 5);
        Edge e7 = new Edge(3, 1, 1);
        Edge e8 = new Edge(4, 3, -3);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        bellmanFord(V, E, edges, 0);

        //Test 2: from source to other nodes distance: (0, 1, 3, 5, 0, 4, 3)
//        int V = 7;
//        int E = 10;
//        ArrayList<Edge> edges = new ArrayList<>();
//        Edge e1 = new Edge(0, 1, 6);
//        Edge e2 = new Edge(0, 2, 5);
//        Edge e3 = new Edge(0, 3, 5);
//        Edge e4 = new Edge(3, 2, -2);
//        Edge e5 = new Edge(2, 1, -2);
//        Edge e6 = new Edge(1, 4, -1);
//        Edge e7 = new Edge(2, 4, 1);
//        Edge e8 = new Edge(3, 5, -1);
//        Edge e9 = new Edge(4, 6, 3);
//        Edge e10 = new Edge(5, 6, 3);
//        edges.add(e1);
//        edges.add(e2);
//        edges.add(e3);
//        edges.add(e4);
//        edges.add(e5);
//        edges.add(e6);
//        edges.add(e7);
//        edges.add(e8);
//        edges.add(e9);
//        edges.add(e10);
//        bellmanFord(V, E, edges, 0);

        //Test 3: Negative Cycle!
//        int V = 4;
//        int E = 5;
//        ArrayList<Edge> edges = new ArrayList<>();
//        Edge e1 = new Edge(2, 1, -10);
//        Edge e2 = new Edge(3, 2, 3);
//        Edge e3 = new Edge(0, 3, 5);
//        Edge e4 = new Edge(0, 1, 4);
//        Edge e5 = new Edge(1, 3, 5);
//        edges.add(e1);
//        edges.add(e2);
//        edges.add(e3);
//        edges.add(e4);
//        edges.add(e5);
//        bellmanFord(V, E, edges, 0);
    }


    static class Edge {
        int from;
        int to;
        int cost;

        public Edge(int f, int t, int c) {
            from=f;
            to=t;
            cost=c;
        }
    }
}
