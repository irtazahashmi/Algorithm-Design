package NF;

import java.util.*;

public class ProjectSelectionHasCirculation {

    // n hospitals
    // m is the people willing to help
    // c is the required vaccinations at each hospital (c1...cn)
    // d is the available vaccinations at each hospital (d1...dn)
    // e is the travel time between people and hospitals e[i][j], 1 <= i <= n & 1 <= j <= m
    // f is the number of vaccinations a person can carry

    // Do all hospitals get required vaccinations?


    public static boolean needAMedic(int n, int[] c, int[] d, int m, int[][] e, int[] f) {
        int totalVaccinesNeeded = 0;
        for(int i : c)  totalVaccinesNeeded += i;

        // total supply for source node
        Node source = new Node(0, -totalVaccinesNeeded);
        // total demand for sink node
        Node sink = new Node(1, totalVaccinesNeeded);

        // src -> hospital with cap = vaccination required at hospital
        List<Node> hospitals = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            Node hospital = new Node(i, 0);
            hospitals.add(hospital);
            source.addEdge(hospital, 0, c[i]);
        }

        // person -> sink with cap = person can carry
        List<Node> people = new ArrayList<>();
        for(int i = 1; i <= m; i++){
            Node person = new Node(i, 0);
            people.add(person);
            person.addEdge(sink, 0, f[i]);
        }

