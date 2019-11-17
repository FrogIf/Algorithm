package sch.frog.sum;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args){
        int[] arr = new int[]{2, 53, 12, 23, 56, 1, 3, 7, 23, 35, 132};
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
        int[] lArr = new int[mid - start + 1];
        int[] rArr = new int[end - mid];
        for(int i = 0; i < lArr.length; i++){
            lArr[i] = arr[start + i];
        }
        for (int i = 0; i < rArr.length; i++){
            rArr[i] = arr[mid + i + 1];
        }

        int i = 0;
        int j = 0;
        int k = start;
        while(k <= end){
            if(j == rArr.length || (i < lArr.length && lArr[i] < rArr[j])){
                arr[k++] = lArr[i++];
            }else{
                arr[k++] = rArr[j++];
            }
        }
    }
}
