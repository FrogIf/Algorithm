package sch.frog.algorrthmnew;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = new int[]{12, 3, 12, 65, 12, 765, 23, 56, 67, 2, 3645, 45, 4, 24, 78, 896, 4, 4, 37, 9, 54};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int j = i + 1;
            int a = arr[j];
            while (j > 0 && arr[j - 1] > a) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = a;
        }
    }

}
