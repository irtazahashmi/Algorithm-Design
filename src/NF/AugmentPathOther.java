package NF;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AugmentPathOther {

    static void augmentPath(Graph g, List<Integer> path) throws IllegalArgumentException {

        for(int i = 0; i < path.size() - 1; i++) {

            // find an edge between i and i+1 (FORWARDS edge)
            Edge e = findEdge(g.getNodes().get(path.get(i)), (g.getNodes().get(path.get(i+1))));
            // if no path is found
            if (e == null) throw new IllegalArgumentException();
            // path cant be incremented because its at MAX FLOW
            if (!e.isBackwards() && e.getFlow() >= e.getCapacity()) throw new IllegalArgumentException();
            // path cant be incremented because capacity = 0 of
            if (e.isBackwards() && e.getCapacity() == 0) throw new IllegalArgumentException();

            // increase the flow by 1 for FORWARD edge, else decrease cap by 1
            if (!e.isBackwards()) e.setFlow(e.getFlow() + 1);
            else e.setCapacity(e.getCapacity() - 1);


            // find an edge between i+1 and i (BACKWARD edge)
            e = findEdge(g.getNodes().get(path.get(i+1)), (g.getNodes().get(path.get(i))));

            // decrease the flow by 1 for forward edge, else increase cap by 1
            if (!e.isBackwards()) e.setFlow(e.getFlow() - 1);
            else e.setCapacity(e.getCapacity() + 1);
        }
    }


    public static Edge findEdge(Node from, Node to) {
        for(Edge e : from.getEdges()) {
            if (e.getTo().equals(to)) return e;
        }
        return null; // no edge found
    }


    // parsing the graph to the given data structure
    static Graph parse(InputStream in) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt(); //nodes
        int m = sc.nextInt(); // edges
        int s = sc.nextInt(); // source
        int t = sc.nextInt(); // sink

        List<Node> nodes = new ArrayList<>();
        for(int i = 0; i < n; i++) nodes.add(new Node(i));

        for(int i = 0; i < m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cap = sc.nextInt();
            int flow = sc.nextInt();
            nodes.get(from).addEdge(nodes.get(to), cap, flow, false);
            nodes.get(to).addEdge(nodes.get(from), flow, 0, true);
        }
        return new Graph(nodes, nodes.get(s), nodes.get(t));
    }
}
