package DivideAndConquer;

import java.util.*;

class CountingInversions {

  static int countInversions(int[] array) {
    if (array == null || array.length <= 1) return 0;
    return sortAndCount(array, 0, array.length - 1);
  }

  static int sortAndCount(int[] arr, int start, int end) {
    if (end - start <= 0) return 0;
    int mid = (end + start) / 2;
    int inversions = 0;
    inversions += sortAndCount(arr, start, mid);
    inversions += sortAndCount(arr, mid + 1, end);
    inversions += mergeAndCount(arr, start, mid, end);
    return inversions;
  }

  static int mergeAndCount(int[] arr, int start, int mid, int end) {
    int i = start;
    int j = mid + 1;
    int temp[] = new int[end - start + 1];
    int k = 0;
    int inversions = 0;

    while(i <= mid && j <= end) {
      if (arr[i] <= arr[j]) temp[k++] = arr[i++];
      else {
        temp[k++] = arr[j++];
        // incrementing count by number of elements remaining in the left array
        inversions += (mid - i + 1);
      }
    }

    while(i <= mid) temp[k++] = arr[i++];
    while(j <= end) temp[k++] = arr[j++];

    for(int ind = start; ind <= end; ind++) arr[ind] = temp[ind - start];

    return inversions;
  }
}