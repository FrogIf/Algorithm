package sch.frog.algorrthmnew;

import java.util.Arrays;
import java.util.Random;

public class RandomOrder {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        randomOrder(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void randomOrder(int[] arr){
        Random random = new Random();
        for(int i = 0; i < arr.length; i++){
            int k = random.nextInt(arr.length - i) + i;
            int temp = arr[k];
            arr[k] = arr[i];
            arr[i] = temp;
        }
    }

}
