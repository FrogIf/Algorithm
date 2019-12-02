package sch.frog.clrs;

import java.util.Arrays;

/**
 * 最大子数组<br/>
 * 给定一个由数字组成的数组, 该数组中必然一段连续区域的几个数字的和最大, 求该区域
 */
public class MaxSubArray {

    public static void main(String[] args){
        int[] arr = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(Arrays.toString(maxSubArrayRecursion(arr, 0, arr.length - 1)));
        System.out.println(Arrays.toString(maxSubArray(arr)));
    }

    private static int[] maxSubArray(int[] arr){
        int start = 0, end = 0;
        int maxS = arr[start];
        int borderSum = maxS;
        int bStart = start;
        for(int i = 1; i < arr.length; i++){
            int s = borderSum + arr[i];
            if(s >= arr[i]){
                borderSum = s;
            }else{
                borderSum = arr[i];
                bStart = i;
            }

            if(borderSum > maxS){
                maxS = borderSum;
                start = bStart;
                end = i;
            }
        }

        return new int[]{
                maxS, start, end
        };
    }

    /**
     * 最大子数组
     * @return 返回数组中 0 - 元素和, 1 - 范围起点, 2 - 范围终点
     */
    private static int[] maxSubArrayRecursion(int[] arr, int start, int end){
        if(start == end){
            return new int[]{arr[start], start, start};
        }else{
            int mid = start + ((end - start) >> 1);
            int[] l = maxSubArrayRecursion(arr, start, mid);
            int[] r = maxSubArrayRecursion(arr, mid + 1, end);
            int[] c = crossMaxSubArray(arr, start, mid, end);
            if(l[0] > r[0] && l[0] > c[0]){
                return l;
            }else if(r[0] > l[0] && r[0] > c[0]){
                return r;
            }else{
                return c;
            }
        }
    }

    private static int[] crossMaxSubArray(int[] arr, int start, int mid, int end){
        int li = mid;
        int lm = 0;
        int sum = 0;
        for(int i = mid; i > start; i--){
            sum += arr[i];
            if(sum > lm){
                lm = sum;
                li = i;
            }
        }

        int ri = mid + 1;
        int rm = 0;
        sum = 0;
        for(int i = mid + 1; i < end; i++){
            sum += arr[i];
            if(sum > rm){
                rm = sum;
                ri = i;
            }
        }

        return new int[]{lm + rm, li, ri};
    }

}
