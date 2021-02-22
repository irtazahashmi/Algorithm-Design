package NF;

import java.util.*;

public class ProjectSelectionWorkerJob {

    // members with available time, skills
    // jobs with required time, skills

    // can all jobs be done with given members (maxFlow == total Job Time Required)?

    public boolean solve() {
        Scanner sc = new Scanner(System.in);

        int numberOfMembers = sc.nextInt(); //members
        int numberOfProjects = sc.nextInt(); //amount of jobs

        Node source = new Node("source");
        Node sink = new Node("sink");

        // parsing members
        ArrayList<Node> memberNodes = new ArrayList<>();

        ArrayList<String> memberNames = new ArrayList<>();
        ArrayList<Integer> availableTimes = new ArrayList<>();
        ArrayList<List<String>> skillsOfMember = new ArrayList<>();

        for(int i = 0; i < numberOfMembers; i++) {
            String memberName = sc.next();
            memberNames.add(memberName);
            int availTime = sc.nextInt();
            availableTimes.add(availTime);
            int numOfSkills = sc.nextInt();
            List<String> skills = new ArrayList<>();
            for(int j = 0; j < numOfSkills; j++) skills.add(sc.next());
            skillsOfMember.add(skills);

            //add source -> member edge
            Node newMember = new Node(memberName);
            memberNodes.add(newMember);
            source.addEdge(newMember, availTime);
        }


        //parse projects
        ArrayList<Node> projectNodes = new ArrayList<>();
        ArrayList<String> projectNames = new ArrayList<>();
        ArrayList<Integer> timeTakes = new ArrayList<>();
        ArrayList<List<String>> skillsRequired = new ArrayList<>();

        for(int i = 0; i < numberOfProjects; i++) {
            String projectName = sc.next();
            projectNames.add(projectName);
            int timeTake = sc.nextInt();
            timeTakes.add(timeTake);
            int numOfSkills = sc.nextInt();
            List<String> skillsReq = new ArrayList<>();
            for(int j = 0; j < numOfSkills; j++) skillsReq.add(sc.next());
            skillsRequired.add(skillsReq);

            //add edge project -> sink
            Node newProject = new Node(projectName);
            projectNodes.add(newProject);
            newProject.addEdge(sink, timeTake);
        }

        //add edges between members and projects IF COMPATIBLE (precedence constraints)
        for(int i = 0; i < numberOfMembers; i++) {
            for(int j = 0; j < numberOfProjects; j++) {

                //check compatible
                boolean compatible = true;
                List<String> skill = skillsOfMember.get(i);
                for(String skillsReq: skillsRequired.get(j)) {
                    if (!skill.contains(skillsReq)) compatible = false;
                }

                //if compatible member->job (infinite)
                if (compatible) {
                    memberNodes.get(i).addEdge(projectNodes.get(j), Integer.MAX_VALUE / 10);
                }
            }
        }

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addAll(memberNodes);
        nodes.addAll(projectNodes);
        nodes.add(source);
        nodes.add(sink);
        Graph g = new Graph(nodes, source, sink);
        g.maximizeFlow();

        //calculate max flow
        int maxFlow = 0;
        for (Edge e : g.getSource().getEdges()) maxFlow += e.getFlow();

        // Compute total job times
        int totalJobTime = 0;
        for (int i = 0; i < numberOfProjects; i++) totalJobTime += timeTakes.get(i);

        // Max flow should be equal to total job times, otherwise not all jobs are completed.
        return maxFlow == totalJobTime;
    }

    public static void main(String[] args) {
        ProjectSelectionWorkerJob projectSelection = new ProjectSelectionWorkerJob();
        System.out.println(projectSelection.solve());
    }



    //--------------------------------- LIBRARY CODE ---------------------------------------------------
    class MaxFlow {

        private List<Edge> findPath(Graph g, Node start, Node end) {
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

         public void maximizeFlow(Graph g) {
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
            }
        }
    }



    class Graph {

        private Collection<Node> nodes;

        private Node source;

        private Node sink;

        public Graph(Collection<Node> nodes, Node source, Node sink) {
            this.nodes = nodes;
            this.source = source;
            this.sink = sink;
        }

        public Node getSink() {
            return sink;
        }

        public Node getSource() {
            return source;
        }

        public Collection<Node> getNodes() {
            return nodes;
        }

        public void maximizeFlow() {
            MaxFlow m = new MaxFlow();
            m.maximizeFlow(this);
        }

        public boolean equals(Object other) {
            if (other instanceof Graph) {
                Graph that = (Graph) other;
                return this.nodes.equals(that.nodes);
            }
            return false;
        }
    }

    class Node {

        protected String id;

        protected Collection<Edge> edges;

        public Node(String id) {
            this.id = id;
            this.edges = new ArrayList<Edge>();
        }

        public void addEdge(Node to, int capacity) {
            Edge e = new Edge(capacity, this, to);
            edges.add(e);
            to.getEdges().add(e.getBackwards());
        }

        public Collection<Edge> getEdges() {
            return edges;
        }

        public String getId() {
            return id;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node that = (Node) other;
                if (id.equals(that.getId()))
                    return edges.equals(that.getEdges());
            }
            return false;
        }
    }

    class Edge {

        protected int capacity;

        protected int flow;

        protected Node from;

        protected Node to;

        protected Edge backwards;

        private Edge(Edge e) {
            this.flow = e.getCapacity();
            this.capacity = e.getCapacity();
            this.from = e.getTo();
            this.to = e.getFrom();
            this.backwards = e;
        }

        protected Edge(int capacity, Node from, Node to) {
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
                return this.capacity == that.capacity && this.flow == that.flow && this.from.getId().equals(that.getFrom().getId()) && this.to.getId().equals(that.getTo().getId());
            }
            return false;
        }
    }
}



