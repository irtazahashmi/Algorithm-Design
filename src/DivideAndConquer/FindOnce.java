package DivideAndConquer;

public class FindOnce {
    // in an sorted arr, find the element that occurs only once.
    // others occur more than once
    static int findOnce(int arr[]) {
        int start = 0;
        int end = arr.length - 1;

        // Apply binary search
        while(start < end) {
            int mid = (end + start)/2;

            if (mid % 2 == 0) {
                if (arr[mid] == arr[mid + 1]) start = mid + 2;
                else end = mid;
            } else {
                if (arr[mid] == arr[mid - 1]) start = mid + 1;
                else end = mid - 1;
            }
        }

        return arr[start];
    }

    public static void main(String[] args) {
        int[] arr = {0, 0, 2, 4, 4, 6, 6, 8, 8};
        System.out.println(findOnce(arr));
    }
}
