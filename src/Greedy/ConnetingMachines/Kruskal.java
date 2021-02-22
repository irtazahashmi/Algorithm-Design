package Greedy.ConnetingMachines;

import java.io.*;
import java.util.*;


public class Kruskal {

  // Kruskal using union find
  // Store UNDIRECTED GRAPH

  // vertices, edges, budget
  // 4 5 8

  // u, v, cost
  // 0 1 6
  // 1 2 9
  // 0 2 7
  // 1 3 2
  // 0 3 8
  public static String run(InputStream in) {
    return new Kruskal().solve(in);
  }

  public String solve(InputStream in) {
    Scanner sc = new Scanner(in);

    int n = sc.nextInt(); // locations(nodes)
    int m = sc.nextInt(); // connections(edges)
    int budget = sc.nextInt();

    ArrayList<Edge> edges = new ArrayList<>();
    for(int i = 1; i <= m; i++) edges.add(new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()));

    Collections.sort(edges);
    UnionFind uf = new UnionFind(n); // n + 1 instead of n when graph starts from 1
    ArrayList<Edge> mst = new ArrayList<>();
    int index = 0;

    // find mst
    for(Edge e : edges) {
      //if not in same set, add to the same set
      if (uf.union(e.from, e.to)) {
        mst.add(e);
        index++;
      }
      if (index == n - 1) break;
    }

    int totalCost = 0;
    int connections = 0;
    Collections.sort(mst);

    // find totalCost and connections
    for(Edge e : mst) {
      totalCost += e.cost;
      if (totalCost <= budget) connections++;
    }

    return totalCost + " " + connections;
  }


  class Edge implements Comparable<Edge> {
    int from;
    int to;
    int cost;

    public Edge(int f, int t, int c) {
      from = f;
      to = t;
      cost = c;
    }

    @Override
    public int compareTo(Edge o) {
      return this.cost - o.cost;
    }
  }

  class UnionFind {
    private int[] parent;
    private int[] rank;

    // Union Find structure implemented with two arrays for Union by Rank
    public UnionFind(int size) {
      parent = new int[size];
      rank = new int[size];
      for (int i = 0; i < size; i++) parent[i] = i;
    }

    /**
     * Merge two subtrees if they have a different parent, input is array indices
     * @param i a node in the first subtree
     * @param j a node in the second subtree
     * @return true iff i and j had different parents.
     */
    boolean union(int i, int j) {
      int setI = find(i);
      int setJ = find(j);

      if (setI == setJ) return false;
      else if (rank[setI] < rank[setJ]) parent[setI] = setJ;
      else {
        parent[setJ] = setI;
        if (rank[setI] == rank[setJ]) rank[setI]++;
      }

      return true;
    }

    /**
     * NB: this function should also do path compression
     * @param i index of a node
     * @return the root of the subtree containg i.
     */
    int find(int i) {
      if (parent[i] != i) parent[i] = find(parent[i]);
      return parent[i];
    }

    // Return the rank of the trees
    public int[] getRank() {
      return rank;
    }

    // Return the parent of the trees
    public int[] getParent() {
      return parent;
    }
  }
}