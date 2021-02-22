package DP.ShortestPath;

import java.util.*;

public class EdgesToMatrix {

    // converts all edges to a matrix
    // for FLOYD WARSHALL for example

    final static int INF = 9999;

    public static void etoM(int nodes, Set<Edge> edges){
        int[][] matrix = new int[nodes][nodes];
        for(int[] row : matrix) Arrays.fill(row, INF);

        // same id = 0;
        for(int i = 0; i < nodes; i++) {
            for(int j = 0; j < nodes; j++) {
                if (i == j) matrix[i][j] = 0;
            }
        }

        for(Edge e : edges) {
            int from = e.getFrom().getId();
            int to = e.getTo().getId();
            int cost = e.getCost();
            matrix[from - 1][to - 1] = cost; // id starts from 1, matrix from 0
        }

        printMatrix(matrix, nodes);
    }


    private static void printMatrix(int[][] matrix, int nodes) {
        // print matrix
        for(int i = 0; i < nodes; i++){
            for(int j = 0; j < nodes; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Set<Edge> edges = new HashSet<>();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        edges.add(new Edge(node1, node2, 3));
        edges.add(new Edge(node2, node1, 8));
        edges.add(new Edge(node1, node4, 7));
        edges.add(new Edge(node4, node1, 2));
        edges.add(new Edge(node3, node4, 1));
        edges.add(new Edge(node3, node1, 5));
        edges.add(new Edge(node2, node3, 2));

        etoM(4, edges);
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
