package sch.frog.sum;

import java.util.Arrays;

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
        int[][] arrange = greedyActivitySelector(apply);
        for (int[] ints : arrange) {
            System.out.print(Arrays.toString(ints) + ", ");
        }
    }

    private static int[][] greedyActivitySelector(int[][] arr){
        int[][] activity = sortActivityByEndTime(arr);
        int f = activity[0][0]; // 初始化结束时间为贪心策略中肯定为第一个元素的开始时间
        int[][] temp = new int[arr.length][];
        int k = 0;
        for(int i = 0; i < activity.length; i++){
            if(activity[i][0] >= f){
                f = activity[i][1];
                temp[k++] = activity[i];
            }
        }

        int[][] result = new int[k][];
        for(int i = 0; i < k; i++){
            result[i] = temp[i];
        }
        return result;
    }

    /**
     * 根据活动结束时间, 给活动排序
     * @param arr 待排序的活动, 二维数组, 每一个子数组长度为2, 第一个元素时开始时间, 第二个元素时结束时间
     * @return 排序后的活动序列
     */
    private static int[][] sortActivityByEndTime(int[][] arr){
        int min = arr[0][1];
        int max = min;
        for(int i = 1; i < arr.length; i++){
            int a = arr[i][1];
            if(a > max){
                max = a;
            }else if(a < min){
                min  = a;
            }
        }

        int[] count = new int[max - min + 1];
        for(int i = 0; i < arr.length; i++){
            count[arr[i][1] - min]++;
        }

        for(int i = 1; i < count.length; i++){
            count[i] = count[i] + count[i - 1];
        }

        int[][] result = new int[arr.length][];
        for(int i = result.length - 1; i >= 0; i--){
            result[count[arr[i][1] - min] - 1] = arr[i];
            count[arr[i][1] - min]--;
        }
        return result;
    }
}
