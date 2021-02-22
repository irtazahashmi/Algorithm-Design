package DP.SequenceAllignment;

public class Iterative {

    // O(nm) time and space (quadratic)
    // mismatch cost is 1, also gap cost = 1 (S = 1)

    public static int solve(String firstString, String secondString) {
        int m = firstString.length();
        int n = secondString.length();

        int[][] mem = new int[m+1][n+1];

        //base case
        for(int i = 0; i <= m; i++) mem[i][0] = 1*i;
        for(int j = 0; j <= n; j++) mem[0][j] = 1*j;

        for(int j = 1; j <= n; j++) {
            for(int i = 1; i <= m; i++) {
                //mismatch cost is 1
                int mismatchCost = (firstString.charAt(i-1) != secondString.charAt(j-1)) ? 1 : 0;
                //1) mismatched chars (align) 2) pay gap cost of xi unmatched 3) pay gap cost of yj unmatched
                mem[i][j] = Math.min(mismatchCost + mem[i-1][j-1], Math.min(1 + mem[i-1][j], 1 + mem[i][j-1]));
            }
        }

//        printEdits(mem, firstString, secondString);
        return mem[m][n];
    }

    public static void printEdits(int mem[][], String first, String second) {
        int i = mem.length - 1;
        int j = mem[0].length - 1;

        // + 1 because that's the cost!
        while(i > 0 && j > 0) {

            // the chars match, continue!
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                i--; j--;
            } else if (mem[i][j] == mem[i - 1][j - 1] + 1) { // edit (mismatch cost)
                //align x(1,i) to y(1,j)
                System.out.println("Edit " + second.charAt(j - 1) + " in the second string to "
                        + first.charAt(i-1) + " from the first string");
                i--; j--;
            } else if (mem[i][j] == mem[i - 1][j] + 1) { //delete (gap cost)
                // x(1,i-1) aligned with y(1, j) align x(i) with a gap in y.
                System.out.println("Delete " + first.charAt(i-1) + "  from the first string");
                i--;
            } else if (mem[i][j] == mem[i][j - 1] + 1) { // delete (gap cost)
                // x(1,i) aligned with y(1, j-1) align y(j) with a gap in x.
                System.out.println("Delete " + second.charAt(j-1) + " from the second string");
                j--;
            } else {
                throw new IllegalArgumentException("Something wrong with given data");
            }
        }

        while (i > 0) {
            System.out.println("Delete " + first.charAt(i-1) + "  from the first string");
            i--;
        }

        while (j > 0) {
            System.out.println("Delete " + second.charAt(j-1) + " from the second string");
            j--;
        }
    }

    public static void main(String[] args) {
        //test 1: 3
        String a = "kitten";
        String b = "sitting";
        System.out.println(solve(a, b));
//
//        //test 2: 4
//        String a2 = "GCCCTAGCG";
//        String b2 = "GCGCAATG";
//        System.out.println(solve(a2, b2));

//        //test 3: 2
//        String a3 = "AGGGCT";
//        String b3 = "AGGCA";
//        System.out.println(solve(a3, b3));

        //test 4: 2
//        System.out.println();
//        String a4 = "ocurrance";
//        String b4 = "occurrence";
//        System.out.println(solve(a4, b4));
    }
}
