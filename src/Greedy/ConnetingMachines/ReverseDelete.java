package Greedy.ConnetingMachines;

import java.io.*;
import java.util.*;

public class ReverseDelete {

  //REVERSE DELETE, UNDIRECTED GRAPH. WHOLE GRAPH.

    // vertices, edges
    // 4 5

    // u, v, cost
    // 0 1 6
    // 1 2 9
    // 0 2 7
    // 1 3 2
    // 0 3 8
  public static String run(InputStream in) {
    return new ReverseDelete().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    
    int n = sc.nextInt(); //nodes
    int m = sc.nextInt(); //edges
      sc.nextInt(); //extra
    
    ArrayList<Edge> edges = new ArrayList<>();
    
    for(int i = 1; i <= m; i++) {
      int from = sc.nextInt();
      int to = sc.nextInt();
      int cost = sc.nextInt();
      edges.add(new Edge(from, to, cost));
    }
    
    sc.close();
    
    //sort by heaviest cost
    Collections.sort(edges, Comparator.comparingInt(e -> e.cost));
    Collections.reverse(edges);
    
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    ArrayList<Edge> mst = new ArrayList<>();
    
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>()); // 1 <= n if graph starts from 1, 0 < n if starts from 0

    //add edges to the graph
    for(Edge e : edges){
      graph.get(e.from).add(e.to);
      graph.get(e.to).add(e.from);
    }
    
    for(Edge e : edges) {
      //remove the costliest edge

        //alternative
      //graph.get(e.from).removeIf(edge -> edge == e.to);
      //graph.get(e.to).removeIf(edge -> edge == e.from);

        ArrayList<Integer> from = graph.get(e.from);
        int index = -1;
        for(int i = 0; i < from.size(); i++) {
            if (e.to == from.get(i)) index = i;
        }
        from.remove(index);
        graph.set(e.from, from);

        ArrayList<Integer> to = graph.get(e.to);
        index = -1;
        for(int i = 0; i < to.size(); i++) {
            if (e.from == to.get(i)) index = i;
        }
        to.remove(index);
        graph.set(e.to, to);
      
      //if graph disconnects, add it back and add node to mst.
      if(!isConnected(graph)) {
        graph.get(e.from).add(e.to);
        graph.get(e.to).add(e.from);
        mst.add(e);
      }
    }
    
    int cost = 0;
    for(Edge e : mst) {
      cost += e.cost;
    }
    
    return cost + " ";
  }
  
 public boolean isConnected(ArrayList<ArrayList<Integer>> graph){
    boolean[] visited = new boolean[graph.size()];
    Queue<Integer> q = new LinkedList<>();
    q.add(0); //if graph starts from 0

    // If graph starts from 1
//     visited[0] = true;
//     q.add(1);
    
    //DFS
    while(!q.isEmpty()){
      int node = q.poll();
      if(!visited[node]){ 
        visited[node] = true;
        ArrayList<Integer> neighbours = graph.get(node);
        for(int n : neighbours){      
          if(!visited[n])q.add(n);
        }
      }
    }
    
    //see if everything visited -> connected.
    for(int i = 0; i < visited.length; i++){
      if (!visited[i]) return false; 
    }
    return true;
  }
  
   class Edge {
    int from;
    int to;
    int cost;

    public Edge(int f, int t, int c) {
      from = f;
      to = t;
      cost = c;
    }
  }
}