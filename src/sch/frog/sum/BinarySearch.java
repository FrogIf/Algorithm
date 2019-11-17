package sch.frog.sum;

/**
 * 二分法查找
 */
public class BinarySearch {

    public static void main(String[] args){
        int[] arr = new int[]{1, 4, 7, 9, 12, 14, 57, 34, 45};
        System.out.println(binarySearch(arr, 12));
    }

    /**
     * 二分法查找
     * @param arr 查找源, 该数组必须是从大到小排列
     * @param aim 查找目标
     * @return 找到的元素在数组中的位置
     */
    private static int binarySearch(int[] arr, int aim){
        int low = 0;
        int high = arr.length - 1;
        while(low <= high){
            int mid = low + ((high - low) >> 1);
            if(arr[mid] == aim){
                return mid;
            }else if(arr[mid] > aim){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }

        return -1;
    }

}
