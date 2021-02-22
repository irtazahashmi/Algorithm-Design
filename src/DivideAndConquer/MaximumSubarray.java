package DivideAndConquer;

public class MaximumSubarray {

    public static int largestSum(int[] arr) {
        if(arr == null || arr.length == 0) return 0;
        return largestSum(arr, 0, arr.length - 1);
    }

    public static int largestSum(int[] arr, int start, int end) {
        if (end - start == 0) return arr[start];

        int mid = (end + start)/2;
        int left = largestSum(arr, start, mid); //largest sum in left subarray
        int right = largestSum(arr, mid + 1, end);  //largest sum in right subarray
        int leftRightMax = largestSumLR(arr, start, mid, end); //largest sum in crossing subarrays
        return Math.max(leftRightMax, Math.max(left, right)); //return the max of 3
    }

    public static int largestSumLR(int[] arr, int start, int mid, int end) {
        //max sum in left array
        int leftMax = Integer.MIN_VALUE;
        int sum = 0;
        for(int i = mid; i >= start; i--) {
            sum += arr[i];
            leftMax = Math.max(sum, leftMax);
        }

        // max sum in right subarry
        int rightMax = Integer.MIN_VALUE;
        sum = 0;
        for(int i = mid + 1; i <= end; i++) {
            sum += arr[i];
            rightMax = Math.max(sum, rightMax);
        }

        //return max of the three
        return Math.max(leftMax + rightMax, Math.max(leftMax, rightMax));
    }
}
