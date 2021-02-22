package DivideAndConquer;

public class MedianOfSortedArrays {

    //You are given access to two databases, A and B, containing n ordered integers each.
    // The only operation you can perform is to query A[k] or B[k], which gives the kth smallest
    // value in A or B, respectively. Give pseudocode of an algorithm to determine the median
    // of all 2n values (i.e., the nth smallest value), using at most O(log n) queries.
    // Use the divide and conquer.

    public static double findMedianSortedArrays(int[] A, int[] B) {
        int l = (A.length + B.length + 1)/ 2;
        int r = (A.length + B.length + 2) / 2;
        return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
    }

    public static double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
        if (aStart > A.length - 1) return B[bStart + k - 1];
        if (bStart > B.length - 1) return A[aStart + k - 1];
        if (k == 1) return Math.min(A[aStart], B[bStart]);

        int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
        if (aStart + k/2 - 1 < A.length) aMid = A[aStart + k/2 - 1];
        if (bStart + k/2 - 1 < B.length) bMid = B[bStart + k/2 - 1];

        if (aMid < bMid)
            return getkth(A, aStart + k / 2, B, bStart, k - k / 2);
        else
            return getkth(A, aStart, B, bStart + k / 2, k - k / 2);
    }


    public static void main(String[] args) {
        int[] A = {0, 2, 4, 6, 8};
        int[] B = {1, 3, 5, 7, 9};
        System.out.println(findMedianSortedArrays(A, B));
    }
}
