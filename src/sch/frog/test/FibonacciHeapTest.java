package sch.frog.test;

import sch.frog.clrs.FibonacciHeap;
import sch.frog.clrs.RandomOrder;

import java.util.Arrays;

public class FibonacciHeapTest {

    public static void main(String[] args){
        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
//        int[] arr = new int[]{11, 3, 8, 9, 17, 16, 15, 6, 5, 7, 12, 13, 4, 10, 1, 2, 14};
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17};
        RandomOrder.randomOrder(arr);
        System.out.println(Arrays.toString(arr));

        for (int n : arr) {
            heap.insert(new FibonacciHeap.Node<>(n));
        }

        arr = new int[]{20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        RandomOrder.randomOrder(arr);
        System.out.println(Arrays.toString(arr));

        FibonacciHeap<Integer> heap2 = new FibonacciHeap<>();
        for(int n : arr){
            heap2.insert(new FibonacciHeap.Node<>(n));
        }

        heap.union(heap2);

        FibonacciHeap.Node<Integer> node = new FibonacciHeap.Node<>(31);
        heap.insert(node);
//        System.out.println("=======================");
//        System.out.println(heap.show());
//        System.out.println("=======================");

        System.out.println(heap.extractMin());

//        while (!heap.isEmpty()){
//            System.out.println("---" + heap.extractMin() + "---");
//        }

        System.out.println("=============================================");

        for(int i = 31; i >= 0; i--){
            heap.decrease(node, i);
            System.out.println("============" + i + "===========");
            System.out.println(heap.show());
            System.out.println("=======================");
        }
    }
}
