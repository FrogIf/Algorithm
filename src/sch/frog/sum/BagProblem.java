package sch.frog.sum;

import java.util.Arrays;

/**
 * 动态规划 -- 0-1背包问题:
 *  背包可以装35kg物品
 *  现在有:
 *      音响 - 30kg - 3000元
 *      笔记本电脑 - 20kg - 2000元
 *      吉他 - 15kg - 1500元
 *  请指出如何选择向背包中放入的物品, 使价值最大
 */
public class BagProblem {

    public static void main(String[] args){
        int[][] info = new int[][]{
                {30, 3000},
                {20, 2000},
                {15, 1500}
        };
        int[] res = bagProblem(info, 45);
        System.out.println(Arrays.toString(res));
    }

    /*
     * 分析:
     *  1. 最优子结构:
     *      S = max{Si + ai}
     *
     *
     * 返回结果:
     *  0 - 背包使用容量, 1 - 背包装载总价值, 2, 3, 4, 5...背包中装入的元素索引
     */
    private static int[] bagProblem(int[][] arr, int capacity){
        int[][] levelRes = new int[arr.length][]; // 二维数组中子数组的结构: 0 - 使用的总容量, 1 - 总价值, 2,3,4... 已装入背包中的元素索引
        for(int i = 0; i < arr.length; i++){
            levelRes[i] = new int[]{arr[i][0], arr[i][1], i};
        }
        for(int i = 2; i <= arr.length; i++){   // 控制背包中装入物品数量
            int[][] temp = new int[levelRes.length][];
            for(int j = 0; j < levelRes.length; j++){ // 指定使用哪一个子结果构造更大范围的子结果
                // 初始化
                temp[j] = new int[2 + i];
                for(int k = 0; k < levelRes[j].length; k++){
                    temp[j][k] = levelRes[j][k];
                }
                temp[j][i + 1] = -1;
                if(temp[j][i] == -1){ continue; }   // 说明老早背包就已经装满了

                // 获取当前范围的最优解
                label : for(int k = 0; k < arr.length; k++){
                    for(int m = 2; m < 1 + i; m++){   // 判断当前索引是否已经存在于上一个子结果中
                        int a = temp[j][m];
                        if(a == k){ continue label; }
                    }

                    int c = temp[j][0]; // 获取当前总容量
                    int w = temp[j][1]; // 总价值
                    int a = temp[j][1 + i];
                    int ci = a != -1 ? arr[a][0] : 0;   // 当前末端容量
                    int wi = a != -1 ? arr[a][1] : 0;   // 当前结果的价值
                    if(arr[k][0] + c - ci <= capacity && wi < arr[k][1]){
                        temp[j][1 + i] = k;
                        temp[j][1] = w - wi + arr[k][1];
                        temp[j][0] = c - ci + arr[k][0];
                    }

                }
            }
            levelRes = temp;
        }

        int[] result = levelRes[0];
        for(int i = 1; i < levelRes.length; i++){
            if(levelRes[i][1] > result[1]){
                result = levelRes[i];
            }
        }

        return result;
    }

}
