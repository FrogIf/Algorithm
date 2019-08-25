package sch.frog.leetcode;

public class ATOI {

    public static void main(String[] args){
        System.out.println(new Solution().myAtoi("-91283472332"));
//        System.out.println(new Solution().myAtoi("-6147483648"));
//        System.out.println(new Solution().myAtoi("6147483648"));
//        System.out.println(new Solution().myAtoi("             -42"));
//        System.out.println(new Solution().myAtoi("words and 987"));
//        System.out.println(new Solution().myAtoi("4193 with words"));
        System.out.println(Integer.MAX_VALUE + 123);
    }

    private static class Solution {
        public int myAtoi(String str) {
            if(str == null) return 0;
            char[] chars = str.toCharArray();

            int num = 0;
            boolean start = false;
            int fb = 1;
            for(int i = 0; i < chars.length; i++){
                char ch = chars[i];
                if(ch >= '0' && ch <= '9'){
                    start = true;

                    int n = ch - '0';

                    // overflow detect.
                    if(num > 0){
                        // num 如果使用long, 溢出检测就变得简单啊
                        if(fb == 1){
                            if(Integer.MAX_VALUE / 10 < num || (Integer.MAX_VALUE / 10 == num && Integer.MAX_VALUE % 10 <= n)){
                                return Integer.MAX_VALUE;
                            }
                        }else{
                            if(Integer.MIN_VALUE / -10 < num || (Integer.MIN_VALUE / -10 == num && Integer.MIN_VALUE % 10 >= -n)){
                                return Integer.MIN_VALUE;
                            }
                        }
                    }
                    num = num * 10 + n;
                }else if(!start){
                    if(ch == ' '){
                    }else if(ch == '-') {
                        fb = -1;
                        start = true;
                    }else if(ch == '+'){
                        start = true;
                    }else{
                        break;
                    }
                }else {
                    break;
                }

            }

            return num * fb;
        }
    }
}
