package NF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Graph {

    private List<Node> nodes;
    private Node source;
    private Node sink;

    public Graph(List<Node> nodes, Node source, Node sink) {
        this.nodes = nodes;
        this.source = source;
        this.sink = sink;
    }

    public Node getSink() {return sink;}
    public Node getSource() {return source;}
    List<Node> getNodes() {return nodes;}

    public boolean equals(Object other) {
        if (other instanceof Graph) {
            Graph that = (Graph) other;
            return this.nodes.equals(that.nodes);
        }
        return false;
    }
}

class Node {

    private int id;
    private Collection<Edge> edges;

    public Node(int id) {
        this.id = id;
        this.edges = new ArrayList<Edge>();
    }

    // directed edge.
    void addEdge(Node to, int capacity, int flow, boolean backwards) {
        Edge e = new Edge(capacity, this, to, flow, backwards);
        edges.add(e);
    }

    // undirected edge. adding edge both ways -> residual graph
    public void addEdge(Node to, int capacity) {
        Edge e = new Edge(capacity, this, to);
        edges.add(e);
        to.getEdges().add(e.getBackwards());
    }

    Collection<Edge> getEdges() {return edges;}
    int getId() {return id; }

    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            if (id == that.getId())
                return edges.equals(that.getEdges());
        }
        return false;
    }
}

class Edge {

    private int capacity;
    private int flow;
    private Node from;
    private Node to;
    private boolean isBackwards;
    private Edge backwards;


    protected Edge(int capacity, Node from, Node to, boolean backwards) {
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.flow = 0;
        this.isBackwards = backwards;
    }

    protected Edge(int capacity, Node from, Node to, int flow, boolean backwards) {
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.flow = flow;
        this.isBackwards = backwards;
    }

    public Edge(int capacity, Node from, Node to) {
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.flow = 0;
        this.backwards = new Edge(this);

        // extra line added for edge disjoint paths
        this.backwards.isBackwards = true;
    }

    private Edge(Edge e) {
        this.flow = e.getCapacity();
        this.capacity = e.getCapacity();
        this.from = e.getTo();
        this.to = e.getFrom();
        this.backwards = e;
    }

    int getCapacity() {return capacity;}
    int getFlow() {return flow;}
    Node getFrom() {return from; }
    Node getTo() {return to; }
    public Edge getBackwards() {return backwards;}

    void setFlow(int f) {
        assert (f <= capacity);
        this.flow = f;
    }

    void setCapacity(int capacity) {
        assert (this.flow <= capacity);
        this.capacity = capacity;
    }

    boolean isBackwards() {return this.isBackwards;}

    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge that = (Edge) other;
            return this.capacity == that.capacity && this.flow == that.flow && this.from.getId() == that.getFrom().getId() && this.to.getId() == that.getTo().getId();
        }
        return false;
    }
}