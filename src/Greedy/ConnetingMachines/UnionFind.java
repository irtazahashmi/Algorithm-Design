package Greedy.ConnetingMachines;

import java.util.*;

class UnionFind {

  private int[] parent;
  private int[] rank;

  // Union Find structure implemented with two arrays for Union by Rank
  public UnionFind(int size) {
    parent = new int[size];
    rank = new int[size];
    for (int i = 0; i < size; i++) // for graphs starting at 0
      parent[i] = i;
  }

  /**
   * Merge two subtrees if they have a different parent, input is array indices.
   * @param i a node in the first subtree.
   * @param j a node in the second subtree.
   * @return true iff i and j had different parents.
   */
   //merges the sets i and j are in
   //always attaches the shorter tree to the root of the taller tree.
  // initially rank is 0. If rank different, higher rank is new rank.
  boolean union(int i, int j) {
    int seti = find(i);//find root of i
    int setj = find(j);//find root of j
    
    if (seti == setj) return false; //nothing to merge. roots are same.
    else if (rank[seti] < rank[setj]) parent[seti] = setj; //parent of seti is connected setj because setj bigger
    else {
      parent[setj] = seti; //parent of setj connected to seti because seti bigger
      if (rank[seti] == rank[setj]) //same height, hang left subtree with right. 
        rank[seti] = rank[seti] + 1; //rank increases by 1.
    }

    return true;
  }

  /**
   * NB: this function should also do path compression
   * @param i index of a node
   * @return the root of the subtree containg i.
   */
   //returns root id of the set i is in using path compression
  int find(int i) {
    if (parent[i] != i) {
      parent[i] = find(parent[i]);
    }
    return parent[i];
  }

  // get the clusters by id
  Collection<List<Integer>> clusters() {
    Map<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < parent.length; i++) {
      int root = find(i);
      if (!map.containsKey(root)) map.put(root, new ArrayList<>());
      map.get(root).add(i);
    }
    return map.values();
  }

  //rank is the height of subtree whose root is i
  public int[] getRank() {
    return rank;
  }

  // Return the parent of the trees
  public int[] getParent() {
    return parent;
  }
}

