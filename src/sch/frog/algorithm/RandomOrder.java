package sch.frog.algorithm;

import java.util.Arrays;
import java.util.Random;

public class RandomOrder {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        random(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void random(int[] arr){
        Random r = new Random();
        for(int i = 0; i < arr.length - 1; i++){
            int j = r.nextInt(arr.length - i - 1) + 1 + i;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

}
