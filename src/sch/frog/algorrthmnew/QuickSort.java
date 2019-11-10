package sch.frog.algorrthmnew;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args){
        int[] arr = new int[]{12, 3, 12, 65, 12, 765, 23, 56, 67, 2, 3645, 45, 4, 24, 78, 896, 4, 4, 37, 9, 54};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int start, int end){
        if(start < end){
            int tempStart = start;
            int tempEnd = end;
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

            quickSort(arr, tempStart, start - 1);
            quickSort(arr, start + 1, tempEnd);
        }
    }

}
