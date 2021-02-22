package NF;

import java.io.InputStream;
import java.util.*;

public class AugmentPathBook {

    // Augments the flow over the given path by 1 if possible (BOOK VERSION)
    // throw IllegalArgumentException if impossible

    static void augmentPath(Graph g, List<Integer> path) throws IllegalArgumentException {
        for(int i = 0; i < path.size() - 1; i++) {

            // find forward edge between i and i + 1
            Edge e = findEdge(g.getNodes().get(path.get(i)), g.getNodes().get(path.get(i+1)));

            // no edge -> no augmenting path
            if (e == null) throw new IllegalArgumentException();

            // cant augment more: max capacity reached
            if (e.getCapacity() == 0) throw new IllegalArgumentException();

            e.setCapacity(e.getCapacity() - 1);

            // find backward edge
            e = findEdge(g.getNodes().get(path.get(i+1)), g.getNodes().get(path.get(i)));

            e.setCapacity(e.getCapacity() + 1);

        }
    }

    // find edge between node s and t
    public static Edge findEdge(Node s, Node t) {
        for(Edge e : s.getEdges()) {
            if (e.getTo().equals(t)) return e;
        }
        return null;
    }

    //parse residual graph
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
            nodes.get(from).addEdge(nodes.get(to), cap - flow); // forward edge
            nodes.get(to).addEdge(nodes.get(from), flow); // backward edge
        }

        return new Graph(nodes, nodes.get(s), nodes.get(t));
    }
}
