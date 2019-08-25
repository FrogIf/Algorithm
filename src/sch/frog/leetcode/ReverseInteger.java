package sch.frog.leetcode;

public class ReverseInteger {

    public static void main(String[] args){
        Solution s = new Solution();
//        System.out.println(s.reverse(23894));
//        System.out.println(s.reverse(-123));
//        System.out.println(s.reverse(1534236469));
        System.out.println(s.reverse(-2147483648));
        System.out.println(Integer.MAX_VALUE);
    }

    private static class Solution{
        public int reverse(int x) {
            if(x > -10 && x < 10) return x;

            if(Integer.MIN_VALUE == x) return 0;

            boolean negative = x < 0;
            if(negative){ x = -x; }

            int high = x % 10;
            x = x / 10;

            int fb = 1;


            int result = 0;
            while(x > 0){
                result = result * 10 + x % 10;
                x /= 10;
                fb *= 10;
            }

            int mh = Integer.MAX_VALUE / fb;
            boolean isOverflow = false;
            if(mh < 10){
                if(mh < high){
                    isOverflow = true;
                }else if(mh == high){
                    isOverflow = Integer.MAX_VALUE % fb < result;
                }
            }

            if(!isOverflow){
                result = fb * high + result;
            }else{
                return 0;
            }

            if(negative){ result = -result; }

            return result;
        }
    }

}
