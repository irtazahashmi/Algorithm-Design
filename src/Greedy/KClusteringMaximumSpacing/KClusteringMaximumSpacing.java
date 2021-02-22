package Greedy.KClusteringMaximumSpacing;

import java.io.InputStream;
import java.util.*;

public class KClusteringMaximumSpacing {

    // n k
    // 10 3

    // x y
    // 4 5
    // 5 10
    // 7 7
    // 8 20
    // 11 6
    // 12 22
    // 22 15
    // 23 9
    // 25 12
    // 26 17


    public static String run(InputStream in) {
        return new KClusteringMaximumSpacing().solve(in);
    }

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        List<House> houses = new ArrayList<>(n);

        // add houses with id, x, y. ID starts from 0!!!
        for (int i = 0; i < n; i++) {
            houses.add(new House(i, sc.nextInt(), sc.nextInt()));
        }

        int m = n - 1; // number of edges in mst!!!
        List<Distance> distances = new ArrayList<>(m);

        // add distances. PAY ATTENTION TO j = i + 1
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances.add(new Distance(houses.get(i), houses.get(j)));
            }
        }

        // Sort to get shortest distance first
        Collections.sort(distances, Comparator.comparingLong(d -> d.distance));

        // Initially n initial clusters
        UnionFind uf = new UnionFind(houses);
        int index = 0;

        // Find closest pair of houses from different clusters and add edge between them
        for (Distance dist : distances) {
            if (uf.join(dist.a, dist.b)) index++;
            if (index == n - k) break; // Repeat n - k times
        }

        // get the clusters
        Collection<List<House>> clusters = uf.clusters();
        StringBuilder sb = new StringBuilder();

        for(List<House> cluster : clusters) {
            int x = 0;
            int y = 0;
            int totalHouses = 0;

            for (House house : cluster) {
                x += house.x;
                y += house.y;
                totalHouses++;
            }

            sb.append((double) x / totalHouses).append(" ").append((double) y / totalHouses).append("\n");
        }

        return sb.toString();
    }
}



class House {
    int id, x, y;

    public House(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

class Distance {
    House a, b;
    long distance;

    public Distance(House a, House b) {
        this.a = a;
        this.b = b;
        // Square Euclidean distance, to avoid floating-point errors
        this.distance = (long) (a.x - b.x) * (a.x - b.x) + (long) (a.y - b.y) * (a.y - b.y);
    }
}

class UnionFind {
    private List<House> houses;
    private int[] parent;
    private int[] rank;

    UnionFind(List<House> houses) {
        this.houses = houses;
        int n = houses.size();
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i; // starts from 0
    }

    /**
     * Joins two disjoint sets together, if they are not already joined.
     * @return false if x and y are in same set, true if the sets of x and y are now joined.
     */
    // Use House ID to join. Takes two House as argument
    boolean join(House x, House y) {
        int xrt = find(x.id);
        int yrt = find(y.id);
        if (rank[xrt] > rank[yrt])
            parent[yrt] = xrt;
        else if (rank[xrt] < rank[yrt])
            parent[xrt] = yrt;
        else if (xrt != yrt)
            rank[parent[yrt] = xrt]++;

        return xrt != yrt;
    }

    /**
     * @return The house that is indicated as the "root" of the set of house h.
     */
    House find(House h) {
        return houses.get(find(h.id));
    }

    private int find(int x) {
        return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }


    //return all clusters of houses in each separate list
    //Clusters size may be < k !!
    Collection<List<House>> clusters() {
        Map<Integer, List<House>> map = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            if (!map.containsKey(root)) map.put(root, new ArrayList<>());
            map.get(root).add(houses.get(i));
        }
        return map.values();
    }
}
