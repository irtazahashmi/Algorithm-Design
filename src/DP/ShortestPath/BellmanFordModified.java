package DP.ShortestPath;

import java.util.*;

public class BellmanFordModified {

    // find shortest path between V[1] and V[g]
    // one of the costs can be / 2 (most expensive one would be best)

    public static double solveProper(int n, int m, int g, Node[] V, Set<Edge> E) {
        double[][] mem = new double[n + 1][2];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 2; j++) {
                mem[i][j] = Integer.MAX_VALUE;
            }
        }
        mem[1][1] = mem[1][0] = 0;

        for (int counter = 1; counter <= n; counter++) {
            for (Edge e : E) {
                // dont use the discount
                mem[e.to.getId()][0] = Math.min(mem[e.to.getId()][0], e.cost + mem[e.from.getId()][0]);
                //use the discount
                mem[e.to.getId()][1] = Math.min(mem[e.to.getId()][1], Math.min(mem[e.from.getId()][0] + e.cost * 0.5, mem[e.from.getId()][1] + e.cost));
            }
        }

        if (mem[g][1] == Integer.MAX_VALUE) return -1;
        return mem[g][1];
    }


    public static double solveProper2(int n, int m, int g, Node[] V, Set<Edge> E) {
        if (g == 1) return 0.0;

        int dist[] = new int[n+1];
        int pred[] = new int[n+1];
        for(int i = 0; i <= n; i++) dist[i] = Integer.MAX_VALUE;
        dist[0] = 0;

        // start at 1
        int start = 1;
        dist[start] = 0;

        // V - 1 relax edges
        for(int i = 1; i <= n - 1; i++) {
            for(Edge e: E) {
                Node u = e.getFrom();
                Node v = e.getTo();
                int cost = e.getCost();
                // check if d[u] + cvw < dist[v], if yes update dist[v] (u -> v)
                if (dist[u.getId()] + cost < dist[v.getId()] && dist[u.getId()] != Integer.MAX_VALUE) {
                    dist[v.getId()] = dist[u.getId()] + cost;
                    pred[v.getId()] = u.getId();
                }
            }
        }

        // find path from 1 to g
        List<Integer> path = new ArrayList<>();
        int predIndex = g;
        path.add(g);
        while (predIndex > 1) {
            path.add(pred[predIndex]);
            predIndex = pred[predIndex];
        }

        // find the costs of the path
        List<Integer> costs = new ArrayList<>();
        Collections.reverse(path);
        for(int i = 0; i < path.size() - 1; i++) {
            int f = path.get(i);
            int t = path.get(i+1);
            for(Edge e: E) {
                if (e.getFrom().getId() == f && e.getTo().getId() == t) {
                    costs.add(e.getCost());
                }
            }
        }

        // total cost = total costs - (max cost/2)
        int totalCost = 0;
        for(int i : costs) totalCost += i;
        double help = (double) Collections.max(costs) / 2;
        return totalCost - help;
    }





    static class Node {

        protected int id;

        /**
         *  Create a new node
         *  @param id: Id for the node.
         */
        public Node(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node that = (Node) other;
                return this.id == that.id;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class Edge {

        protected int cost;

        protected Node from;

        protected Node to;

        protected Edge(Node from, Node to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Edge edge = (Edge) o;
            return cost == edge.cost && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cost, from, to);
        }
    }
}
