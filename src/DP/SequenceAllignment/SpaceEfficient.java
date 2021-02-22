package DP.SequenceAllignment;

public class SpaceEfficient {

    // O(m) space!
    // We only need left, left-below, and below to calculate i
    // mismatch cost is 1, also gap cost = 1 (S = 1)

    // cant recover solution with this

    public static int sequenceAlignmentLS(String firstString, String secondString) {
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

        return mem[m][1];
    }

    public static void main(String[] args) {
        //test 1: 3
        String a = "kitten";
        String b = "sitting";
        System.out.println(sequenceAlignmentLS(a, b));

        //test 2: 4
        String a2 = "GCCCTAGCG";
        String b2 = "GCGCAATG";
        System.out.println(sequenceAlignmentLS(a2, b2));

        //test 3: 2
        String a3 = "AGGGCT";
        String b3 = "AGGCA";
        System.out.println(sequenceAlignmentLS(a3, b3));
    }

}
