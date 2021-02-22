package DivideAndConquer;

public class SearchSortedSequence {
    int solve(int n, int[] t, int[] p, int[] S, int[] I, int x) {
        int start = 1;
        int end = n;
        while(start < end) {
            int mid = (start + end)/2;
            if (x < S[mid]) end = mid - 1;
            else if (x > S[mid]) start = mid + 1;
            else return n + 1 - mid;
        }

        if (x <= S[start]) return n + 1 - start;
        else return n - start;
    }
}
