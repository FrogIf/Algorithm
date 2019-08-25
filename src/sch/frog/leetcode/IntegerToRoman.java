package sch.frog.leetcode;

public class IntegerToRoman {

    public static void main(String[] args){
        System.out.println(new Solution().intToRoman(101));
    }

    static class Solution {

        private final java.util.Map<Integer, String> roman = new java.util.HashMap<Integer, String>();

        {
            roman.put(1, "I");
            roman.put(5, "V");
            roman.put(10, "X");
            roman.put(50, "L");
            roman.put(100, "C");
            roman.put(500, "D");
            roman.put(1000, "M");
        }

        public String intToRoman(int num) {
            int temp = num / 10;
            int fb = 1;
            while(temp != 0){
                temp /= 10;
                fb *= 10;
            }

            StringBuilder sb = new StringBuilder();
            while(num != 0){
                int h = num / fb;
                while(h == 0){
                    fb /= 10;
                    h = num / fb;
                }
                if((h + 1) % 5 == 0){
                    sb.append(roman.get(fb));
                    sb.append(roman.get(fb * (h + 1)));
                    num -= (h * fb);
                }else if(h >= 5){
                    sb.append(roman.get(fb * 5));
                    num -= (5 * fb);
                }else{
                    sb.append(roman.get(fb));
                    num -= fb;
                }
            }
            return sb.toString();
        }
    }

}
