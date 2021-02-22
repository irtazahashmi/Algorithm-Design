package DP;

public class MaximumAverageGrade {

    // n assignments, h hours.
    // Maximise the sum of grades within h.
    // O(nh^2)

    public static int maxGrade(int n, int h, int[][] f) {
        int mem[][] = new int[n+1][h+1];

        for(int i = 0; i <= n; i++) {
            for(int j = 0; j <= h; j++) {
                // first row is 0. 0 assignments done -> 0 grade always
                if (i == 0) {
                    mem[i][j] = 0;
                    continue;
                }

                // sum up grades in the first col
                if (j == 0) {
                    mem[i][j] = f[i][j] + mem[i-1][j];
                    continue;
                }

                // make combinations to achieve max grade(e.g. n = 2, h = 2 -> max((0, 2), (1, 1), (2, 0))
                for(int k = 0; k <= j; k++)
                    mem[i][j] = Math.max(mem[i][j], f[i][k] + mem[i-1][j-k]);
            }
        }

        //print mem
        for(int i = 0; i <= n; i++) {
            for(int j = 0; j <= h; j++) {
                System.out.print(mem[i][j] + " ");
            }
            System.out.println();
        }

        return mem[n][h];
    }
}
