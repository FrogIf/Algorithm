package sch.frog.leetcode;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberLetter {

    public static void main(String[] args){
        List<String> strings = new Solution().letterCombinations("23");
        for (String string : strings) {
            System.out.println(string);
        }
    }


    private static class Solution {
        public List<String> letterCombinations(String digits) {
            if(digits.length() == 0){
                return new ArrayList<>(0);
            }
            int[][] arr = new int[][]{
                    {'a', 'b', 'c'},
                    {'d', 'e', 'f'},
                    {'g', 'h', 'i'},
                    {'j', 'k', 'l'},
                    {'m', 'n', 'o'},
                    {'p', 'q', 'r', 's'},
                    {'t', 'u', 'v'},
                    {'w', 'x', 'y', 'z'}
            };

            char[] chars = digits.toCharArray();

            int total = 1;
            for (char aChar : chars) {
                total *= arr[aChar - '2'].length;
            }

            List<StringBuilder> builders = new ArrayList<>(total);
            int[] initConditions = arr[chars[0] - '2'];
            for (int initCondition : initConditions) {
                StringBuilder builder = new StringBuilder();
                builder.append((char) initCondition);
                builders.add(builder);
            }

            for(int i = 1; i < chars.length; i++){
                int[] conditions = arr[chars[i] - '2'];
                int range = builders.size();
                for(int k = 0; k < range; k++){
                    StringBuilder builder = builders.get(k);
                    for(int j = conditions.length - 1; j > 0; j--){
                        StringBuilder nb = new StringBuilder(builder);
                        nb.append((char) conditions[j]);
                        builders.add(nb);
                    }
                    builder.append((char) conditions[0]);
                }
            }

            List<String> result = new ArrayList<>(builders.size());
            for (StringBuilder builder : builders) {
                result.add(builder.toString());
            }

            return result;
        }
    }
}
