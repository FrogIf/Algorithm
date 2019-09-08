package sch.frog.algorithm;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args){
        int[] arr = new int[]{2, 5, 23, 45, 2, 65, 2, 43, 9, 8, 23, 4, 5, 8, 9};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void insertionSort(int[] arr){
        /*
         * 时间复杂度:
         *      T(n) = O(n^2)
         *      T(n) = Ω(n)    ; 当已经有序的时候
         * 循环不变式:
         *      外层循环每次循环开始前, 0 - i-1 都是已经排好序的了
         * 特点:
         *      in place sort
         */
        for(int i = 1; i < arr.length; i++){
            int val = arr[i];
            int j = i - 1;
            for(; j >= 0 && arr[j] > val; j--){
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = val;
        }
    }


}
