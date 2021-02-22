package NF;

import java.util.*;

public class FordFulkersonScaled {

    // runs in O(m^2 * logC)

    static int maxFlow(Graph g) {
        long startTime = System.nanoTime();
        maximizeFlow(g); // apply ford-fulkerson algorithm

        // get flow leaving source (max-flow)
        int flow = 0;
        for (Edge e : g.getSource().getEdges()) flow += e.getFlow();
        long endTime = System.nanoTime();


        System.out.println(endTime - startTime + "ns");
        return flow;
    }

    public static void maximizeFlow(Graph g) {
        Node source = g.getSource();
        Node sink = g.getSink();
        int C = 0;
        for(Edge e : source.getEdges()) C += e.getCapacity();
        // scaling factor to be largest power of 2 and <= C
        int scalingFactor = (C % 2 != 0) ? C-1 : C;

        while(scalingFactor >= 1){
            List<Edge> path;
            // while there exists a path between s and t
            while((path = findPath(source, sink)) != null) {

                // find bottleneck edge capacity in the s-t path
                int bottleneck = Integer.MAX_VALUE;
                for (Edge e : path) bottleneck = Math.min(bottleneck, e.getCapacity() - e.getFlow());

                // only augment path that have a bottleneck of atleast scaling factor
                if (bottleneck <= scalingFactor){
                    // Augment the flow on the path
                    for (Edge e : path) {
                        e.setFlow(e.getFlow() + bottleneck);
                        e.getBackwards().setFlow(e.getCapacity() - e.getFlow());
                    }
                }
            }
            scalingFactor /= 2;
        }
    }

    public static List<Edge> findPath(Node source, Node sink) {
        Map<Node, Edge> mapPath = new HashMap<>();
        Queue<Node> q = new LinkedList<>();
        Node currNode = source; // start from the src
        q.add(currNode); // add the src to q

        //bfs
        while(!q.isEmpty() && currNode != sink) {
            currNode = q.remove();
            for(Edge n: currNode.getEdges()) {
                Node to = n.getTo();
                // no cycle, check if to Node not explored yet
                if (to != source && mapPath.get(to) == null && n.getCapacity() > n.getFlow()) {
                    q.add(n.getTo());
                    mapPath.put(to, n);
                }
            }
        }

        // No path found
        if (q.isEmpty() && currNode != sink) return null;
        // Trace back path
        LinkedList<Edge> path = new LinkedList<Edge>();
        Node curr = sink;
        while (mapPath.get(curr) != null) {
            Edge e = mapPath.get(curr);
            path.addFirst(e);
            curr = e.getFrom();
        }
        return path;
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

        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = 0; x < 4; x++) nodes.add(new Node(x));
        nodes.get(0).addEdge(nodes.get(1), 100);
        nodes.get(0).addEdge(nodes.get(2), 100);
        nodes.get(1).addEdge(nodes.get(2), 1);
        nodes.get(1).addEdge(nodes.get(3), 100);
        nodes.get(2).addEdge(nodes.get(3), 100);
        Graph g = new Graph(nodes, nodes.get(0), nodes.get(3));
        System.out.println(maxFlow(g)); //200
    }
}
