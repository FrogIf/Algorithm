package sch.frog.algorithm;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args){
        int[] arr = new int[]{34, 22, 3, 56, 12, -1, 493, 2, 5, 904, 2};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertionSort(int[] arr){
        for(int i = 1; i < arr.length; i++){
            int key = arr[i];
            int j = i - 1;
            for(; j >= 0 && arr[j] < key; j--){
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;
        }
    }
}
