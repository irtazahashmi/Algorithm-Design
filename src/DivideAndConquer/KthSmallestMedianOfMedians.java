package DivideAndConquer;

import java.util.*;

public class KthSmallestMedianOfMedians {

  // Takes an array and returns the ith smallest number in O(n).
  public static int findIth(int[] arr, int index) {
    // If elements less than five, sort and return ith smallest
    if (arr.length < 5) {
      Arrays.sort(arr);
      return arr[index];
    }
    
    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Double> medians = new ArrayList<>();
    
    int elements = 0;
    //divide elements into 5 elements each and find medians
    for(int i = 0; i < arr.length; i++) {
      list.add(arr[i]);
      elements++;
      
      if (i + 1 == arr.length) {
        Collections.sort(list);
        medians.add(findMedian(list));
        break;
      }
      
      if (elements == 5) {
        elements = 0;
        Collections.sort(list);
        medians.add(findMedian(list));
        list.clear();
      }
    }
    
    //find median of medians
    double median = findMedianDouble(medians);
    
    //divide array into low and high
    ArrayList<Integer> low = new ArrayList<>();
    ArrayList<Integer> high = new ArrayList<>();
    for(int i = 0; i < arr.length; i++) {
      if (arr[i] > median) high.add(arr[i]);
      else if (arr[i] <= median) low.add(arr[i]);
    }
    
    //do comparison with k
    int k = low.size() - 1;
    if (index < k) return findIth(low.stream().mapToInt(i -> i).toArray(), index);
    if (index > k) return findIth(high.stream().mapToInt(i -> i).toArray(), index - k - 1);
    else return (int) median;
  }
  
  public static double findMedian(ArrayList<Integer> list) {
    int mid = list.size() / 2;
    if (list.size() % 2 == 0) return (list.get(mid - 1) + list.get(mid)) / 2;
    else return list.get((int) Math.floor(mid));
  }
  
  public static double findMedianDouble(ArrayList<Double> list) {
    int mid = list.size() / 2;
    if (list.size() % 2 == 0) return (list.get(mid - 1) + list.get(mid)) / 2;
    else return list.get((int) Math.floor(mid));
  }
}
