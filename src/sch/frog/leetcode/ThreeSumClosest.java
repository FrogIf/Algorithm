package sch.frog.leetcode;

public class ThreeSumClosest {

    public static void main(String[] args){
//        System.out.println(new Solution().threeSumClosest(new int[]{-1, 2, 1, -4, 0}, 1));
//        System.out.println(new Solution().threeSumClosest(new int[]{1, 1, 1, 0}, -100));
//        System.out.println(new Solution().threeSumClosest(new int[]{1, 2, 4, 8, 16, 32, 64, 128}, 82));
//        System.out.println(new Solution().threeSumClosest(new int[]{-3, -2, -5, 3, -4}, -1));
//        System.out.println(new Solution().threeSumClosest(new int[]{0, 2, 1, -3}, 1));
        System.out.println(new Solution().threeSumClosest(new int[]{-3, 0, 1, 2}, 1));
    }

    private static class Solution {
        public int threeSumClosest(int[] nums, int target) {
            if(nums.length == 3){
                return nums[0] + nums[1] + nums[2];
            }else{
                mergeSort(nums, 0, nums.length - 1);

                int sum = nums[0] + nums[1] + nums[2];
                int cf = abs(sum - target);
                for(int i = 0; i < nums.length - 2; i++){
                    for(int j = i + 1; j < nums.length - 1; j++){
                        for(int k = j + 1; k < nums.length; k++){
                            int nsum = nums[i] + nums[j] + nums[k];
                            int c = abs(nsum - target);
                            if(c < cf){
                                sum = nsum;
                                cf = c;
                            }else{
                                if(nsum - target > 0){
                                    break;
                                }
                            }
                        }
                    }
                }

                return sum;
            }
        }

        private int abs(int num){
            return num > 0 ? num : -num;
        }

        private void mergeSort(int[] nums, int start, int end){
            if(end - start <= 7){
                for(int i = start + 1; i <= end; i++){
                    int cursor = nums[i];
                    int j = i - 1;
                    for(; j >= start; j--){
                        if(nums[j] > cursor){
                            nums[j + 1] = nums[j];
                        }else{
                            break;
                        }
                    }
                    nums[j + 1] = cursor;
                }
            }else{
                int mid = start + (end - start) / 2;
                if(mid > start) mergeSort(nums, start, mid);
                if(mid + 1 < end) mergeSort(nums, mid + 1, end);
                merge(nums, start, mid, end);
            }
        }

        private void merge(int[] nums, int p, int q, int r){
            int[] arr1 = new int[q - p + 2];
            int[] arr2 = new int[r - q + 1];
            for(int i = 0; i < arr1.length - 1; i++){
                arr1[i] = nums[p + i];
            }
            for(int i = 0; i < arr2.length - 1; i++){
                arr2[i] = nums[q + i + 1];
            }

            int max = (arr1[arr1.length - 2] > arr2[arr2.length - 2] ? arr1[arr1.length - 2] : arr2[arr2.length - 2]) + 1;
            arr1[arr1.length - 1] = arr2[arr2.length - 1] = max;

            int m = 0;
            int n = 0;
            for(int i = p; i <= r; i++){
                if(arr1[m] > arr2[n]){
                    nums[i] = arr2[n];
                    n++;
                }else{
                    nums[i] = arr1[m];
                    m++;
                }
            }
        }

    }

}
