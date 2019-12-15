package sch.frog.clrs;

import java.util.Arrays;

/**
 * 堆排序
 * 可以拆分为2个子动作:
 * 1. 局部堆化(maxHeapify) : T(n) = O(lgn)
 * 2. 构建最大堆(buildMaxHeap) : T(n) = O(n)
 * 合成-->
 *      堆排序(heapSort) : T(n) = O(nlgn)
 */
public class HeapSort {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        RandomOrder.randomOrder(arr);
        System.out.println("排序前 : " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println("排序后 : " + Arrays.toString(arr));
    }

    private static void heapSort(int[] arr){
        buildMaxHeap(arr);
        for(int i = arr.length - 1; i > 0; i--){
            int t = arr[i];
            arr[i] = arr[0];
            arr[0] = t;
            maxHeapify(arr, 0, i);
        }
    }

    // 将数组转换为最大堆
    private static void buildMaxHeap(int[] arr){
        for(int i = arr.length >> 1; i >= 0; i--){
            maxHeapify(arr, i, arr.length);
        }
    }

    /**
     * 最大堆化
     * @param arr 待最大堆化的数组
     * @param i 最大堆化位置, 该位置下面的元素都已经满足最大堆
     * @param heapSize 堆大小
     */
    private static void maxHeapify(int[] arr, int i, int heapSize){
        int l = (i << 1) + 1;
        int r = l + 1;
        int largest = i;
        if(l < heapSize && arr[l] > arr[i]){
            largest = l;
        }
        if(r < heapSize && arr[r] > arr[largest]){
            largest = r;
        }
        if(largest != i){
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            maxHeapify(arr, largest, heapSize);
        }
    }

}
