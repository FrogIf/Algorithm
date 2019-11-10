package sch.frog.algorithm;

/**
 * 最长公共子序列
 */
public class LCS {

    public static void main(String[] args){
        String a = "ABCBDAB";
        String b = "BDCABA";
        System.out.println(longestCommonString(a.toCharArray(), b.toCharArray()));
    }

    private static int longestCommonString(char[] a, char[] b){
        int[][] res = new int[a.length + 1][b.length + 1];
        for(int i = 1; i <= a.length; i++){
            char ac = a[i - 1];
            for(int j = 1; j <= b.length; j++){
                char bc = b[j - 1];
                if(ac == bc){
                    res[i][j] = res[i - 1][j - 1] + 1;
                }else if(res[i - 1][j] > res[i][j - 1]){
                    res[i][j] = res[i - 1][j];
                }else{
                    res[i][j] = res[i][j - 1];
                }
            }
        }


        int i = a.length, j = b.length;
        StringBuilder sb = new StringBuilder();
        while(i > 0 && j > 0){
            int l = res[i][j];
            if(l == res[i - 1][j]){
                i--;
            }else if(l == res[i][j - 1]){
                j--;
            }else{
                sb.append(a[i - 1]);
                i--;
                j--;
            }
        }

        System.out.println(sb.reverse().toString());

        return res[a.length][b.length];
    }

}
