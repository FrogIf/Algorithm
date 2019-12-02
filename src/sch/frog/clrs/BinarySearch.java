package sch.frog.clrs;

/**
 * 二分法查找
 */
public class BinarySearch {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(binarySearch(arr, 2));
    }

    private static int binarySearch(int[] arr, int a){
        int low = 0;
        int high = arr.length - 1;
        while(low <= high){
            int mid = low + ((high - low) >> 1);
            int t = arr[mid];
            if(t == a){
                return mid;
            }else if(t > a){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return -1;
    }


}
