package sch.frog.clrs;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        RandomOrder.randomOrder(arr);
        System.out.println("排序前 : " + Arrays.toString(arr));
        insertionSort(arr);
        System.out.println("排序后 : " + Arrays.toString(arr));
    }

    private static void insertionSort(int[] arr){
        for(int i = 1; i < arr.length; i++){
            int a = arr[i];
            int j = i - 1;
            for(; j >= 0 && a < arr[j]; j--){
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = a;
        }
    }
}
