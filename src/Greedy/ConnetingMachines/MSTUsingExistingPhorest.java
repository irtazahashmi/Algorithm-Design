package Greedy.ConnetingMachines;

import java.util.*;

public class MSTUsingExistingPhorest {

    // Optimizes the provided Phorest to be as close to an MST as possible.

  /**
   * @param n the number of nodes in the network
   * @param g all edges in the full graph
   * @param p the edges in the Phorest
   * @return total edge weight of optimized Phorest
   */
  public static String run(int n, Edge[] g, Edge[] p) {
    return new MSTUsingExistingPhorest().solve(n, g, p);
  }

  public String solve(int nodes, Edge[] graph, Edge[] phorest) {
    Edge[] mst = new Edge[nodes - 1];
    UnionFind uf = new UnionFind(nodes);

    // the size of the mst already using existing phorest
    int p = phorest.length;

    //add everything to mst from existing phorest
    for(int i = 0; i < p; i++) {
      mst[i] = phorest[i];
      uf.join(mst[i].x, mst[i].y);
    }

    //adding edges till it comes as close to mst as possible
    for(Edge e : graph) {
      if (uf.join(e.x, e.y)) {
        mst[p] = e;
        p++;
      }
      if (p == nodes - 1)
        break;
    }

    //summing the weight
    int totalweight = 0;
    for(Edge e : mst) totalweight += e.l;

    return totalweight + "";
  }


  static Edge[] makeGraph(Scanner sc) {
    int m = sc.nextInt();
    Edge[] edges = new Edge[m];
    for (int i = 0; i < m; i++) edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
    Arrays.sort(edges, Comparator.comparingInt(e -> e.l));
    return edges;
  }


  class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public boolean join(int x, int y) {
        int xrt = find(x);
        int yrt = find(y);
        if (rank[xrt] > rank[yrt])
            parent[yrt] = xrt;
        else if (rank[xrt] < rank[yrt])
            parent[xrt] = yrt;
        else if (xrt != yrt)
            rank[parent[yrt] = xrt]++;
        return xrt != yrt;
    }

    public int find(int x) {
        return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }
  }
}

class Edge {
    int x, y, l;

    public Edge(int from, int to, int length) {
      x = from;
      y = to;
      l = length;
    }
}
