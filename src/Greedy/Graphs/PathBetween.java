package Greedy.Graphs;

import java.io.*;
import java.util.*;

public class PathBetween {
    // Check if there is a path from s to t.
    // 7 7 1 5
    // 1 2 2
    // 2 3 100
    // 3 4 10
    // 4 5 10
    // 2 6 10
    // 6 7 10
    // 7 4 80
    public static String run(InputStream in) {
        return new PathBetween().solve(in);
    }

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        int t = sc.nextInt();

        //STORE DIRECTED GRAPH
        Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            nodes.put(i, new Node());
        }

        for (int i = 1; i <= m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            sc.nextInt();
            nodes.get(from).outgoingEdges.add(nodes.get(to));
        }

        Queue<Node> q = new LinkedList<>();
        q.add(nodes.get(s));
        nodes.get(s).marked = true;

        while (!q.isEmpty()) {
            Node node = q.remove();
            if (node == nodes.get(t)) return "yes";
            for (Node neighbour : node.outgoingEdges) {
                if (!neighbour.marked) {
                    q.add(neighbour);
                    neighbour.marked = true;
                }
            }
        }

        return "no";
    }
}


