package DivideAndConquer;

import java.util.*;

public class AllPossiblePartialSums {
    /**
     * Computes all possible partial sums given an array of integers.
     *
     * @param arr - all values in the input set
     * @return set of sums
     */
    public static Set<Integer> partialSums(Integer[] arr) {
        if (arr == null) return null;
        // if arr is empty, add 0 and return
        if (arr.length == 0) {
            Set<Integer> s = new HashSet<>();
            s.add(0);
            return s;
        }
        Set<Integer> res = partialSums(arr, 0, arr.length - 1);
        res.add(0);
        return res;
    }

    public static Set<Integer> partialSums(Integer[] arr, int start, int end) {
        // one element left, add the element to set
        if (end - start == 0) {
            Set<Integer> s = new HashSet<>();
            s.add(arr[start]);
            return s;
        }

        int mid = (end + start)/2;
        Set<Integer> left = partialSums(arr, start, mid);
        Set<Integer> right = partialSums(arr, mid + 1, end);

        // merged + add all left + right
        Set<Integer> merged = new HashSet<>();
        merged.addAll(left);
        merged.addAll(right);

        // sum each el of left to each el of right
        for(int i : left) {
            for(int j : right) {
                merged.add(i + j);
            }
        }

        return merged;
    }
}

