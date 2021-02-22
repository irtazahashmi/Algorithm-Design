package Greedy.ConnetingMachines;

import java.io.*;
import java.util.*;

public class PrimsMSTPQ {
    // Return min mst cost using djisktra. STARTS FROM 1!
    // 7 7 1

    // 1 2 2
    // 2 3 100
    // 3 4 10
    // 4 5 10
    // 2 6 10
    // 6 7 10
    // 7 4 80
  public static String run(InputStream in) {
    return new PrimsMSTPQ().solve(in);
  }

 public String solve(InputStream in) {
    Scanner sc = new Scanner(in);
    
    int n = sc.nextInt();
    int m = sc.nextInt();
    int s = sc.nextInt();

    ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<>();
    for (int i = 0; i <= n; i++) nodes.add(new HashMap<>());
    
    for (int i = 0; i < m; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      int cost = sc.nextInt();
      nodes.get(u).put(v, cost);
      nodes.get(v).put(u, cost);
    }

    if (n <= 1) return "0";
    return solve(nodes, s);
  }

  public String solve(ArrayList<HashMap<Integer, Integer>> nodes, int s) {
    boolean[] visited = new boolean[nodes.size()];
    int[] distances = new int[nodes.size()];

    for (int i = 0; i < nodes.size(); i++) distances[i] = Integer.MAX_VALUE / 2;

    PriorityQueue<Tuple> q = new PriorityQueue<>();
    q.add(new Tuple(s, 0));
    distances[s] = 0;
    int cost = 0;

    while (!q.isEmpty()) {
      Tuple curTuple = q.poll();
      if (visited[curTuple.id]) continue;
      visited[curTuple.id] = true;

      // No MST
      if (curTuple.cost >= Integer.MAX_VALUE / 2) return Integer.toString(Integer.MAX_VALUE / 2);

      cost += curTuple.cost;
      //get all neighbours
      for (int neighbour : nodes.get(curTuple.id).keySet()) {
          //get neighbour dist
        int newDistance = nodes.get(curTuple.id).get(neighbour);
        //if neighbour not visited and new dist is smaller than recorded dist
        if (!visited[neighbour] && newDistance < distances[neighbour]) {
          Tuple newTuple = new Tuple(neighbour, newDistance);
          distances[neighbour] = newTuple.cost;
          q.add(newTuple);
        }
      }
    }

    //MST Complete
    return Integer.toString(cost);
  }

  class Tuple implements Comparable<Tuple> {
    int id;
    int cost;

    Tuple(int id, int cost) {
      this.id = id;
      this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Tuple tuple = (Tuple) o;
      return id == tuple.id;
    }

    @Override
    public int hashCode() {
      return id;
    }

    @Override
    public int compareTo(Tuple o) {
      int res = Integer.signum(this.cost - o.cost);
      if (res == 0) {
        return Integer.signum(this.id - o.id);
      }
      return res;
    }
  }
}