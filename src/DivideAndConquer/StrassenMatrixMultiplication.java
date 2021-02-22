package DivideAndConquer;

public class StrassenMatrixMultiplication {


    public static int[][] strassenMultiply(int[][] A, int[][] B) {
        int[][] multiplied = new int[A.length][A.length];

        // base case of 1x1 matrix
        if (A.length == 1) {
            multiplied[0][0] = A[0][0] * B[0][0];
            return multiplied;
        }

        int half = A.length / 2;
        // first 4x4 matrix
        int[][] a = new int[half][half];
        int[][] b = new int[half][half];
        int[][] c = new int[half][half];
        int[][] d = new int[half][half];

        // second 4x4 matrix
        int[][] e = new int[half][half];
        int[][] f = new int[half][half];
        int[][] g = new int[half][half];
        int[][] h = new int[half][half];

        //dividing matrix in .5n x .5n blocks
        // dividing matrix A into 4 parts
        divideCopy(A, a, 0, 0, "divide");
        divideCopy(A, b, 0, half, "divide");
        divideCopy(A, c, half, 0, "divide");
        divideCopy(A, d, half, half, "divide");

        // dividing matrix B into 4 parts
        divideCopy(B, e, 0, 0, "divide");
        divideCopy(B, f, 0, half, "divide");
        divideCopy(B, g, half, 0, "divide");
        divideCopy(B, h, half, half, "divide");

        //p1 = a(f - h)
        //p2 = (a + b)h
        //p3 = (c + d)e
        //p4 = d(g - e)
        //p5 = (a + d)(e + h)
        //p6 = (b - d)(g + h)
        //p7 - (a - c)(e + f)
        int p1[][] = strassenMultiply(a, addSub(f, h, "subtract"));
        int p2[][] = strassenMultiply(addSub(a, b, "add"), h);
        int p3[][] = strassenMultiply(addSub(c, d, "add"), e);
        int p4[][] = strassenMultiply(d, addSub(g, e, "subtract"));
        int p5[][] = strassenMultiply(addSub(a, d, "add"), addSub(e, h, "add"));
        int p6[][] = strassenMultiply(addSub(b, d, "subtract"), addSub(g, h, "add"));
        int p7[][] = strassenMultiply(addSub(a, c, "subtract"), addSub(e, f, "add"));

        //c11 = p5 + p4 - p2 + p6
        //c12 = p1+p2
        //c21 = p3+p4
        //c22 = p5 + p1 -p3 -p7
        int[][] C11 = addSub(addSub(addSub(p5, p4, "add"), p2, "subtract"), p6, "add");
        int[][] C12 = addSub(p1, p2, "add");
        int[][] C21 = addSub(p3, p4, "add");
        int[][] C22 = addSub(addSub(addSub(p5, p1, "add"), p3, "subtract"), p7, "subtract");

        divideCopy(C11, multiplied, 0, 0, "copy");
        divideCopy(C12, multiplied, 0, half, "copy");
        divideCopy(C21, multiplied, half, 0,  "copy");
        divideCopy(C22, multiplied, half, half,  "copy");
        return multiplied;
    }

    public static int[][] addSub(int[][] a, int[][] b, String operation) {
        int[][] res = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (operation.equals("add")) res[i][j] = a[i][j] + b[i][j];
                else res[i][j] = a[i][j] - b[i][j];
            }
        }
        return res;
    }

    public static void divideCopy(int[][] a, int[][] b, int start, int end, String operation) {
        int n;
        if (operation.equals("divide")) n = b.length;
        else n = a.length;
        int s = start;
        for(int i = 0; i < n; i++) {
            int e = end;
            for (int j = 0; j < n; j++) {
                if (operation.equals("divide")) b[i][j] = a[s][e];
                else b[s][e] = a[i][j];
                e++;
            }
            s++;
        }
    }

    public static void printMatrix(int[][] a) {
        int n = a.length;
        System.out.println("Resultant Matrix ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] A = {{1, 4, 3, 4},{1, 9, 3, 4},{1, 2, 7, 5},{5, 2, 3, 4}};
        int[][] B = {{3, 3, 2, 6},{4, 6, 2, 3},{6, 2, 5, 3},{7, 2, 7, 4}};
        printMatrix(strassenMultiply(A, B));
    }
}
