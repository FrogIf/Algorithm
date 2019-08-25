package sch.frog.leetcode;

import java.util.List;

public class ThreeSum {

    public static void main(String[] args){
        show(new Solution().threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        show(new Solution().threeSum(new int[]{0, 0, 0, 0}));
    }

    public static void show(List<List<Integer>> lists){
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer + ",");
            }
            System.out.println();
        }
    }

    private static class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> result = new java.util.ArrayList<List<Integer>>();
            if(nums.length < 3) return result;
            quickSort(nums, 0, nums.length - 1);

            int start = nums[0] - 1;
            int base = start;
            for(int i = 0; i < nums.length - 2 && nums[i] <= 0; i++){
                if(base == nums[i]) {continue;}
                else {base = nums[i];}

                int subBase = start;
                for(int j = nums.length - 1; j > i && nums[j] >= 0; j--){
                    int value = 0 - nums[j] - nums[i];
                    if(subBase == value) {continue;}
                    else { subBase = value;}

                    if(binarySearch(value, i + 1, j - 1, nums)){
                        List<Integer> child = new java.util.ArrayList<Integer>();
                        child.add(nums[i]);
                        child.add(nums[j]);
                        child.add(value);
                        result.add(child);
                    }
                }
            }

            return result;
        }

        private boolean binarySearch(int value, int low, int high, int[] source){
            while(low <= high){
                int mid = low + (high - low) / 2;
                if(source[mid] == value){
                    return true;
                }else if(source[mid] > value){
                    high = mid - 1;
                }else{
                    low = mid + 1;
                }
            }
            return false;
        }

        private void quickSort(int[] nums, int low, int high){
            int tempL = low;
            int tempH = high;
            int pivot = nums[low];

            while(low < high){
                while(low < high && nums[high] > pivot){
                    high--;
                }
                if(high > low){
                    nums[low] = nums[high];
                    nums[high] = pivot;
                    low++;
                }

                while(low < high && nums[low] < pivot){
                    low++;
                }
                if(high > low){
                    nums[high] = nums[low];
                    nums[low] = pivot;
                    high--;
                }
            }

            if(tempL < low - 1){quickSort(nums, tempL, low - 1);}
            if(tempH > low + 1){quickSort(nums, low + 1, tempH);}
        }
    }
}
