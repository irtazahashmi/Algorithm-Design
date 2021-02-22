package NF;

import java.util.*;

public class ProjectSelectionOutputMincut {

    // output job chosen for project selection using bfs!

    public static Set<Integer> outputSelectedProjects(int n, int[] benefits, int[] costs, int[][] dependencies) {
        Graph g = buildGraph(n, benefits, costs, dependencies);
        FordFulkerson.maximizeFlow(g);

        Set<Integer> selectedProjects = new HashSet<>();
        Queue<Node> q = new LinkedList<>();
        q.add(g.getSource());

        //BFS
        while(!q.isEmpty()) {
            Node curr = q.poll();

            for(Edge e : curr.getEdges()) {
                if (e.getFlow() < e.getCapacity() && e.getTo().getId() > 0 && !selectedProjects.contains(e.getTo().getId())) {
                    selectedProjects.add(e.getTo().getId());
                    q.add(e.getTo());
                }
            }
        }

        return selectedProjects;
    }

    // You have the graph g, whose flow has been maximised.
    // Now you should return the set of node ids from the set containing s of the minimum (s,t)-cut
    public static Set<Integer> outputMinCut(int n, int[] benefits, int[] costs, int[][] dependencies) {
        Graph g = buildGraph(n, benefits, costs, dependencies);
        FordFulkerson.maximizeFlow(g);

        Set<Integer> mincut = new HashSet<>();
        Queue<Node> q = new LinkedList<>();
        q.add(g.getSource());
        mincut.add(g.getSource().getId());

        while(!q.isEmpty()) {
            Node currNode = q.poll();
            for(Edge e : currNode.getEdges()) {
                if (e.getFlow() < e.getCapacity() && !mincut.contains(e.getTo().getId())) {
                    mincut.add(e.getTo().getId());
                    q.add(e.getTo());
                }
            }
        }

        return mincut;
    }



    // Project Selection Code
    private static Graph buildGraph(int n, int[] benefits, int[] costs, int[][] dependencies) {
        Node source = new Node(0);
        Node sink = new Node(n + 1);
        ArrayList<Node> nodes = new ArrayList<>(n + 2);
        nodes.add(source);
        for (int i = 1; i <= n; i++) {
            Node newNode = new Node(i);
            nodes.add(newNode);
        }
        nodes.add(sink);
        for (int i = 1; i <= n; i++) {
            source.addEdge(nodes.get(i), benefits[i]);
            nodes.get(i).addEdge(sink, costs[i]);
            for (int j = 1; j <= n; j++) {
                if (dependencies[i][j] > 0) {
                    nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE / 2);
                }
            }
        }
        return new Graph(nodes, source, sink);
    }
}
