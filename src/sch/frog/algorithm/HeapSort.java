package sch.frog.algorithm;

import java.util.Arrays;

/**
 * 堆排序
 * 可以拆分为3个子动作:
 * 1. 局部堆化(maxHeapify) : T(n) = O(lgn)
 * 2. 构建最大堆(buildMaxHeapify) : T(n) = O(n)
 * 3. 堆排序(heapSort) : T(n) = O(nlgn)
 */
public class HeapSort {

    public static void main(String[] args){
        int[] a = new int[]{5, 3, 17, 10, 84, 19, 6, 22, 9};
        heapSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void heapSort(int[] arr){
        buildMaxHeapify(arr);
        for(int i = arr.length - 1; i >= 0; i--){
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            maxHeapify(arr, 0, i);
        }
    }

    /**
     * 构建最大堆
     * @param arr
     */
    private static void buildMaxHeapify(int[] arr){
        for(int i = arr.length >> 1; i >= 0; i--){
            maxHeapify(arr, i, arr.length);
        }
    }

    /**
     * 最大堆化
     * @param arr
     */
    private static void maxHeapify(int[] arr, int root, int end){
        int t = arr[root];

        int large = root;
        int l = (root << 1) + 1;
        int r = l + 1;

        while (r < end){
            if(arr[l] > t){
                large = l;
            }

            if(arr[r] > arr[large]){
                large = r;
            }

            if(large == root){ break; }

            arr[root] = arr[large];
            root = large;
            l = (root << 1) + 1;
            r = l + 1;
        }

        if(r == end && arr[l] > t){
            arr[root] = arr[l];
            root = l;
        }

        arr[root] = t;
    }

}
