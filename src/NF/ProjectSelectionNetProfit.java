package NF;

import java.util.*;

public class ProjectSelectionNetProfit {

    // n jobs with benefits and costs
    // job i and j are dependent on each other if dependencies[i][j] == 1

    //Output only the (net) value of this release, i.e., the benefit minus the costs of the included job
    //net profit = maxProfit - maxFlow


    public static int solve(int n, int[] benefits, int[] costs, int[][] dependencies) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(0);
        Node sink = new Node(n + 1);

        //init nodes
        nodes.add(source);
        for(int i = 1; i <= n; i++) nodes.add(new Node(i));
        nodes.add(sink);

        for(int i = 1; i <= n; i++) {
            //src -> job i
            source.addEdge(nodes.get(i), benefits[i]);

            //job i -> sink
            nodes.get(i).addEdge(sink, costs[i]);

            // add edge i -> j (inf cap) if compatible
            for(int j = 1; j <= n; j++) {
                if (dependencies[i][j] == 1) { // i depends on j
                    nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE/10);
                }
            }
        }

        Graph g = new Graph(nodes, source, sink);
        FordFulkerson.maximizeFlow(g);

        //max profit
        int maxProfit = 0;
        for(int i : benefits) maxProfit += i;

        // cost
        int maxFlow = 0;
        for(Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();

        // C = sum of pi > 0
        // maxFlow = profit(A)
        //net profit = C - maxFlow
        return maxProfit - maxFlow;
    }
}
