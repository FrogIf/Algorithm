package sch.frog.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机算法:
 *      存在一个对集合进行操作的算法, 这个算法的期望运行时间较好, 但是O时间较差
 *      如果这个算法的输入集合是均匀分布的, 那么就可以保证运行时间期望较好
 *      但是外界输入是没有办法保证的, 所以, 需要随机算法, 将输入集合重新打乱, 这样就保证了输入集合的排列情况时均匀分布
 */
public class RandomOrder {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        random(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void random(int[] arr){
        Random r = new Random();
        for(int i = 0; i < arr.length - 1; i++){
            int j = r.nextInt(arr.length - i - 1) + 1 + i;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

}
