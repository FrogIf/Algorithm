package sch.frog.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 贪心算法应用
 * 选出最大的互相兼容活动子集
 */
public class GreedyActivitySelector {

    public static void main(String[] args){
        int[][] apply = new int[][]{
                new int[]{1, 4},
                new int[]{3, 5},
                new int[]{0, 6},
                new int[]{5, 7},
                new int[]{3, 9},
                new int[]{5, 9},
                new int[]{6, 10},
                new int[]{8, 11},
                new int[]{8, 12},
                new int[]{2, 14},
                new int[]{12, 16}
        };

        randomSort(apply);
        showInfo(apply);
        apply = countingSort(apply);
        System.out.println();
        showInfo(apply);
        System.out.println();
        int[][] strategy = greedyActivitySelector(apply);
        showInfo(strategy);
    }

    /**
     * 应用贪心策略获取最大兼容子集
     */
    public static int[][] greedyActivitySelector(int[][] apply){
        int f = 0;  // 最早结束时间
        int[][] sel = new int[apply.length][];
        int k = 0;
        for(int i = 0; i < apply.length; i++){
            if(apply[i][0] >= f){
                sel[k++] = apply[i];
                f = apply[i][1];
            }
        }
        return sel;
    }

    private static void showInfo(int[][] arr){
        for (int[] ints : arr) {
            System.out.print(Arrays.toString(ints) + ", ");
        }
    }

    public static void randomSort(int[][] arr){
        Random r = new Random();
        for(int i = 0; i < arr.length; i++){
            int pos = r.nextInt(arr.length - i) + i;
            int[] temp = arr[0];
            arr[0] = arr[pos];
            arr[pos] = temp;
        }
    }

    // 按照结束时间排序
    public static int[][] countingSort(int[][] arr){
        int max = arr[0][1];
        int min = max;
        for(int i = 1; i < arr.length; i++){
            int a = arr[i][1];
            if(a > max){
                max = a;
            }else if(min > a){
                min = a;
            }
        }

        int[] count = new int[max - min + 1];
        for(int i = 0; i < arr.length; i++){
            count[arr[i][1] - min]++;
        }

        for(int i = 1; i < count.length; i++){
            count[i] = count[i - 1] + count[i];
        }

        int[][] result = new int[arr.length][];
        for(int i = result.length - 1; i >= 0; i--){
            result[count[arr[i][1] - min] - 1] = arr[i];
            count[arr[i][1] - min]--;
        }
        return result;
    }

}
