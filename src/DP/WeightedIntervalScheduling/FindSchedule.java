package DP.WeightedIntervalScheduling;

public class FindSchedule {

    // PREDECESSORS COMPUTED.
    // SORTED by ascending finishing time.
    // Compute max value of chosen jobs
    // O(n)

    public static int iterative(int n, int[] s, int[] f, int[] v, int[] p) {
        int[] mem = new int[n+1];
        mem[0] = 0; // base case

        for(int i = 1; i <= n; i++) {
            // if compatible.
            int predecessor = Math.max(0, p[i]);
            // using the formula
            mem[i] = Math.max(v[i] + mem[predecessor], mem[i - 1]);
        }

        //print the optimal schedule of chosen jobs8

        printScheduleIterative(n,s,f,v,p, mem);
        return mem[n];
    }

    public static void printScheduleIterative(int n, int[] s, int[] f, int[] v, int[] p, int[]mem) {
        int j = n;
        while (j > 0) {
            int pred = Math.max(p[j], 0);
            // job was chosen
            if (v[j] + mem[pred] > mem[j-1]){
                System.out.println(s[j] + " to " + f[j] + " with value: " + v[j]);
                j = pred;
            } else{
                j--;
            }
        }
    }

    // print schedule by back tracking using mem
    public static void printScheduleRecusive(int[] s, int[] f, int[] v, int[] p, int[]mem, int i) {
        if (i <= 0) return;

        int compatible = 0;
        if (p[i] != -1) compatible = mem[p[i]];
        if (v[i] + compatible >= mem[i-1]){
            System.out.println(s[i] + " to " + f[i] + " with value: " + v[i]);
            printScheduleRecusive(s, f, v, p, mem, p[i]);
        } else {
            printScheduleRecusive(s, f, v, p, mem, i - 1);
        }
    }

    public static void main(String[] args) {
        // Test 1: Expected 7. (8 11) (4,7) (1,4)
//        int[] s = { 0, 1, 3, 0 , 4, 3, 5, 6, 8};
//        int[] f = { 0, 4, 5, 6 , 7, 8,9,10,11};
//        int[] v = { 0, 2, 1, 1 , 3,4,1,1,2};
//        int[] p = { 0, -1, -1, -1, 1, -1, 2, 3, 5};
//        int n = 8;
//        iterative(n, s, f, v, p);

        // Test 2: Expected 10. (3,8) (0,3)
        int[] s = { 0, 0, 1, 3 };
        int[] f = { 0, 3, 4, 8 };
        int[] v = { 0, 3, 5, 7 };
        int[] p = { 0, -1, -1, 1 };
        int n = 3;
        iterative(n, s, f, v, p);
    }
}
