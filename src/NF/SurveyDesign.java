package NF;
import java.util.*;

public class SurveyDesign {

    // n students, m supervisors
    // students and supervisors either available on monday or tuesday or (both)
    // supervisor supervises 3 - 12 students
    // max 2 groups a day
    // max 5 people in meeting

    // k = 4: MondayG1, MondayG2, TuesdayG1, TuesdayG2

    // source students(n) monday-students(n) tuesday-students(n) mondayG1(m) mondayG2(m) tuesdayG1(m) tuesdayG2(m) supervisors(m) sink

    // src -> student (0, 1) for all
    // student -> monday-student (if available on monday)
    // student -> tuesday-student (if available on tuesday)

    public static boolean areThereGroups(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues,
                                         boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues,
                                         boolean[][] selected){


        Node source = new Node(0, -n); //all students must have a supervisor -> demand = -n
        Node sink = new Node(-1, n);

        ArrayList<Node> students = new ArrayList<>();
        ArrayList<Node> studentsMonday = new ArrayList<>();
        ArrayList<Node> studentsTuesday = new ArrayList<>();

        //add all students
        for(int i = 1; i <= n; i++) {
            students.add(new Node(i, 0));
            studentsMonday.add(new Node(i, 0));
            studentsTuesday.add(new Node(i, 0));
        }

        ArrayList<Node> supervisors = new ArrayList<>();
        ArrayList<Node> supervisorsMonday1 = new ArrayList<>(); // group 1 monday
        ArrayList<Node> supervisorsMonday2 = new ArrayList<>(); // group 2 monday
        ArrayList<Node> supervisorsTuesday1 = new ArrayList<>();// group 1 tuesday
        ArrayList<Node> supervisorsTuesday2 = new ArrayList<>();// group 2 tuesday

        //add all supervisors
        for(int i = 1; i <= m; i++) {
            supervisors.add(new Node(i, 0));
            supervisorsMonday1.add(new Node(i, 0));
            supervisorsMonday2.add(new Node(i, 0));
            supervisorsTuesday1.add(new Node(i, 0));
            supervisorsTuesday2.add(new Node(i, 0));
        }

        for(int i = 0; i < n; i++) {
            source.addEdge(students.get(i), 0, 1); //src -> student
            if (student_availability_mon[i+1]) students.get(i).addEdge(studentsMonday.get(i), 0, 1); // student -> available mon student
            if (student_availability_tues[i+1]) students.get(i).addEdge(studentsTuesday.get(i), 0, 1); // student -> available tues student
        }

        for(int i = 0; i < m; i++){
            supervisors.get(i).addEdge(sink, 3, 12); //supervisor -> sink (each supervisor can have 3-12 students)

            if (supervisor_availability_mon[i+1]) {
                // supervisor can have max 2 groups per day
                supervisorsMonday1.get(i).addEdge(supervisors.get(i), 0, 5); // supervisorMonday -> supervisor (can have 5 students per meeting)
                supervisorsMonday2.get(i).addEdge(supervisors.get(i), 0, 5);
            }

            if (supervisor_availability_tues[i+1]) {
                supervisorsTuesday1.get(i).addEdge(supervisors.get(i), 0, 5);
                supervisorsTuesday2.get(i).addEdge(supervisors.get(i), 0, 5);
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++) {
                if(selected[i+1][j+1]) {
                    studentsMonday.get(i).addEdge(supervisorsMonday1.get(j), 0, 1);
                    studentsMonday.get(i).addEdge(supervisorsMonday2.get(j), 0, 1);
                    studentsTuesday.get(i).addEdge(supervisorsTuesday1.get(j), 0, 1);
                    studentsTuesday.get(i).addEdge(supervisorsTuesday2.get(j), 0, 1);
                }
            }
        }

        List<Node> nodes = new ArrayList<>();
        nodes.add(source);
        nodes.add(sink);
        nodes.addAll(students);
        nodes.addAll(studentsMonday);
        nodes.addAll(studentsTuesday);
        nodes.addAll(supervisors);
        nodes.addAll(supervisorsMonday1);
        nodes.addAll(supervisorsMonday2);
        nodes.addAll(supervisorsTuesday1);
        nodes.addAll(supervisorsTuesday2);
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

        public boolean hasCirculation() {
            this.removeLowerBounds();
            int D = this.removeSupplyDemand();
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
            if (Dmin != Dplus) {
                throw new IllegalArgumentException("Demand and supply are not equal!");
            }
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


