package NF;

import java.io.InputStream;
import java.util.*;

public class ImageSegmentation {

    // nodes/edges
    // 1 2

    // id, f_i, b_i
    // 1 9 1
    // 2 8 2

    //pij = 10
    // 1 2

    public static int solve() {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // pixels(nodes)
        int m = sc.nextInt(); //edges

        ArrayList<Node> nodes = new ArrayList<>();
        //src & sink
        Node src = new Node(0);
        Node sink = new Node(n + 1);

        //add src
        nodes.add(src);

        for(int i = 1; i <= n; i++) {
            int id = sc.nextInt();
            int f_i = sc.nextInt();
            int b_i = sc.nextInt();
            Node node = new Node(id);
            nodes.add(node);
            src.addEdge(node, f_i); //adding edges from s to each node
            node.addEdge(sink, b_i);// adding edges from each node to t
        }

        //add sink
        nodes.add(sink);


        // edges between nodes (pij)
        for(int i = 0; i < m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            // 10 is the pij value. Adding both ways
            nodes.get(from).addEdge(nodes.get(to), 10);
            nodes.get(to).addEdge(nodes.get(from), 10);
        }

        // get max flow using FF
        Graph g = new Graph(nodes, src, sink);
        FordFulkerson.maxFlow(g);

        int maxFlow = 0;
        for(Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();
        return maxFlow;
    }

    public static void main(String[] args) {
        System.out.println(solve()); //3
    }
}
