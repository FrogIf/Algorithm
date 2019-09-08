package sch.frog.algorithm.extend;

/**
 * 二分法查找
 */
public class BinarySearch {

    /*
     * 二分法查找时间复杂度计算:
     *
     * 已知数组中元素个数为n个, 二分法查找每次会将查找范围缩小一半:
     *  范围变化: n, n/2, n/4, ... , n/(2^x)
     *  也就是当执行x次时, 查找范围为n/(2^x)
     *
     *  当查找范围缩小为1时即可查找到目标, 即:
     *      n/(2^x) = 1
     *  所以
     *      x = log2(n)
     *  即时间复杂度为:
     *      O(log2(n))
     */


    /**
     * 经典二分法查找
     * @param value
     * @param arr
     * @return
     */
    public static int binarySearch(int value, int[] arr){
        int low = 0;
        int high = arr.length - 1;
        int mid;

        while(low <= high){
            mid = (low + high) / 2;

            if(arr[mid] == value){
                return mid;
            }else if(arr[mid] < value){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return -1;
    }

    public static int strongBinarySearch(int value, int[] arr){
        int low = 0;
        int high = arr.length - 1;
        int mid;

        while(low <= high){
            /*
             * 上面直接low + high, 在low和high很大的情况下, 有可能导致数据溢出
             * 那 为什么不用 low / 2 + high / 2呢?
             */
            mid = low + ((high - low) / 2);

            if(arr[mid] == value){
                return mid;
            }else if(arr[mid] < value){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return -1;
    }

    public static int zbBinarySearch(int value, int[] arr){
        int low = 0;
        int high = arr.length - 1;
        int mid;

        while(low <= high){
            /*
             * 防止数据溢出, 并且使用位运算
             */
            mid = low + ((high - low) >>> 1);

            if(arr[mid] == value){
                return mid;
            }else if(arr[mid] < value){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args){
        int[] arr = {1, 2, 4, 7, 8, 9, 10, 20, 45, 67};
        System.out.println(binarySearch(4, arr));
        System.out.println(strongBinarySearch(4, arr));
        System.out.println(zbBinarySearch(5, arr));
    }

}
