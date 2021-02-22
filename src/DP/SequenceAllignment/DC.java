package DP.SequenceAllignment;

import java.awt.desktop.SystemSleepEvent;

public class DC {

    // O(mn) time and O(m) space

    public static int DC(int m, int n, String x, String y) {
        if (m <= 2 || n <= 2) return Iterative.solve(x, y);

        int mid = n/2;
        // LS(x1..xm, y1...yn/2)
        int[] f = sequenceAlignmentLS(x.substring(0, m), y.substring(0, mid));

        // LS(xm...x1, yn/2+1...yn)
        int[] g = sequenceAlignmentLS(
                new StringBuilder(x.substring(0, m)).reverse().toString(),
                new StringBuilder(y.substring(mid)).reverse().toString());

        // find min index i
        int min = Integer.MAX_VALUE;
        int i = 0;
        for(int j = 0; j <= m; j++) {
            int v = f[j] + g[m - j];
            if (v < min) {
                min = v;
                i = j;
            }
        }

        // DC(x1...xi-1, y1...yn/2)
        // substring(inclusive index, exclusive index)
        DC(x.substring(0, i).length(), y.substring(0, mid).length(), x.substring(0, i), y.substring(0, mid));

        // from i to mid -> cost is min?
        System.out.println(i + " " + mid + " cost: " + min);

        // DC(xi+1...xm, yn/2 + 1...yn)
        DC(x.substring(i + 1, m).length(), y.substring(mid).length(), x.substring(i + 1, m), y.substring(mid));

        return min;
    }


    public static int[] sequenceAlignmentLS(String firstString, String secondString) {
        int m = firstString.length();
        int n = secondString.length();

        int[][] mem = new int[m+1][2];

        //base case, gap cost.
        for(int i = 0; i <= m; i++) mem[i][0] = 1*i;

        for(int j = 1; j <= n; j++) {
            mem[0][1] = j * 1;
            for(int i = 1; i <= m; i++) {
                int mismatchCost = (firstString.charAt(i-1) != secondString.charAt(j-1)) ? 1 : 0;
                mem[i][1] = Math.min(mismatchCost + mem[i-1][0], Math.min(1 + mem[i-1][1], 1 + mem[i][0]));
            }

            for(int i = 0; i <= m; i++) mem[i][0] = mem[i][1];
        }

        int[] res = new int[m + 1];
        for(int i = 0; i <= m; i++) res[i] = mem[i][1];
        return res;
    }

    public static void main(String[] args) {
        //test 1: 3
        String a = "kitten";
        String b = "sitting";
        System.out.println(DC(a.length(), b.length(), a, b));

        System.out.println();

        //test 2: 4
        String a2 = "GCCCTAGCG";
        String b2 = "GCGCAATG";
        System.out.println(DC(a2.length(), b2.length(), a2, b2));

        System.out.println();

        //test 3: 2
        String a3 = "AGGGCT";
        String b3 = "AGGCA";
        System.out.println(DC(a3.length(), b3.length(), a3, b3));

        System.out.println();

        //test 4: 2
        String a4 = "ocurrance";
        String b4 = "occurrence";
        System.out.println(DC(a4.length(), b4.length(), a4, b4));
    }


//    function Align-D&C(m,n,x1,x2,...,xm,y1,y2,...,yn)
//    if m ≤ 2 or n ≤ 2 then try all possible combinations.
//    else
//
//      h = 21 n
//      f = OPT(x1,x2,...,xm,y1,y2,...,yh)
//      g = OPT(xm,xm−1,...,x1,yn,yn−1,...,yh+1)
//      i = arg min0≤i≤m{f[i] + g[m − i]}
//      Align-D&C(i−1,h−1,x1,x2,...,xi−1,y1,y2,...,yh−1)
//      Print (i, h)
//      Align-D&C(m−i,h,xi+1,xi+2,...,xm,yh+1,yh+2,...,yn)
//    end if end function
}
