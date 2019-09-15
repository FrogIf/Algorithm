package sch.frog.algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 使用最大堆实现简单的优先级队列
 */
public class PriorityQueue {

    private int[] array = new int[10];

    private int size = 0;

    public PriorityQueue(int[] array) {
        this.array = array;
        this.size = array.length;
        this.buildMaxHeapify();
    }

    public PriorityQueue() { }

    public int maximum(){
        if(size == 0){
            throw new IllegalStateException("no elements in this heap.");
        }
        return array[0];
    }

    public int extractMax(){
        if(size == 0){
            throw new IllegalStateException("no elements in this heap.");
        }
        int max = this.array[0];
        this.array[0] = this.array[--this.size];
        this.array[this.size] = 0;
        this.buildMaxHeapify();
        return max;
    }

    /**
     * 升权操作
     */
    public void increaseKey(int x, int k){
        if(x > k){
            throw new IllegalArgumentException("k must larger than x.");
        }
        int index = -1;
        for(int i = 0; i < this.size; i++){
            if(x == this.array[i]){
                index = i;
                break;
            }
        }
        if(index == -1){
            throw new IllegalArgumentException("can't find this element.");
        }

        this.array[index] = k;
        this.upfloat(index);
    }

    /**
     * 向堆中插入元素
     * @param x
     */
    public void insert(int x){
        this.size++;
        if(this.size > this.array.length){
            extendCapacity();
        }
        this.array[this.size - 1] = x;
        this.upfloat(this.size - 1);
    }

    private void extendCapacity(){
        this.array = Arrays.copyOf(this.array, this.size << 1);
    }

    /**
     * 上浮
     */
    private void upfloat(int index){
        int t = this.array[index];

        int low = index;
        int i = (index - 2 + index % 2) >> 1;

        for(; i >= 0;){
            if(this.array[i] >= t){ break; }

            this.array[low] = this.array[i];
            low = i;

            i = (i - 2 + i % 2) >> 1;
        }

        this.array[low] = t;

    }

    private void buildMaxHeapify(){
        for(int i = size >> 1; i >= 0; i--){
            maxHeapify(this.array, i, size);
        }
    }

    /**
     * 不包括end
     */
    private void maxHeapify(int[] arr, int root, int end){
        int t = arr[root];

        int large = root;
        int l = (root << 1) + 1;
        int r = l + 1;

        while(r < end){
            if(arr[l] > t){
                large = l;
            }

            if(arr[r] > arr[large]){
                large = r;
            }

            if(large == root){
                break;
            }

            arr[root] = arr[large];
            root = large;
            l = (root << 1) + 1;
            r = l + 1;
        }

        if(r == end && arr[l] > t){
            arr[root] = t;
            root = l;
        }
        arr[root] = t;
    }

    public static void main(String[] args){
        PriorityQueue pq = new PriorityQueue(new int[]{5, 3, 17, 10, 84, 19, 6, 22, 9});
        pq.insert(34);
        pq.increaseKey(5, 20);
        System.out.println(Arrays.toString(pq.array));
        System.out.println(pq.extractMax());
        System.out.println(Arrays.toString(pq.array));
    }

}

