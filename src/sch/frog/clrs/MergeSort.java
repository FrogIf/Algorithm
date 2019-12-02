package sch.frog.clrs;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        RandomOrder.randomOrder(arr);
        System.out.println("排序前 : " + Arrays.toString(arr));
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("排序后 : " + Arrays.toString(arr));
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
        int[] arrL = new int[mid - start + 1];
        int[] arrR = new int[end - mid];
        for(int i = 0; i < arrL.length; i++){
            arrL[i] = arr[start + i];
        }
        for(int i = 0; i < arrR.length; i++){
            arrR[i] = arr[mid + i + 1];
        }

        int k = start;
        int i = 0, j = 0;
        while(k <= end){
            if(j >= arrR.length || (i < arrL.length && arrL[i] < arrR[j])){
                arr[k++] = arrL[i++];
            }else{
                arr[k++] = arrR[j++];
            }
        }
    }

}
