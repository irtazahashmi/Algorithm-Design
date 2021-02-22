package DP.SequenceAllignment;

public class Recursive {

    // Brute force

    public static int solve(String firstString, String secondString, int i, int j) {
        if (i == 0) return j*1;
        if (j == 0) return i*1;

        int mismatchCost = (firstString.charAt(i-1) != secondString.charAt(j-1)) ? 1 : 0;

        return Math.min(mismatchCost + solve(firstString, secondString, i-1, j-1),
                Math.min(1 + solve(firstString, secondString, i-1, j),
                        1 + solve(firstString, secondString, i, j-1)));
    }

    public static void main(String[] args) {
        //test 1: 3
        String a = "kitten";
        String b = "sitting";
        System.out.println(solve(a, b, a.length(), b.length()));

        //test 2: 4
        String a2 = "GCCCTAGCG";
        String b2 = "GCGCAATG";
        System.out.println(solve(a2, b2, a2.length(), b2.length()));

        //test 3: 2
        String a3 = "AGGGCT";
        String b3 = "AGGCA";
        System.out.println(solve(a3, b3, a3.length(), b3.length()));
    }
}
