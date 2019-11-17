package sch.frog.sum;

import java.util.Arrays;

/**
 * 计数排序
 */
public class CountingSort {

    public static void main(String[] args){
        int[] arr = new int[]{2, 53, 12, 23, 56, 1, 3, 7, 23, 35, 132};
        int[] res = countingSort(arr);
        System.out.println(Arrays.toString(res));
    }

    private static int[] countingSort(int[] arr){
//        int min = arr[0];
//        int max = arr[0];
//        for(int i = 1; i < arr.length; i++){
//            int a = arr[i];
//            if(a > max){
//                max = a;
//            }else if(a < min){
//                min = a;
//            }
//        }

        // 最大值, 最小值
        boolean isOdd = arr.length % 2 == 1;
        int max = arr[0];
        int min = isOdd ? max : arr[1];
        if(min < max){
            int t = max;
            max = min;
            min = t;
        }
        for(int i = isOdd ? 2 : 3; i < arr.length; i += 2){
            int tMax = arr[i];
            int tMin = arr[i - 1];
            if(tMax < tMin){
                int t = tMax;
                tMax = tMin;
                tMin = t;
            }
            if(tMax > max){ max = tMax; }
            if(tMin < min){ min = tMin;}
        }

        int[] count = new int[max - min + 1];
        for(int i = 0; i < arr.length; i++){
            count[arr[i] - min]++;
        }

        for(int i = 1; i < count.length; i++){
            count[i] = count[i] + count[i - 1];
        }

        int[] result = new int[arr.length];
        for(int i = result.length - 1; i >= 0; i--){
            result[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }
        return result;
    }


}
