package sch.frog.algorithm;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args){
        int[] arr = new int[]{23, 34, 1, 56, 23,6, 23, 356,8, 23, 23,5, 9, 2, 4};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int start, int end){
        int mid = start + (end - start) / 2;

        if(start < mid) mergeSort(arr, start, mid);
        if(mid + 1 < end) mergeSort(arr, mid + 1, end);

        mergeC(arr, start, mid, end);
    }

    /*
     * 巧妙的普通二路归并
     */
    private static void mergeC(int[] arr, int start, int mid, int end){
        int[] arrA = new int[mid - start + 1];
        int[] arrB = new int[end - mid];
        for(int i = 0; i < arrA.length; i++){
            arrA[i] = arr[start + i];
        }
        for(int i = 0; i < arrB.length; i++){
            arrB[i] = arr[mid + i + 1];
        }

        for(int i = start, m = 0, n = 0; i <= end; i++){
            if(n >= arrB.length || (m < arrA.length && arrA[m] < arrB[n])){
                arr[i] = arrA[m++];
            }else{
                arr[i] = arrB[n++];
            }
        }
    }

    /*
     * 普通二路归并
     */
    private static void mergeB(int[] arr, int start, int mid, int end){
        int[] arrA = new int[mid - start + 1];
        int[] arrB = new int[end - mid];
        for(int i = 0; i < arrA.length; i++){
            arrA[i] = arr[start + i];
        }
        for(int i = 0; i < arrB.length; i++){
            arrB[i] = arr[mid + i + 1];
        }

        int m = 0;
        int n = 0;
        int i = start;
        while(m < arrA.length && n < arrB.length){
            int a = arrA[m];
            int b = arrB[n];
            if(a < b){
                arr[i] = a;
                m++;
            }else{
                arr[i] = b;
                n++;
            }
            i++;
        }
        while (m < arrA.length){
            arr[i++] = arrA[m++];
        }
        while (n < arrB.length){
            arr[i++] = arrB[n++];
        }
    }

    /*
     * 使用末端哨兵的二路归并
     */
    private static void mergeA(int[] arr, int start, int mid, int end){
        int[] arrA = new int[mid - start + 2];
        int[] arrB = new int[end - mid + 1];
        for(int i = 0; i < arrA.length - 1; i++){
            arrA[i] = arr[start + i];
        }
        for(int i = 0; i < arrB.length - 1; i++){
            arrB[i] = arr[mid + i + 1];
        }
        arrA[arrA.length - 1] = arrB[arrB.length - 1] = Integer.MAX_VALUE;

        int m = 0;
        int n = 0;
        for(int i = start; i <= end; i++){
            int a = arrA[m];
            int b = arrB[n];
            if(a < b){
                arr[i] = a;
                m++;
            }else{
                arr[i] = b;
                n++;
            }
        }
    }

}
