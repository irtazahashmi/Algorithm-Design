package NF;

import java.util.*;

public class BaseballEliminationFlightManual {

    // l is the number of flight Lee has done
    // n competitors
    // m slots left
    // the number of flights each of the competitors has done (flights1....n)
    // slot i can be used by competitor j iff compatible[i][j] is true. 1 <= i <= m & 1 <= j <= n

    // can Lee have most flights to his name and WIN?

    public static boolean solve(int l, int n, int m, int[] flights, boolean[][] compatible) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(0);
        Node sink = new Node(n+m+1);
        nodes.add(source);

        // source -> slot cap = 1 because 1 time slot.
        for(int i = 1; i <= m; i++) {
            Node slot = new Node(i);
            nodes.add(slot);
            source.addEdge(slot, 1);
        }

        // aeronaut -> sink cap = l - fi - 1
        for(int i = 1; i <= n; i++) {
            Node aero = new Node(m+i);
            nodes.add(aero);
            // the aeronaut i can only fly less than l if Lee wants to WIN
            aero.addEdge(sink, l - flights[i] - 1);
        }

        // if slot i and aeronaut j compatible -> infinity cap
        for(int i = 1; i <= m; i++) {
            for(int j = m + 1; j <= n+m; j++) {
                if (compatible[i][j-m]) nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE/10);
            }
        }

        nodes.add(sink);

        // find circulation manually
        Graph g = new Graph(nodes, source, sink);
        FordFulkerson.maximizeFlow(g);

        int maxCap = 0;
        int maxFlow = 0;
        for (Edge e : g.getSource().getEdges()) {
            maxFlow += e.getFlow();
            maxCap += e.getCapacity();
        }

        // eliminated if maximum flow < g*
        if (maxFlow < maxCap) return false;
        else return true;
    }
}
