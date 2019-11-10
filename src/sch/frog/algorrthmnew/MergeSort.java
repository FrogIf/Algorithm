package sch.frog.algorrthmnew;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args){
        int[] arr = new int[]{12, 3, 12, 65, 12, 765, 23, 56, 67, 2, 3645, 45, 4, 24, 78, 896, 4, 4, 37, 9, 54};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }


    private static void mergeSort(int[] arr, int start, int end){
        if(start < end){
            int mid = start + ((end - start) >> 1);
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }
    }

    private static void merge(int[] arr, int start, int mid, int end){
        int[] left = new int[mid - start + 1];
        int[] right = new int[end - mid];

        for(int i = 0; i < left.length; i++){
            left[i] = arr[start + i];
        }

        for(int j = 0; j < right.length; j++){
            right[j] = arr[mid + j + 1];
        }

        int i = 0;
        int j = 0;
        int k = start;
        while(k <= end){
            if(j == right.length || (i != left.length && left[i] < right[j])){
                arr[k++] = left[i++];
            }else{
                arr[k++] = right[j++];
            }
        }

    }

}
