package sch.frog.algorithm;

import java.util.Arrays;

/**
 * 最大子数组
 */
public class MaxSubArray {

    public static void main(String[] args){
        int[] arr = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        int[] result = new RecursionMaxSubArray().maxSubArray(arr);
        System.out.println(Arrays.toString(result));

        int[] result2 = new LineTimeSubArray().maxSubArray(arr);
        System.out.println(Arrays.toString(result2));
    }


    private static class LineTimeSubArray {

        /*
         * 时间复杂度:
         *  T(n) = θ(n)
         */
        private int[] maxSubArray(int[] array){
            int start = 0;
            int end = 0;
            int bStart = 0;
            int borderSum = array[0];
            int maxSum = array[0];

            for(int i = 1; i < array.length; i++){
                int bs = borderSum + array[i];
                if(bs >= array[i]){
                    borderSum = bs;
                }else{
                    borderSum = array[i];
                    bStart = i;
                }

                if(maxSum < borderSum){
                    start = bStart;
                    end = i;
                    maxSum = borderSum;
                }
            }

            return Arrays.copyOfRange(array, start, end + 1);
        }

    }

    private static class RecursionMaxSubArray{
        private int[] maxSubArray(int[] array){
            SubArrayInfo subArrayInfo = maxSubArray(array, 0, array.length - 1);
            return Arrays.copyOfRange(array, subArrayInfo.start, subArrayInfo.end + 1);
        }

        /*
         * 递归式:
         *   T(n) = 2T(n/2) + O(n) = θ(nlgn)    ; 主定理情况2
         */
        private SubArrayInfo maxSubArray(int[] array, int start, int end){
            if(start == end){
                SubArrayInfo subArrayInfo = new SubArrayInfo();
                subArrayInfo.end = subArrayInfo.start = start;
                subArrayInfo.sum = array[start];
                return subArrayInfo;
            }else{
                int mid = start + ((end - start) >> 1);
                SubArrayInfo l = maxSubArray(array, start, mid);
                SubArrayInfo r = maxSubArray(array, mid + 1, end);
                SubArrayInfo m = maxCrossArray(array, start, mid, end);
                if(l.sum > r.sum && l.sum > m.sum){
                    return l;
                }else if(r.sum > l.sum && r.sum > m.sum){
                    return r;
                }else{
                    return m;
                }
            }
        }

        private SubArrayInfo maxCrossArray(int[] array, int start, int mid, int end){
            int ls;
            int ml = mid;
            int sum = ls = array[mid];
            for(int i = mid - 1; i >= start; i--){
                sum += array[i];
                if(sum > ls){
                    ls = sum;
                    ml = i;
                }
            }

            int rl = mid + 1;
            int rs = sum = array[rl];
            for(int i = mid + 2; i <= end; i++){
                sum += array[i];
                if(sum > rs){
                    rs = sum;
                    rl = i;
                }
            }

            SubArrayInfo si = new SubArrayInfo();
            si.sum = ls + rs;
            si.start = ml;
            si.end = rl;
            return si;
        }

        private static class SubArrayInfo{
            private int start;
            private int end;
            private int sum;
        }
    }

}
