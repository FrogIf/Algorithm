package sch.frog.clrs;

import java.util.Arrays;

/**
 * 计数排序
 */
public class CountingSort {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        RandomOrder.randomOrder(arr);
        System.out.println("排序前 : " + Arrays.toString(arr));
        arr = countingSort(arr);
        System.out.println("排序后 : " + Arrays.toString(arr));
    }

    private static int[] countingSort(int[] arr){
        // 先获取到该数组的上下界
        int max = arr[0];
        int min = max;
        for(int i = 1; i < arr.length; i++){
            int t = arr[i];
            if(t > max){
                max = t;
            }else if(t < min){
                min = t;
            }
        }

        int[] count = new int[max - min + 1];
        for(int i = 0; i < arr.length; i++){
            count[arr[i] - min]++;
        }

        for(int i = 1; i < count.length; i++){
            count[i] = count[i] + count[i - 1];
        }

        int[] result = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            int c = count[arr[i] - min];
            result[c - 1] = arr[i];
            count[arr[i] - min] = c - 1;
        }
        return result;
    }

}
