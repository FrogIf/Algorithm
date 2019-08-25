package sch.frog.leetcode;

public class LongestPalindromicSubstring {

    public static void main(String[] args){
        char[] ch = new char[10];
        for(int i = 0; i < ch.length; i++){
            System.out.println((ch[i] = (char) -1));
        }
        System.out.println(new Solution().longestPalindrome("aaaaaaaaaaaaaaa"));  // ababa
        System.out.println(new Solution().longestPalindrome("babad"));  // bab
        System.out.println(new Solution().longestPalindrome("cbbd"));   // bb
    }

    private static class Solution {
        public String longestPalindrome(String s) {
            if(s == null || s.length() == 0) return s;

            char[] nChs = new char[2 * s.length() + 1];
            int[] radius = new int[nChs.length];

            char[] oChs = s.toCharArray();
            for(int i = 0; i < oChs.length; i++){
                nChs[2 * i] = oChs[i];
                if(i != 0) nChs[2 * i - 1] = (char) -1;
            }

            int maxLen = 0;
            for(int i = 0; i < nChs.length; i++){
                int ml = maxLen(i, nChs);
                radius[i] = ml / 2;


            }

            return null;
        }

        private int maxLen(int center, char[] source){
            int l = center, r = center;
            while(l >= 0 && r <= source.length){
                if(source[l] != source[r]){
                    break;
                }
                l--;
                r++;
            }
            return r - l;
        }
    }

}
