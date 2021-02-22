package NF;

import java.util.*;

public class FordFulkerson {

    //O(mC) or O(mnC) (m edges, n vertices, C = maximal flow)

    static int maxFlow(Graph g) {
        maximizeFlow(g); // apply ford-fulkerson algorithm

        // get flow leaving source (max-flow)
        int maxFlow = 0;
        for (Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();

        return maxFlow;
    }

    public static void maximizeFlow(Graph g) {
        Node source = g.getSource();
        Node sink = g.getSink();

        List<Edge> path;
        // while there exists a path between s and t in residual graph
        while((path = findPath(source, sink)) != null) {

            // find bottleneck edge capacity in the s-t path
            int bottleneckEdge = Integer.MAX_VALUE;
            for (Edge e : path) bottleneckEdge = Math.min(bottleneckEdge, e.getCapacity() - e.getFlow());

            // Augment the flow on the path
            for (Edge e : path) {
                e.setFlow(e.getFlow() + bottleneckEdge);
                e.getBackwards().setFlow(e.getCapacity() - e.getFlow());
            }
        }
    }

    // find a path between souce and sink
    public static List<Edge> findPath(Node source, Node sink) {
        Map<Node, Edge> mapPath = new HashMap<>(); // book-keeping to construct path 
        
        Queue<Node> q = new LinkedList<>();
        Node currNode = source; // start from the src
        q.add(currNode); // add the src to q

        //bfs
        while(!q.isEmpty() && currNode != sink) {
            currNode = q.remove();
            for(Edge n: currNode.getEdges()) {
                Node to = n.getTo();
                // no cycle, check if to Node not explored yet, flow constraint
                if (to != source && mapPath.get(to) == null && n.getCapacity() > n.getFlow()) {
                    q.add(n.getTo());
                    mapPath.put(to, n);
                }
            }
        }

        // No path found
        if (q.isEmpty() && currNode != sink) return null;
        
        // build back path
        LinkedList<Edge> path = new LinkedList<>();
        Node curr = sink;
        while (mapPath.get(curr) != null) {
            Edge e = mapPath.get(curr);
            path.addFirst(e);
            curr = e.getFrom();
        }
        return path; //return path
    }

    public static void main(String[] args) {
//        ArrayList<Node> nodes = new ArrayList<>();
//        for (int x = 0; x < 4; x++) nodes.add(new Node(x));
//        nodes.get(0).addEdge(nodes.get(1), 10);
//        nodes.get(0).addEdge(nodes.get(2), 10);
//        nodes.get(1).addEdge(nodes.get(3), 10);
//        nodes.get(2).addEdge(nodes.get(3), 10);
//        nodes.get(1).addEdge(nodes.get(2), 2);
//        Graph g = new Graph(nodes, nodes.get(0), nodes.get(3));
//        System.out.println(maxFlow(g)); //20

//        ArrayList<Node> nodes = new ArrayList<>();
//        for (int x = 0; x < 4; x++) nodes.add(new Node(x));
//        nodes.get(0).addEdge(nodes.get(1), 100);
//        nodes.get(0).addEdge(nodes.get(2), 100);
//        nodes.get(1).addEdge(nodes.get(2), 1);
//        nodes.get(1).addEdge(nodes.get(3), 100);
//        nodes.get(2).addEdge(nodes.get(3), 100);
//        Graph g = new Graph(nodes, nodes.get(0), nodes.get(3));
//        System.out.println(maxFlow(g)); //200

        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = 0; x < 8; x++) nodes.add(new Node(x));
        nodes.get(0).addEdge(nodes.get(1), 4);
        nodes.get(0).addEdge(nodes.get(2), 5);
        nodes.get(0).addEdge(nodes.get(3), 7);
        nodes.get(1).addEdge(nodes.get(4), 5);
        nodes.get(1).addEdge(nodes.get(5), 2);
        nodes.get(2).addEdge(nodes.get(4), 3);
        nodes.get(2).addEdge(nodes.get(6), 3);
        nodes.get(3).addEdge(nodes.get(6), 3);
        nodes.get(4).addEdge(nodes.get(7), 4);
        nodes.get(5).addEdge(nodes.get(7), 3);
        nodes.get(6).addEdge(nodes.get(7), 5);
        Graph g = new Graph(nodes, nodes.get(0), nodes.get(7));
        System.out.println(maxFlow(g));
    }
}
