package sch.frog.sum;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {

    public static void main(String[] args){
        int[] arr = new int[]{2, 53, 12, 23, 56, 1, 3, 7, 23, 35, 132};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void insertionSort(int[] arr){
        for(int i = 1; i < arr.length; i++){
            int j = i - 1;
            int a = arr[i];
            for(; j >= 0; j--){
                if(arr[j] <= a){
                    break;
                }
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = a;
        }
    }
}
