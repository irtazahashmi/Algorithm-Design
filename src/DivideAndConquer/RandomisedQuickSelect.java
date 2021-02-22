package DivideAndConquer;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomisedQuickSelect {

    public static int randomisedQuickSelect(int[] arr, int k) {
        return kthSmallest(arr, 0, arr.length - 1, k);
    }

    // Takes an array and returns the kth smallest number in O(n).
    public static int kthSmallest(int[] arr, int start, int end, int k) {
        if (start == end) return arr[start]; // One element left
        Random random = new Random();
        int pivotIndex = start + random.nextInt(end - start + 1); //random pivotIndex
        int pivot = partition(arr, start, end, pivotIndex); // partition and get pivot index
        if (k == pivot) return arr[k];
        else if (k < pivot) return kthSmallest(arr, start, pivot - 1, k); // call with left arr
        else return kthSmallest(arr, pivot + 1, end, k); //call with right arr
    }

    public static int partition(int arr[], int start, int end, int pivotIndex) {
        // put pivot at the end o arr
        int lastIndex = end;
        swap(arr, pivotIndex, end);

        // Go through array from start till end - 1 (not the pivot)
        end--;
        while(start <= end){
            // Putting all elements less than pivot to left side of arr
            if(arr[start] < arr[lastIndex]) start++;
            else {
                swap(arr, start, end); //swap if bigger element found
                end--;
            }
        }

        // Moving pivot to right position in arr
        swap(arr, start, lastIndex);
        return start;
    }

    public static void swap(int[]arr, int a, int b) {
        if (a == b) return;
        else {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }
}
