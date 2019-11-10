package sch.frog.algorithm;

public class CutRod {

    public static void main(String[] args){
        System.out.println(bottomUpCutRot(40));
//        System.out.println(cutRodWithMemoized(40));
//        System.out.println(cutRodSlow(40));
    }

    /*
     * 长度| 1 | 2 | 3 | 4 | 5  | 6  | 7  | 8  | 9  | 10 |
     * ---------------------------------------------------
     * 价格| 1 | 5 | 8 | 9 | 10 | 17 | 18 | 22 | 25 | 30 |
     */

    private static int[] base = new int[]{
            1, 5, 8, 9, 10, 17, 18, 22, 25, 30
    };


    private static int bottomUpCutRot(int n){
        int[] m = new int[n + 1];
        for(int i = 1; i <= n; i++){    // 指定子规模
            int q = 0;
            for(int j = 1; j <= i; j++){    // 求解子规模
                /*
                 * 每一个规模都可以用到前面的规模的结果
                 */
                int t = (j <= 10 ? base[j - 1] : m[j]) + m[i - j];
                if(t > q){
                    q = t;
                }
            }
            m[i] = q;
        }
        return m[n];
    }

    private static int cutRodSlow(int n){
        if(n == 0){
            return 0;
        }

        int q = 0;
        for(int i = 1; i <= 10 && i <= n; i++){
            int t = cutRodSlow(n - i) + base[i - 1];
            if(t > q){
                q = t;
            }
        }
        return q;
    }

    private static int cutRodWithMemoized(int n){
        int[] m = new int[n];   // 记忆子结果数组, 全部被初始化为0
        return cutRowWithMemoizedRe(n, m);
    }

    private static int cutRowWithMemoizedRe(int n, int[] m){
        if(n == 0){
            return 0;
        }
        if(m[n - 1] > 0){
            return m[n - 1];
        }else{
            int q = 0;
            for(int i = 1; i <= 10 && i <= n; i++){
                int t = base[i - 1] + cutRowWithMemoizedRe(n - i, m);
                if(t > q){
                    q = t;
                }
            }
            m[n - 1] = q;
            return q;
        }

    }



}
