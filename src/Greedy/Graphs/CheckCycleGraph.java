package Greedy.Graphs;

import java.io.*;
import java.util.*;

public class CheckCycleGraph {

    // Check if there is a cycle in the graph.
    // 5 6 1
    // 1 2 2
    // 3 2 100
    // 1 3 10
    // 4 5 10
    // 2 4 10
    // 5 3 10
    public static String run(InputStream in) {
        return new CheckCycleGraph().solve(in);
    }

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();

        // Directed Graph
        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 0; i <= n; i++) nodes.put(i, new Node());

        //edges
        for (int i = 1; i <= m ; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            sc.nextInt();
            nodes.get(from).outgoingEdges.add(nodes.get(to));
        }

        sc.close();

        Queue<Node> q = new LinkedList<>();
        q.add(nodes.get(s));
        nodes.get(s).marked = true;

        while(!q.isEmpty()) {
            Node node = q.remove();
            for (Node too : node.outgoingEdges) {
                if (too.marked) return "yes";
                else {
                    q.add(too);
                    too.marked = true;
                }
            }
        }
        return "no";
    }
}

class Node {

    List<Node> outgoingEdges;

    boolean marked;

    public Node() {
        this.outgoingEdges = new ArrayList<>();
        this.marked = false;
    }
}



