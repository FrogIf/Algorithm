package sch.frog.clrs;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        RandomOrder.randomOrder(arr);
        System.out.println("排序前 : " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后 : " + Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int start, int end){
        if(start < end){
            int ts = start;
            int te = end;
            int pivot = arr[start];
            while(start < end){
                while (start < end && arr[end] > pivot){
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
            quickSort(arr, ts, start - 1);
            quickSort(arr, start + 1, te);
        }
    }

}