        //adding edge if distance is less than 50
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m ; j++){
                if(e[i+1][j+1] <= 50){
                    hospitals.get(i).addEdge(people.get(j), 0, Integer.MAX_VALUE/10);
                }
            }
        }

        //add all nodes
        List<Node> nodes = new ArrayList<>();
        nodes.add(source);
        nodes.add(sink);
        nodes.addAll(hospitals);
        nodes.addAll(people);

        // if feasible circulation
        Graph g = new Graph(nodes);
        return g.hasCirculation();
    }


    //--------------------------------- LIBRARY CODE ---------------------------------------------------

    static class Graph {

        public List<Node> nodes;

        public Node source;

        public Node sink;

        public Graph(List<Node> nodes) {
            this.nodes = nodes;
            this.source = null;
            this.sink = null;
        }

        public Node getSink() {
            return sink;
        }

        public Node getSource() {
            return source;
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public boolean equals(Object other) {
            if (other instanceof Graph) {
                Graph that = (Graph) other;
                return this.nodes.equals(that.nodes);
            }
            return false;
        }

        // check if the graph's demand = supply
        public boolean hasCirculation() {
            this.removeLowerBounds();
            int D = this.removeSupplyDemand(); // the demand to fulfill
            int x = MaxFlow.maximizeFlow(this);
            return x == D;
        }

        public void removeLowerBounds() {
            for (Node n : this.getNodes()) {
                for (Edge e : n.edges) {
                    if (e.lower > 0) {
                        e.capacity -= e.lower;
                        e.backwards.capacity -= e.lower;
                        e.backwards.flow -= e.lower;
                        e.from.d += e.lower;
                        e.to.d -= e.lower;
                        e.lower = 0;
                    }
                }
            }
        }

        public int removeSupplyDemand() {
            int Dplus = 0, Dmin = 0;
            int maxId = 0;
            for (Node n : this.getNodes()) {
                maxId = Math.max(n.id, maxId);
            }
            Node newSource = new Node(maxId + 1, 0);
            Node newSink = new Node(maxId + 2, 0);

            for (Node n : this.getNodes()) {
                if (n.d < 0) {
                    newSource.addEdge(n, 0, -n.d);
                    Dmin -= n.d;
                } else if (n.d > 0) {
                    n.addEdge(newSink, 0, n.d);
                    Dplus += n.d;
                }
                n.d = 0;
            }

            if (Dmin != Dplus) throw new IllegalArgumentException("Demand and supply are not equal!");
            this.nodes.add(newSource);
            this.nodes.add(newSink);
            this.source = newSource;
            this.sink = newSink;
            return Dplus;
        }
    }

    static class Node {

        public int id;

        public int d;

        public Collection<Edge> edges;

        /**
         *  Create a new node
         *  @param id: Id for the node.
         *  @param d: demand for the node. Remember that supply is represented as a negative demand.
         */
        public Node(int id, int d) {
            this.id = id;
            this.d = d;
            this.edges = new ArrayList<Edge>();
        }

        public void addEdge(Node to, int lower, int upper) {
            Edge e = new Edge(lower, upper, this, to);
            edges.add(e);
            to.getEdges().add(e.getBackwards());
        }

        public Collection<Edge> getEdges() {
            return edges;
        }

        public int getId() {
            return id;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node that = (Node) other;
                if (id == that.getId())
                    return edges.equals(that.getEdges());
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getId());
            sb.append(" ");
            sb.append(this.getEdges().size());
            sb.append(":");
            for (Edge e : this.getEdges()) {
                sb.append("(");
                sb.append(e.from.getId());
                sb.append(" --[");
                sb.append(e.lower);
                sb.append(',');
                sb.append(e.capacity);
                sb.append("]-> ");
                sb.append(e.to.getId());
                sb.append(")");
            }
            return sb.toString();
        }
    }

    static class Edge {

        public int lower;

        public int capacity;

        public int flow;

        public Node from;

        public Node to;

        public Edge backwards;

        public Edge(Edge e) {
            this.lower = 0;
            this.flow = e.getCapacity();
            this.capacity = e.getCapacity();
            this.from = e.getTo();
            this.to = e.getFrom();
            this.backwards = e;
        }

        public Edge(int lower, int capacity, Node from, Node to) {
            this.lower = lower;
            this.capacity = capacity;
            this.from = from;
            this.to = to;
            this.flow = 0;
            this.backwards = new Edge(this);
        }

        public void augmentFlow(int add) {
            assert (flow + add <= capacity);
            flow += add;
            backwards.setFlow(getResidual());
        }

        public Edge getBackwards() {
            return backwards;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getFlow() {
            return flow;
        }

        public Node getFrom() {
            return from;
        }

        public int getResidual() {
            return capacity - flow;
        }

        public Node getTo() {
            return to;
        }

        private void setFlow(int f) {
            assert (f <= capacity);
            this.flow = f;
        }

        public boolean equals(Object other) {
            if (other instanceof Edge) {
                Edge that = (Edge) other;
                return this.capacity == that.capacity && this.flow == that.flow && this.from.getId() == that.getFrom().getId() && this.to.getId() == that.getTo().getId();
            }
            return false;
        }
    }

    static class MaxFlow {

        public static List<Edge> findPath(Graph g, Node start, Node end) {
            Map<Node, Edge> mapPath = new HashMap<Node, Edge>();
            Queue<Node> sQueue = new LinkedList<Node>();
            Node currentNode = start;
            sQueue.add(currentNode);
            while (!sQueue.isEmpty() && currentNode != end) {
                currentNode = sQueue.remove();
                for (Edge e : currentNode.getEdges()) {
                    Node to = e.getTo();
                    if (to != start && mapPath.get(to) == null && e.getResidual() > 0) {
                        sQueue.add(e.getTo());
                        mapPath.put(to, e);
                    }
                }
            }
            if (sQueue.isEmpty() && currentNode != end)
                return null;
            LinkedList<Edge> path = new LinkedList<Edge>();
            Node current = end;
            while (mapPath.get(current) != null) {
                Edge e = mapPath.get(current);
                path.addFirst(e);
                current = e.getFrom();
            }
            return path;
        }

        public static int maximizeFlow(Graph g) {
            int f = 0;
            Node sink = g.getSink();
            Node source = g.getSource();
            List<Edge> path;
            while ((path = findPath(g, source, sink)) != null) {
                int r = Integer.MAX_VALUE;
                for (Edge e : path) {
                    r = Math.min(r, e.getResidual());
                }
                for (Edge e : path) {
                    e.augmentFlow(r);
                }
                f += r;
            }
            return f;
        }
    }
}
