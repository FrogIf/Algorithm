package sch.frog.algorithm;

public class SelectionSort {

    /*
     * 选择排序时间复杂度的计算:
     *     选择排序有两层循环:
     *          当 i = 0 时, 内层循环需要执行 n - 1 次
     *          当 i = 1 时, 内存循环需要执行 n - 2 次
     *          ...
     *          当 i = n - 1时, 内层循环需要执行 1 次
     *
     *     所以总共执行的次数为:
     *          (n - 1) + (n - 2) + (n - 3) + ... + 1
     *        显然这时一个等差数列, 所以有:
     *          (n - 1) + (n - 2) + (n - 3) + ... + 1 = ((n - 1 + 1) * n) / 2 = (n^2)/2
     *
     * 因此, 选择排序的时间复杂度为O(n^2)
     *
     */

    public static void selectionSort(int[] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = i + 1; j < arr.length; j++){
                if(arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] arr = {2, 5, 9, 1, 5, 2, 6, 2, 4, 23, 90, 29, 12};
        selectionSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.print("\t" + arr[i]);
        }
    }

}
