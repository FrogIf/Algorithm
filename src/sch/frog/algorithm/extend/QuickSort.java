package sch.frog.algorithm.extend;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args){
        int[] arr = {99, 45, 12, 36, 69, 22, 62, 796, 4, 696};
//        int[] arr = {12, 4, 7, 23, 90, 23, 34, 999, 34, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr){
        sort(arr, 0, arr.length - 1);
    }


    public static void sort(int[] arr, int start, int end){
        int low = start;
        int high = end;

        int pivot = arr[low];

        while(low < high){
            while(low < high && arr[high] > pivot){
                high--;
            }
            if(low < high){
                swap(arr, low, high);
                low++;
            }

            while(low < high && arr[low] < pivot){
                low++;
            }
            if(low < high){
                swap(arr, low, high);
                high--;
            }
        }
        int mid = low;

        if(mid - 1 > start) sort(arr, start, mid - 1);
        if(mid + 1 < end) sort(arr, mid + 1, end);
    }

    private static void swap(int[] arr, int a, int b){
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }


}
