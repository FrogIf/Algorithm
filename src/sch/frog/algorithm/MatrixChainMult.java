package sch.frog.algorithm;

public class MatrixChainMult {

    public static void main(String[] args){
        matrixChain(new int[]{30, 35, 15, 5, 10, 20, 25});
    }

    public static void matrixChain(int[] border){
        int n = border.length - 1;  // 总的矩阵个数
        int[][] m = new int[n][n];  // i -- j矩阵连乘的最小代价
        int[][] s = new int[n][n];

        for(int r = 2; r < n + 1; r++){  // 控制求解规模, 从2开始
            for(int i = 0; i < n - r + 1; i++){  // 控制规模起始位置, 从0开始, 0表示1号矩阵
                int j = i + r - 1; // 控制规模终止位置
                m[i][j] = Integer.MAX_VALUE;
                for(int k = i; k < j; k++){ // 控制分割位置, k表示第k号矩阵的后方开始分割
                    int t = m[i][k] + m[k + 1][j] + border[i] * border[k + 1] * border[j + 1];
                    if(t < m[i][j]){
                        m[i][j] = t;
                        s[i][j] = k;
                    }
                }
            }
        }

        System.out.println("总代价是:" + m[0][n - 1]);
        System.out.println("分割策略是:");
        showStrategy(s, 0, n - 1);
    }

    public static void showStrategy(int[][] s, int start, int end){
        if(start == end){
            System.out.print("A");
        }else{
            System.out.print("(");
            int ts = s[start][end];
            showStrategy(s, start, ts);
            showStrategy(s, ts + 1, end);
            System.out.print(")");
        }
    }

}
