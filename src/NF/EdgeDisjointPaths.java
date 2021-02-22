package NF;

import java.util.*;

public class EdgeDisjointPaths {


    public static int maxEdgeDisjointPaths(int V, int[][] graph, int s, int t) {
        //init nodes
        List<Node> nodes = new ArrayList<>();
        for(int i = 0; i < V; i++) nodes.add(new Node(i));

        // add edges between each node
        for(int i = 0; i < V; i++) {
            for(int j = 0; j < V; j++) {
                if (graph[i][j] == 1) {
                    nodes.get(i).addEdge(nodes.get(j), 1);
                }
            }
        }

        // find max flow
        Graph g = new Graph(nodes, nodes.get(s), nodes.get(t));
        FordFulkerson.maximizeFlow(g);
        int maxFlow = 0;
        for(Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();

        findPathsDFS(g, s, t, maxFlow);

        //max flow = number of edge disjoint paths
        return maxFlow;
    }


    //added extra line in library-> this.backwards.isBackwards = true;
    public static void findPathsDFS(Graph g, int s, int t, int maxFlow) {
        List<List<Integer>> paths = new ArrayList<>(); // all possible paths
        for(int i = 0; i < maxFlow; i++) paths.add(new ArrayList<>());

        Set<Edge> known = new HashSet<>();
        Node start = g.getNodes().get(s);

        int path = 0; // current path index

        for(Edge e : start.getEdges()) {
            if (e.getFlow() == 1 && !e.isBackwards() && !known.contains(e)) {
                paths.get(path).add(e.getTo().getId());
                known.add(e);
                findPath(e.getTo(), t, known, path, paths);
                path++;
            }
        }

        // add first and last node in path
        for(int i = 0; i < maxFlow; i++){
            paths.get(i).add(0, s);
            paths.get(i).add(paths.get(i).size(), t);
        }

        System.out.println(paths);
    }

    private static void findPath(Node node, int t, Set<Edge> known, int path, List<List<Integer>> paths) {
        for(Edge e : node.getEdges()) {
            if (e.getTo().getId() == t) break;

            if (e.getFlow() == 1 && !e.isBackwards() && !known.contains(e)) {
                paths.get(path).add(e.getTo().getId());
                known.add(e);
                findPath(e.getTo(), t, known, path, paths);
            }
        }
    }


    public static void main(String[] args) {
        //Test 1: 2 -> [[0, 2, 6, 5, 7], [0, 3, 6, 7]]
        int V = 8;
        int graph[][] = {{0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1},
                        {0, 1, 0, 0, 1, 0, 0, 1},
                        {0, 0, 0, 0, 0, 1, 0, 1},
                        {0, 0, 0, 0, 0, 0, 0, 0}};
        int s = 0;
        int t = 7;
        maxEdgeDisjointPaths(V, graph, s, t);

        // Test 2: 2 -> [[0, 1, 3, 5], [0, 2, 4, 5]]
//        int V = 6;
//        int graph[][] = {{0, 1, 1, 0, 0, 0},
//                        {0, 0, 0, 1, 0, 0},
//                        {0, 0, 0, 0, 1, 0},
//                        {0, 0, 0, 0, 0, 1},
//                        {0, 0, 1, 0, 0, 1},
//                        {0, 0, 0, 0, 0, 0}};
//        int s = 0;
//        int t = 5;
//        maxEdgeDisjointPaths(V, graph, s, t);

//        // Test 3: 3 -> [[0, 1, 3, 5], [0, 2, 4, 5], [0, 6, 5]]
//        int V = 7;
//        int graph[][] = {{0, 1, 1, 0, 0, 0, 1},
//                        {0, 0, 0, 1, 0, 0, 0},
//                        {0, 0, 0, 0, 1, 0, 0},
//                        {0, 0, 0, 0, 0, 1, 0},
//                        {0, 0, 1, 0, 0, 1, 0},
//                        {0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 1, 0}};
//        int s = 0;
//        int t = 5;
//        maxEdgeDisjointPaths(V, graph, s, t);
    }
}
