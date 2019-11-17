package sch.frog.sum;

import java.util.Arrays;

/**
 * 动态规划 -- 钢条分割
 * 一公司出售的钢条价格:
 * 长度 | 1 | 2 | 3 | 4 |  5 |  6 |  7 |  8 |  9 | 10
 * ---------------------------------------------------
 * 价格 | 1 | 5 | 8 | 9 | 10 | 17 | 17 | 20 | 24 | 30
 *
 * 问题 : 给定一段长度为n的钢条, 求切割方案, 使销售收益最大
 */
public class CutRod {

    public static void main(String[] args){
        int[][] price = new int[][]{    // 钢条各个规格的价格
                {4, 9},
                {5, 10},
                {6, 17},
                {1, 1},
                {2, 5},
                {9, 24},
                {10, 30},
                {3, 8},
                {7, 17},
                {8, 20},
        };

        int[][] strategy = cutRod(price, 5);
        System.out.println("最大价值为:" + strategy[1][0]);
        System.out.println("分割策略为:" + Arrays.toString(strategy[0]));
    }

    /**
     * @param price 基本单价
     * @param len 单价长度
     * @return 返回切割策略, 返回结构 : [[分割长度数组], [最大价值]]
     */
    private static int[][] cutRod(int[][] price, int len){
        /*
         * 分析:
         *    最优解:
         *       S(n) = strategy{S(i) + S(n - i)}
         *       当求解S(n)时, 显然S(i)和S(n-i)
         */

        // 根据price进行初始化
        int[][] strategy = new int[len + 1][];   // 索引表示规模, 0的时候是0, 子数组中 0 - 价格, 1 - 第一刀下刀位置
        strategy[0] = new int[2];

        for (int j = 0; j < price.length; j++) {
            int i = price[j][0];
            if(i <= len){   // 防止给出的基本长度规格中有超出待求解长度的
                strategy[i] = new int[2];
                strategy[i][0] = price[j][1];
                strategy[i][1] = price[j][0];
            }
        }

        for(int i = 1; i < len + 1; i++){   // 控制钢条长度
            int[] cursor = strategy[i];
            if(cursor == null){
                cursor = new int[2];
                strategy[i] = cursor;
            }
            int p = cursor[0];  // 记录当前规模的最大切割价值
            int c = cursor[1];  // 用于记录第一刀下刀位置
            for(int j = 1; j <= i; j++){    // 钢条剩余切割位置
                int t = strategy[j][0] + strategy[i - j][0];
                if(p < t) {
                    p = t;
                    c = strategy[j][1];
                }
            }
            cursor[0] = p;
            cursor[1] = c;
        }

        // 构造输出结果
        int[] tempResult = new int[len];
        int i = 0;
        while(len > 0){
            int s = strategy[len][1];
            tempResult[i++] = s;
            len -= s;
        }

        int[][] result = new int[2][];
        int[] cut = new int[i];
        result[0] = cut;
        result[1] = strategy[strategy.length - 1];
        for(int j = 0; j < i; j++){
            cut[j] = tempResult[j];
        }
        return result;
    }
}
