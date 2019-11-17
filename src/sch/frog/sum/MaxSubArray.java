package sch.frog.sum;

import java.util.Arrays;

/**
 * 最大子数组问题:
 * 给定一个由数字组成的数组, 该数组中必然一段连续区域的几个数字的和最大, 求该区域
 */
public class MaxSubArray {

    public static void main(String[] args){
        int[] arr = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println(Arrays.toString(recursionMaxSubArray(arr, 0, arr.length - 1)));
        System.out.println(Arrays.toString(fastMaxSubArray(arr)));
    }

    /**
     * 递归求取最大子数组
     * @param arr 待探查的数组
     * @param start 探查起始位置
     * @param end 探查终止位置
     * @return 返回探查结果, 一个长度为3的数组, 0 - 范围起始位置, 1 - 范围终止位置, 2 - 最大和
     */
    private static int[] recursionMaxSubArray(int[] arr, int start, int end){
        if(start == end){
            return new int[]{
                start, start, arr[start]
            };
        }else{
            int mid = start + ((end - start) >> 1);
            int[] l = recursionMaxSubArray(arr, start, mid);
            int[] r = recursionMaxSubArray(arr, mid + 1, end);
            int[] m = cross(arr, start, mid, end);
            if(l[2] > r[2] && l[2] > m[2]){
                return l;
            }else if(r[2] > l[2] && r[2] > m[2]){
                return r;
            }else{
                return m;
            }
        }
    }

    /**
     * 获取跨mid的最大子数组
     */
    private static int[] cross(int[] arr, int start, int mid, int end){
        int lMaxSum = arr[mid];
        int sum = lMaxSum;
        int s = mid;
        for(int i = mid - 1; i >= start; i--){
            sum += arr[i];
            if(sum > lMaxSum){
                lMaxSum = sum;
                s = i;
            }
        }


        int rMaxSum = sum = arr[mid + 1];
        int e = mid + 1;
        for(int i = mid + 2; i <= end; i++){
            sum += arr[i];
            if(sum > rMaxSum){
                rMaxSum = sum;
                e = i;
            }
        }

        return new int[]{
                s, e, lMaxSum + rMaxSum
        };
    }


    /**
     * 线性时间的最大子数组探查算法
     * @param arr 待探查数组
     * @return 探查结果
     */
    private static int[] fastMaxSubArray(int[] arr){
        int start = 0, end = 0;
        int max = arr[start];
        int borderMax = max;

        int bStart = start;
        for(int i = 1; i < arr.length; i++){
            int a = arr[i];
            if(a + borderMax >= a){
                borderMax = borderMax + a;
            }else{
                borderMax = a;
                bStart = i;
            }

            if(borderMax > max){
                max = borderMax;
                start = bStart;
                end = i;
            }
        }
        return new int[]{
                start, end, max
        };
    }
}
