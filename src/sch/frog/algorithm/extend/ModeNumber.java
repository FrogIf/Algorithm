package sch.frog.algorithm.extend;

/**
 * 求解众数
 *  众数: 这里的众数是指在集合中, 出现次数超过半数的数字, 而非常规意义上的众数
 */
public class ModeNumber {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 2, 1, 4, 1, 1, 4, 1, 7, 1, 6, 1, 1, 1, 1};
        System.out.println(getMode(arr));
    }

    public static int getMode(int[] arr){
        int frequency = 1;
        int n = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(n == arr[i]){
                frequency++;
            }else if(frequency == 0){
                frequency = 1;
                n = arr[i];
            }else{
                frequency--;
            }
        }
        int f = 0;
        for(int i = 0; i < arr.length; i++){
            if(n == arr[i]){
                f++;
            }
        }
        return f >= arr.length / 2 ? n : -1;
    }

}
