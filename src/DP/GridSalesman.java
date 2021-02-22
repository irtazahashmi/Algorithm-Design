package DP;

import java.util.Arrays;

public class GridSalesman {

    //P  Q
    //80 90
    //30 60
    //30 60
    //70 50
    //80 20
    // Takes one day to switch location. Max profit?
    public static int fishSalesman(int n, int[] P, int[] Q) {
        int[][] mem = new int[2][n + 1];
        mem[0][0] = 0;
        mem[1][0] = 0;

        mem[0][1] = P[1];
        mem[1][1] = Q[1];

        for(int i = 2; i<= n; i++) {
            // left: profit + max(prev left, prev prev right) -> takes a day to move to right
            mem[0][i] = P[i] + Math.max(mem[0][i-1], mem[1][i-2]);

            // right: profit + max(prev right, prev prev left) -> takes a day to move to left
            mem[1][i] = Q[i] + Math.max(mem[1][i-1], mem[0][i-2]);
        }

        return Math.max(mem[0][n], mem[1][n]);
    }

    // High stress, low stress job
    // before high stress job, can't work i-1 before that
    // max value?
    public static int highLowStress(int n, int L[], int H[]) {
        int mem[][] = new int[2][n+1];
        mem[0][0] = 0;
        mem[1][0] = 0;

        mem[0][1] = L[1];
        mem[1][1] = H[1];

        for(int i = 2; i <= n; i++) {
            // take profit + max(prev low, prev high) given that I CAN do job at i - 1
            mem[0][i] = L[i] + Math.max(mem[0][i-1], mem[1][i-1]);

            // take profit + max(prev prev low, prev prev high) ->  NO job can be done at i-1
            mem[1][i] = H[i] + Math.min(mem[0][i-2], mem[1][i-2]);
        }

        return Math.max(mem[0][n], mem[1][n]);
    }


    // Two production lines, L & R, both with n machines
    // For every job all machines 1<=i<=n should be used in that order.
    //The time for every machine i on the left side is given by li and on
    //the right side by ri.
    //For an EMERGENCY job BOTH lines can be USED.
    //It is possible to use machine i from the left and machine i+1 from the right,
    //but changing will cost x extra time.
    //Minimize time

    public static int machineTime(int n, int L[], int R[], int x) {
        int mem[][] = new int[2][n+1];
        mem[0][0] = 0;
        mem[1][0] = 0;

        mem[0][1] = L[1];
        mem[1][1] = R[1];

        for(int i = 2; i <= n; i++) {
            // time + min(prev left, prev right + x more time)
            mem[0][i] = L[i] + Math.min(mem[0][i-1], x + mem[1][i-1]);

            // time + min(prev right, prev left + x more time)
            mem[1][i] = R[i] + Math.min(x + mem[0][i-1], mem[1][i-1]);
        }

        return Math.min(mem[0][n], mem[1][n]);
    }



    // Supply of each week s1...sn
    // use A to ship, r * s1 cost
    // use B to ship, but gotta use for 4 weeks CONSECUTIVELY
    // minimize cost
    public static int oneWeekFourWeek(int n, int[] supply, int r, int C) {
        int[] mem = new int[n+1];
        mem[0] = 0;

        for(int i = 1; i <= n; i++) {
            int fourWeeks = 0;
            if (i - 4 >= 0) fourWeeks = mem[i-4];
            mem[i] = Math.min(r * supply[i] + mem[i-1], 4 * C + fourWeeks);
        }

        return mem[n];
    }


    public static void main(String[] args) {
        // 300
        int n = 5;
        int[] P = { 0, 80, 30, 30, 70, 80 };
        int[] Q = { 0, 90, 60, 60, 50, 20 };
        System.out.println(fishSalesman(n, P, Q));
    }
}
