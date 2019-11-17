package sch.frog.sum;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机算法:
 * 将一个数组元素随机乱序
 */
public class RandomOrder {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        randomOrder(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 该算法保证针对指定的数组(长度为n), 任何一种排列出现的概率均相同
     * 证明:
     * 首先, 明确需要求证的结论, 任何一种排列出现的概率均相同, 说明
     *  第一个位置有n中选择, 第二个位置为(n-1)中选择...共有n!中选择
     *  所以, 每一种排列出现的概率为1/n!
     *
     * 下面证明:
     *   证明循环不变式: 第x次迭代前, 对于每一个可能的x - 1排列, 出现的概率为(n - x + 1)! / n!
     *
     *   第一次迭代前, 概率为1, 即每一个0个元素的排列出现的概率为1, 显然成立
     *   假设第k - 1次迭代前, 该循环不变式成立, 即k - 2个排列出现的概率为(n - k + 2)! / n!
     *   第k次迭代前, 有k - 1个排列出现的概率为:(n - k + 2)! / n! * (1 / (n - k + 2)) = (n - x + 1)! / n!
     *   当循环终结时, x = n + 1, 此时n种排列出现的概率为1/n!
     */
    private static void randomOrder(int[] arr){
        Random r = new Random();
        for(int i = 0; i < arr.length - 1; i++){
            /*
            * 这段代码一种理解方式是i与一个i之后的位置进行随机交换
            *
            * 但是实际上更恰当的理解方式是:
            *   i是用来指示当前需要填充数字的位置, 从剩下的n - i - 1个数中挑选一个添加到该位置
            * 每次循环保证i之前的数都是选好的, i之后的数都是选剩下的, 交换的过程实际上是把选好的放入正确位置, 选剩下的放在i后边
            */
            int j = r.nextInt(arr.length - i) + i;
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }
    }

}
