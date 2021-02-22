package Greedy.KClusteringMaximumSpacing;

import java.util.*;

public class KClustering {

    // n = number of people, k = clusters required, r = distances between them, families = keep them together
    // return min distance between any two people from different clusters

    public static int buildingWalls(int n, int k, int[][] r, Set<Set<Integer>> families) {
        // Get the distances from i to j.
        List<Distance> distances = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            for(int j = i + 1 ; j <= n; j++) {
                // i - 1 and j - 1 because ids start with 0 for union rank!
                distances.add(new Distance(i - 1, j - 1, r[i][j]));
            }
        }

        // Sort distances in ascending order
        Collections.sort(distances, Comparator.comparingInt(d -> d.dist));

        UnionFind uf = new UnionFind(n);
        int index = 0;

        // Families stay together
        if (!families.isEmpty()) {
          for(Distance d : distances) {
            for(Set<Integer> family: families) {
              // +1 because we minused from id before.
              if (family.contains(d.idA + 1) && family.contains(d.idB + 1)) {
                if (uf.union(d.idA, d.idB)) index++;
              }
            }
            if (index == n - k) break;
          }
        }

        // make k clusters if not k already
        if (index != n - k) {
            for(Distance d : distances) {
                if (uf.union(d.idA, d.idB)) index++;
                if (index == n - k) break;
            }
        }

        // dont have k clusters, not possible.
        if(uf.clusters().size() < k) return 0;

        // give SHORTEST DISTANCE r between 2 people from different clusters
        // union does it because distance sorted ascending
        for(Distance d : distances) {
            if (uf.union(d.idA, d.idB)) return d.dist;
        }

        // no solution
        return 0;
    }


    static class Distance {
        int idA;
        int idB;
        int dist;

        public Distance(int a, int b, int d) {
            idA = a;
            idB = b;
            dist = d;
        }
    }

    // IDs start with 0!!!
    static class UnionFind {
        private int[] parent;
        private int[] rank;

        // Union Find structure implemented with two arrays for Union by Rank
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++)
                parent[i] = i;
        }

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

        //returns root id of the set i is in using path compression
        int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        //rank is the height of subtree whose root is i
        public int[] getRank() {
            return rank;
        }

        // Return the parent of the trees
        public int[] getParent() {
            return parent;
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
    }
}






