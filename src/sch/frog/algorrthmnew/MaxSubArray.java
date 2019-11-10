package sch.frog.algorrthmnew;

import java.util.Arrays;

public class MaxSubArray {

    public static void main(String[] args){
        int[] arr = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        int[] info = recursionMaxSubArray(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(info));

        System.out.println(Arrays.toString(fasterMaxSubArray(arr)));
    }

    private static int[] recursionMaxSubArray(int[] arr, int start, int end){
        if(start == end){
            return new int[]{
                    arr[start], start, end
            };
        }else{
            int mid = start + ((end - start) >> 1);
            int[] l = recursionMaxSubArray(arr, start, mid);
            int[] r = recursionMaxSubArray(arr, mid + 1, end);
            int[] c = maxCrossSubArray(arr, start, mid, end);
            if(l[0] > r[0] && l[0] > c[0]){
                return l;
            }else if(r[0] > l[0] && r[0] > c[0]){
                return r;
            }else{
                return c;
            }
        }
    }

    private static int[] maxCrossSubArray(int[] arr, int start, int mid, int end){
        int lp = mid;
        int sum = arr[lp];
        int maxL = sum;
        for(int i = mid - 1; i >= start; i--){
            sum += arr[i];
            if(sum > maxL){
                maxL = sum;
                lp = i;
            }
        }

        int rp = mid + 1;
        sum = arr[rp];
        int maxR = sum;
        for(int i = rp + 1; i <= end; i++){
            sum += arr[i];
            if(sum > maxR){
                maxR = sum;
                rp = i;
            }
        }

        return new int[]{
                maxL + maxR,
                lp,
                rp
        };
    }

    private static int[] fasterMaxSubArray(int[] arr){
        int start = 0;
        int end = start;
        int max = arr[0];
        int borderSum = max;
        int bStart = start;
        for(int i = 1; i < arr.length; i++){
            int bs = borderSum + arr[i];
            if(bs >= arr[i]){
                borderSum = bs;
            }else{
                borderSum = arr[i];
                bStart = i;
            }

            if(borderSum > max){
                max = borderSum;
                start = bStart;
                end = i;
            }
        }

        return new int[]{
                max,
                start,
                end
        };
    }

}
