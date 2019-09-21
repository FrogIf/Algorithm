package sch.frog.algorithm;

import java.util.Arrays;

/**
 * 基数排序
 */
public class CountingSort {

    public static void main(String[] args){
        int[] arr = new int[]{23, 34, 1, 56, 23, 6, 23, 356, 8, 23, 23, 5, 9, 2, 4};
        int[] res = countingSort(arr);
        System.out.println(Arrays.toString(res));
    }


    private static int[] countingSort(int[] arr){
        int max = arr[0];
        int min = max;
        for(int i = 1; i < arr.length; i++){
            int t = arr[i];
            if(t > max){ max = t; }
            if(t < min){ min = t;}
        }

        int[] cArr = new int[max - min + 1];
        for(int i = 0; i < arr.length; i++){
            cArr[arr[i] - min] = cArr[arr[i] - min] + 1;
        }

        for(int i = 1; i < cArr.length; i++){
            cArr[i] = cArr[i] + cArr[i - 1];
        }

        int[] result = new int[arr.length];
        /*
         * 这里单从排序结果上看, 与for(int i = 0; i < arr.length; i++)结果相同
         * 但是从后向前会保证排序是"稳定的"
         * "稳定的"在算法导论中的定义:
         *     如果两个对象A, B相等, 排序前, 排列顺序是A在前, B在后
         *     如果排序后, 这两个对象的顺序依旧是A在前, B在后, 那么说这个算法是稳定的
         *
         * 从前向后遍历的效果是: 颠倒
         */
        for(int i = arr.length - 1; i >= 0; i--){
            result[cArr[arr[i] - min] - 1] = arr[i];
            cArr[arr[i] - min]--;
        }

        return result;
    }
}
