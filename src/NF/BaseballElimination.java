package NF;

import java.io.InputStream;
import java.util.*;

public class BaseballElimination {

    // Returns true if team x can still WIN OR TIE the cup.

    public static boolean solve(InputStream in) {
        Scanner sc = new Scanner(in);

        int m = sc.nextInt(); // m teams

        int x = sc.nextInt(); // team x
        int wx = sc.nextInt(); // wins for x so far
        int gx = sc.nextInt(); // games left to be played by x
        int mx = wx + gx; // max possible wins for x

        sc.nextLine();
        sc.nextLine();

        List<Node> nodes = new ArrayList<>();
        for(int i = 1; i <= m; i++){
            if (i != x)  nodes.add(new Node(i)); // exclude x
        }

        Node source = new Node(0);
        Node sink = new Node(m + 1);

        nodes.add(source);
        nodes.add(sink);

        for(int i = 0; i < m - 1; i++) {
            int id = sc.nextInt(); // team id
            int wi = sc.nextInt();// wins of i
            int gi = sc.nextInt();// number of games to be played for i

            if (wi > mx) return false; // // x cant win at all

            // teamID -> sink cap = (mx - wi) because WIN OR TIE
            nodes.get(id).addEdge(sink, mx - wi);

            // count games
            Map<Integer, Integer> games = new HashMap<>();
            for(int j = 0; j < gi; j++) {
                int against = sc.nextInt();
                //to make sure we dont have (1, 2) & (2,1) && exclude x
                if (against < id && against != x) {
                    int n = games.getOrDefault(against, 0);
                    games.put(against, n + 1);
                }
            }

            // make a node for each different game
            for(Integer against: games.keySet()) {
                Node game = new Node(nodes.size());
                nodes.add(game);
                //add edge between source and a game
                source.addEdge(game, games.get(against));
                // add edge between game and possible winner (inf cap)
                game.addEdge(nodes.get(id), Integer.MAX_VALUE/10);
                game.addEdge(nodes.get(against), Integer.MAX_VALUE/10);
            }
        }


        // get circulation
        Graph g = new Graph(nodes, source, sink);
        FordFulkerson.maxFlow(g);

        int maxFlow = 0;
        int maxCap = 0;

        for (Edge e : g.getSource().getEdges()) {
            maxFlow += e.getFlow();
            maxCap += e.getCapacity();
        }

        //x eliminated if maximum flow < g*
        if (maxFlow < maxCap) return false;
        // if maximum flow == g*, x can tie or strictly win
        else return true;
    }
}
