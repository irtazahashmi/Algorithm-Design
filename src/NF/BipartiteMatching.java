package NF;

import java.util.*;

public class BipartiteMatching {

    // Bipartite matching

    //6 6 8
    //1 2 3 4 5 6
    //7 8 9 10 11 12
    //1 8
    //1 9
    //3 7
    //3 10
    //4 9
    //5 9
    //5 10
    //6 12

    // X left nodes, Y right nodes, m edges
    public static int solve(int X, int Y, int m, List<Integer> leftNodes, List<Integer> rightNodes,
                            List<Integer> from, List<Integer> to) {

        List<Node> nodes = new ArrayList<>();
        Node source = new Node(0);
        Node sink = new Node(X + Y + 1);

        nodes.add(source);
        for(int i = 1; i <= X + Y; i++) nodes.add(new Node(i));

        // add edge from source to X with cap 1
        for(Integer x : leftNodes) source.addEdge(nodes.get(x), 1);

        //adding edge from X to Y with cap 1
        for(int i = 0; i < m; i++) {
            int f = from.get(i);
            int t = to.get(i);
            nodes.get(f).addEdge(nodes.get(t), 1);
        }

        //add edge from Y to sink with cap 1
        for(int i = 0; i < rightNodes.size(); i++) nodes.get(rightNodes.get(i)).addEdge(sink, 1);

        nodes.add(sink);

        //find max pairs
        Graph g = new Graph(nodes, source, sink);
        FordFulkerson.maxFlow(g);

        int maxFlow = 0;
        for(Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();

        outputPairs(g);
        return maxFlow;
    }

    //output selected pairs for bipartite matching
    public static void outputPairs(Graph g) {
        List<List<Integer>> pairs = new ArrayList<>();
        for(Edge e: g.getSource().getEdges()) {
            if (e.getFlow() == 1) {
                List<Integer> pair = new ArrayList<>();
                pair.add(e.getTo().getId());
                for(Edge e2 : e.getTo().getEdges()) {
                    if (e2.getFlow() == 1) {
                        pair.add(e2.getTo().getId());
                    }
                }
                pairs.add(pair);
            }
        }

        System.out.println(pairs);
    }

    public static void main(String[] args) {
        //Test 1: 5 -> [[1, 8], [3, 7], [4, 9], [5, 10], [6, 12]]
        int X = 6; //left hand nodes
        int Y = 6; //right hand nodes
        int m = 8; //edges
        List<Integer> leftNodes = new ArrayList<>();
        for(int i = 1; i <= X; i++) leftNodes.add(i);
        List<Integer> rightNodes = new ArrayList<>();
        for(int i = X+1; i <= X+Y; i++) rightNodes.add(i);

        List<Integer> from = new ArrayList<>();
        List<Integer> to = new ArrayList<>();

        from.add(1);to.add(8);
        from.add(1);to.add(9);
        from.add(3);to.add(7);
        from.add(3);to.add(10);
        from.add(4);to.add(9);
        from.add(5);to.add(7);
        from.add(5);to.add(10);
        from.add(6);to.add(12);
        System.out.println(solve(X, Y, m, leftNodes, rightNodes, from, to));

        //Test 2: 4 -> [[1, 6], [2, 7], [3, 8], [4, 10]]
//        int X = 5; //left hand nodes
//        int Y = 5; //right hand nodes
//        int m = 10; //edges
//        List<Integer> leftNodes = new ArrayList<>();
//        for(int i = 1; i <= X; i++) leftNodes.add(i);
//        List<Integer> rightNodes = new ArrayList<>();
//        for(int i = X+1; i <= X+Y; i++) rightNodes.add(i);
//
//        List<Integer> from = new ArrayList<>();
//        List<Integer> to = new ArrayList<>();
//
//        from.add(1);to.add(6);
//        from.add(1);to.add(7);
//        from.add(2);to.add(7);
//        from.add(3);to.add(6);
//        from.add(3);to.add(8);
//        from.add(3);to.add(9);
//        from.add(4);to.add(7);
//        from.add(4);to.add(10);
//        from.add(5);to.add(7);
//        from.add(5);to.add(10);
//
//        System.out.println(solve(X, Y, m, leftNodes, rightNodes, from, to));

        //Test 3: 1 -> [[1, 3]]
//        int X = 2; //left hand nodes
//        int Y = 1; //right hand nodes
//        int m = 2; //edges
//        List<Integer> leftNodes = new ArrayList<>();
//        for(int i = 1; i <= X; i++) leftNodes.add(i);
//        List<Integer> rightNodes = new ArrayList<>();
//        for(int i = X+1; i <= X+Y; i++) rightNodes.add(i);
//
//        List<Integer> from = new ArrayList<>();
//        List<Integer> to = new ArrayList<>();
//
//        from.add(1);to.add(3);
//        from.add(2);to.add(3);
//
//        System.out.println(solve(X, Y, m, leftNodes, rightNodes, from, to));
    }

}
