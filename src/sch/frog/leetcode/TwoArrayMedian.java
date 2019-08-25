package sch.frog.leetcode;

public class TwoArrayMedian {

    public static void main(String[] args){
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{3, 4}, new int[]{1, 2}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{5}, new int[]{1, 2, 3, 4, 6, 7}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{}, new int[]{1}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2}, new int[]{-1, 3}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{2}, new int[]{1, 3, 4}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{3}, new int[]{1, 2, 4}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{4}, new int[]{1, 2, 3}));
        System.out.println("------------------------");
        System.out.println(new Solution().findMedianSortedArrays(new int[]{2, 3, 4, 5, 6, 7, 8, 9}, new int[]{1}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 3, 4, 5, 6, 7, 8, 9}, new int[]{2}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 4, 5, 6, 7, 8, 9}, new int[]{3}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 3, 5, 6, 7, 8, 9}, new int[]{4}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 3, 4, 6, 7, 8, 9}, new int[]{5}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 7, 8, 9}, new int[]{6}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6, 8, 9}, new int[]{7}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6, 7, 9}, new int[]{8}));
        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, new int[]{9}));
    }

    private static class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if(nums1.length == 0){
                return nums2.length % 2 == 1 ? nums2[nums2.length / 2] : (nums2[nums2.length / 2 - 1] / 2.0 + nums2[nums2.length / 2] / 2.0);
            }else if(nums2.length == 0){
                return nums1.length % 2 == 1 ? nums1[nums1.length / 2] : (nums1[nums1.length / 2 - 1] / 2.0 + nums1[nums1.length / 2] / 2.0);
            }

            int count = (nums1.length + nums2.length) / 2 + (nums1.length + nums2.length) % 2;

            int aCount = nums1.length / 2;
            if(aCount == 0){aCount = 1;}

            int aL, bL, aR, bR, aMid, bMid;

            int step = aCount;
            while(true){
                aMid = aCount - 1;
                bMid = count - aCount - 1;

                if(aMid >= nums1.length){
                    int index = bMid + count - nums1.length;
                    aL = nums2[index] > nums1[nums1.length - 1] ? nums2[index] : nums1[nums1.length - 1];
                    aR = nums2[index + 1];
                }else if(aMid >= 0){
                    aL = nums1[aMid];
                    aR = aMid + 1 >= nums1.length ? nums2[bMid + 1] : nums1[aMid + 1];
                }else{
                    int index = count - 1;
                    aL = nums2[index];
                    if(index + 1 >= nums2.length){aR = nums1[0];}
                    else{ aR = (nums2[index + 1] < nums1[0] ? nums2[index + 1] : nums1[0]);}
                }

                if(bMid >= nums2.length){
                    int index = aMid + count - nums2.length;
                    bL = nums1[index] > nums2[nums2.length - 1] ? nums1[index] : nums2[nums2.length - 1];
                    bR = nums1[index + 1];
                }else if(bMid >= 0){
                    bL = nums2[bMid];
                    bR = bMid + 1 >= nums2.length ? nums1[aMid + 1] : nums2[bMid + 1];
                }else{
                    int index = count - 1;
                    aL = bL = nums1[index];
                    if(index + 1 >= nums1.length){aR = bR = nums2[0];}
                    else{aR = bR = (nums1[index + 1] < nums2[0] ? nums1[index + 1] : nums2[0]);}
                }

                if(aL <= bR && bL <= aR){
                    if((nums1.length + nums2.length) % 2 == 1){
                        return aL > bL ? aL : bL;
                    }else{
                        return ((aL > bL ? aL : bL) + (aR < bR ? aR : bR)) / 2.0;
                    }
                }else{
                    step = (step > 1 ? step : 2) / 2;
                    if(aL > bR){
                        aCount -= step;
                    }else{
                        aCount += step;
                    }
                }
            }
        }

    }

}
