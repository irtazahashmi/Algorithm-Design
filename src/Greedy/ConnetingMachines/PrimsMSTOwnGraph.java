package Greedy.ConnetingMachines;

import java.io.*;
import java.util.*;


public class PrimsMSTOwnGraph {

  // vertices, edges
  // 4 5

  // u, v, cost
  // 0 1 6
  // 1 2 9
  // 0 2 7
  // 1 3 2
  // 0 3 8

  public static String run(InputStream in) {
    return new PrimsMSTOwnGraph().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);

    int n = sc.nextInt(); //nodes
    int m = sc.nextInt(); //edges

    ArrayList<Node> graph = new ArrayList<>();

    for(int i = 0; i <= n; i++) graph.add(new Node()); // <= n if graph starts from 1, < n if graph starts from 0

    for(int i = 1; i <= m; i++) {
      int from = sc.nextInt();
      int to = sc.nextInt();
      int cost = sc.nextInt();
      graph.get(from).neighbours.add(new Edge(to, cost));
      graph.get(to).neighbours.add(new Edge(from, cost));
    }

    sc.close();

    // the start node
    int start = 1;
    boolean visited[] = new boolean[graph.size()];
    PriorityQueue<Edge> pq = new PriorityQueue<>();
    visited[start] = true;

    ArrayList<Edge> mst = new ArrayList<>();

    //add all neighbours of start into the queue
    for (Edge e : graph.get(start).neighbours) pq.add(e);

  	//dfs. keep adding the min edge without cycle
    while (!pq.isEmpty()) {
      Edge e = pq.remove();
      if (visited[e.to]) continue;
      visited[e.to] = true;
      mst.add(e);
      for (Edge neig : graph.get(e.to).neighbours) {
        pq.add(neig);
      }
    }

    int cost = 0;
    for(Edge e : mst) {
      cost += e.cost;
    }
    return cost + "";
  }

  class Edge implements Comparable<Edge>{
    int to;
    int cost;

    public Edge(int t, int c) {
      to = t;
      cost = c;
    }

    public int compareTo(Edge e) {
      return this.cost - e.cost;
    }
  }

  class Node {
    ArrayList<Edge> neighbours;

    public Node() {
      neighbours = new ArrayList<>();
    }
  }
}