package sch.frog.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 基数排序
 */
public class RadixSort {

    public static void main(String[] args){
        Random r = new Random();
        int[] arr = new int[100];
        for(int i = 0; i < arr.length; i++){
            arr[i] = r.nextInt(900) + 100;
        }
        System.out.println(Arrays.toString(arr));
        arr = radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static int[] radixSort(int[] arr){
        int step = 1;

        int len = 0;
        int num = arr[0];
        while(num != 0){
            len++;
            num /= 10;
        }

        for(int i = 0; i < len; i++){
            arr = countingSort(arr, step);
            step *= 10;
        }

        return arr;
    }

    private static int[] countingSort(int[] arr, int r){
        int[] cArr = new int[10];
        for(int i = 0; i < arr.length; i++){
            int index = arr[i] / r % 10;
            cArr[index]++;
        }

        for(int i = 1; i < cArr.length; i++){
            cArr[i] = cArr[i - 1] + cArr[i];
        }

        int[] result = new int[arr.length];
        for(int i = arr.length - 1; i >= 0; i--){
            int index = arr[i] / r % 10;
            int pos = cArr[index] - 1;
            result[pos] = arr[i];
            cArr[index]--;
        }

        return result;
    }

}
