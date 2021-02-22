package Greedy.Graphs;

import java.io.*;
import java.util.*;

public class DjikstraModified {

    // Modified Shortest Path Djisktra Algorithm.
    // NOTE: Also adds the number of outgoing edges to total cost.
    // 7 7 1 5
    // 1 2 2
    // 2 3 100
    // 3 4 10
    // 4 5 10
    // 2 6 10
    // 6 7 10
    // 7 4 80
    public static String run(InputStream in) {
        return new DjikstraModified().solve(in);
    }

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        int t = sc.nextInt();
        ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nodes.add(new HashMap<>());
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            nodes.get(u).put(v, cost);
        }
        sc.close();
        return solve(nodes, s, t);
    }


    public String solve( ArrayList<HashMap<Integer, Integer>> nodes, int s, int t){
        if (s == t) return "0";

        int[] distances = new int[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];

        for(int i = 0; i < nodes.size(); i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        pq.add(new Tuple(s, 0));
        distances[s] = 0;

        while(!pq.isEmpty()) {
            Tuple currtup  = pq.remove();
            visited[currtup.id] = true;

            if (currtup.id == t) return Integer.toString(distances[currtup.id]);

            for (int neighbour : nodes.get(currtup.id).keySet()) {
                int newDist = distances[currtup.id] + nodes.get(currtup.id).get(neighbour);

                //cost also includes number of outgoing edges
                newDist += nodes.get(currtup.id).size();

                if(newDist < distances[neighbour]) {
                    Tuple newTup = new Tuple(neighbour, newDist);
                    distances[neighbour] = newDist;
                    pq.add(newTup);
                }
            }

        }

        if (visited[t]) return Integer.toString(distances[t]);
        else return "-1";
    }
}


class Tuple implements Comparable<Tuple> {
    int id;
    int cost;

    Tuple(int id, int cost) {
        this.id = id;
        this.cost=cost;
    }

    //For PQ Sorting
    @Override
    public int compareTo(Tuple o) {
        return this.cost-o.cost;
    }
}