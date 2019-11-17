package sch.frog.sum;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    public static void main(String[] args){
        int[] arr = new int[]{12, 3, 56, 2, 435, 56, 23, 424, 556, 23, 4};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int start, int end){
        if(start < end){
            int tS = start;
            int tE = end;
            int pivot = arr[start];
            while(start < end){
                while(start < end && arr[end] > pivot){
                    end--;
                }
                if(start < end){
                    arr[start++] = arr[end];
                }
                while(start < end && arr[start] < pivot){
                    start++;
                }
                if(start < end){
                    arr[end--] = arr[start];
                }
            }
            arr[start] = pivot;
            quickSort(arr, tS, start - 1);
            quickSort(arr, start + 1, tE);
        }
    }
}
