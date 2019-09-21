package sch.frog.algorithm;

import java.util.Arrays;

/**
 * 快速排序:
 *  平均情况下 : T(n) = θ(nlgn)
 *  最差情况下 : T(n) = θ(n^2)
 */
public class QuickSort {

    public static void main(String[] args){
        int[] arr = new int[]{23, 34, 1, 56, 23, 6, 23, 356, 8, 23, 23, 5, 9, 2, 4};
        quickSortBest(arr, 0, arr.length - 1);
//        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSortBest(int[] arr, int start, int end){
        if(start < end){
            int pivot = arr[start];
            int tempStart = start;
            int tempEnd = end;
            while(start < end){
                while(start < end && arr[end] > pivot){
                    end--;
                }
                if(start < end){
                    arr[start] = arr[end];
                    start++;
                }
                while(start < end && arr[start] < pivot){
                    start++;
                }
                if(start < end){
                    arr[end] = arr[start];
                    end--;
                }
            }
            arr[start] = pivot;
            quickSort(arr, tempStart, start - 1);
            quickSort(arr, start + 1, tempEnd);
        }
    }

    public static void quickSort(int[] arr, int start, int end/*包含end*/){
        if(start < end){
            // 拆分
            int pivot = arr[end];
            int j = start - 1;
            for(int i = start; i < end; i++){
                int t = arr[i];
                if(t < pivot){
                    arr[i] = arr[++j];
                    arr[j] = t;
                }
            }
            arr[end] = arr[++j];
            arr[j] = pivot;

            // 分治
            quickSort(arr, start, j - 1);
            quickSort(arr, j + 1, end);
        }
    }

}
