package sch.frog.leetcode;

public class ZigZagConversion {

    public static void main(String[] args){
        System.out.println(new Solution().convert("ab", 2));
//        System.out.println(new Solution().convert("PAYPALISHIRING", 3).equals("PAHNAPLSIIGYIR"));
    }

    static class Solution {
        public String convert(String s, int numRows) {
            if(numRows == 1) return s;
            int period = 2 * numRows - 2;

            char[] chars = s.toCharArray();

            StringBuilder sb = new StringBuilder();
            for(int r = 0; r < numRows; r++){
                int i = 0;
                while (true){
                    int p1 = i - r;
                    if(p1 >= 0 && r != 0){
                        if(p1 < chars.length){
                            sb.append(chars[p1]);
                        }else{
                            break;
                        }
                    }

                    if(r != numRows - 1){
                        p1 = i + r;
                        if(p1 < chars.length){
                            sb.append(chars[p1]);
                        }else{
                            break;
                        }
                    }

                    i += period;
                }
            }

            return sb.toString();
        }

    }

}
