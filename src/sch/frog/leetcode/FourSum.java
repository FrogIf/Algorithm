package sch.frog.leetcode;

import java.util.List;

public class FourSum {

    public static void main(String[] args){
        List<List<Integer>> list = new Solution().fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0);
        for (List<Integer> ns : list) {
            for (Integer n : ns) {
                System.out.print(n);
            }
            System.out.println();
        }
    }


    private static class Solution {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            quickSort(nums, 0, nums.length - 1);
            // -2, -1, 0, 0, 1, 2       (nums.length * nums.length) C(6,4) = 6 * 5 / 2 = 15
            int[] records = new int[nums.length * nums.length];
            for(int i = 0; i < nums.length; i++){
                records[i] = nums[i] + nums[0];
            }
            return null;
        }

        private void sum(int endIndex, int n){

        }

        private void quickSort(int[] nums, int low, int high){
            int tempL = low;
            int tempH = high;
            int pivot = nums[low];
            while(low < high){
                while (low < high && pivot < nums[high]){
                    high--;
                }
                if(low < high){
                    nums[low] = nums[high];
                    nums[high] = pivot;
                    low++;
                }

                while(low < high && pivot > nums[low]){
                    low++;
                }
                if(low < high){
                    nums[high] = nums[low];
                    nums[low] = pivot;
                    high--;
                }
            }

            if(low - 1 > tempL){ quickSort(nums, tempL, low - 1); }
            if(low + 1 < tempH){ quickSort(nums, low + 1, tempH); }
        }
    }
}
