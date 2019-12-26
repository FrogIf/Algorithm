package sch.frog.clrs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 斐波那契堆
 */
public class FibonacciHeap<T extends Comparable<T>> {

    /*
     * 势函数:
     *  φ(H) = t(H) + 2m(H)
     *      t(H) - 堆的根节点数量
     *      m(H) - 堆中被标记节点数量
     *
     * 最大度数:
     *  D(n) = O(lgn)
     */

    private Node<T> min;

    private int size;

    /**
     * 向堆中插入元素
     * @param node 待插入元素
     */
    public void insert(Node<T> node){
        if(node == null || node.data == null){
            throw new IllegalArgumentException("empty node.");
        }
        node.degree = 0;
        node.parent = null;
        node.child = null;
        node.mark = false;

        if(min == null){
            min = node;
            min.right = min.left = min;
        }else{
            Node<T> tl = min.left;

            tl.right = node;
            node.left = tl;

            min.left = node;
            node.right = min;

            if(node.data.compareTo(min.data) < 0){
                min = node;
            }
        }

        this.size++;
    }

    /**
     * 获取堆中的最小值
     * @return 最小值
     */
    public Node<T> minimum(){
        return this.min;
    }

    /**
     * 将heap合并至当前堆中, 并销毁heap
     * @param heap 待合并的堆
     */
    public void union(FibonacciHeap<T> heap){
        if(this.min != null){
            if(heap.min != null){
                Node<T> thisHL = this.min.left;
                Node<T> thatHL = heap.min.left;

                thisHL.right = heap.min;
                heap.min.left = thisHL;

                thatHL.right = this.min;
                this.min.left = thatHL;

                this.size += heap.size;

                if(heap.min.data.compareTo(this.min.data) < 0){
                    this.min = heap.min;
                }
            }
        }else{
            this.min = heap.min;
        }

        // destroy input heap
        heap.min = null;
        heap.size = 0;
    }

    /**
     * 从堆中取出最小元素
     * @return 最小值
     */
    public T extractMin(){
        Node<T> result = this.min;
        if(result != null){
            if(result.child != null){
                if(this.min.left != this.min){
                    result.child.parent = null;
                    Node<T> tr = result.child.right;

                    this.min.left.right = tr;
                    tr.left = this.min.left;

                    result.child.right = this.min.right;
                    this.min.right.left = result.child;

                    this.min = result.child;
                    consolidate();
                }else{
                    this.min = result.child;
                    Node<T> cursor = result.child;
                    do{
                        if(cursor.data.compareTo(this.min.data) < 0){
                            this.min = cursor;
                        }
                        cursor = cursor.left;
                    }while (cursor != result.child);
                }
            }else{
                if(this.min.left == this.min){  // 空堆
                    this.min = null;
                }else{
                    this.min.left.right = this.min.right;
                    this.min.right.left = this.min.left;
                    this.min = this.min.right;
                    consolidate();
                }
            }
            this.size--;
            return result.data;
        }
        return null;
    }

    /**
     * 整理整个堆, 使得根环中每一棵树的度数均不相同, 并找出堆的新最小值
     */
    private void consolidate(){
        @SuppressWarnings("unchecked")
        Node<T>[] arr = new Node[this.size];

        Node<T> cursor;
        Node<T> next = this.min;
        Node<T> end = this.min.right;
        boolean run;
        do{
            cursor = next;
            run = cursor != end;
            next = next.left;
            int d = cursor.degree;
            Node<T> n;
            while((n = arr[d]) != null){  // 存在相同度数, 需要合并
                if(cursor.data.compareTo(n.data) > 0){
                    Node<T> temp = n;
                    n = cursor;
                    cursor = temp;
                }
                link(cursor, n);
                arr[d++] = null;
            }
            arr[cursor.degree] = cursor;
        }while(run);

        this.min = null;
        for (Node<T> node : arr) {
            if (node != null) {
                if (this.min == null) {
                    this.min = node;
                    this.min.left = this.min.right = this.min;
                } else {
                    Node<T> tn = this.min.left;
                    tn.right = node;
                    node.left = tn;

                    this.min.left = node;
                    node.right = this.min;

                    if (node.data.compareTo(this.min.data) < 0) {
                        this.min = node;
                    }
                }
            }
        }

    }

    /**
     * 将一棵树作为另一个树的子树, 并清除子树的标记
     * @param p 父树
     * @param c 子树
     */
    private void link(Node<T> p, Node<T> c){
        c.left.right = c.right;
        c.right.left = c.left;

        if(p.child != null){
            Node<T> tl = p.child.left;
            c.right = p.child;
            c.left = tl;

            tl.right = p.child.left = c;
        }else{
            p.child = c;
            c.right = c.left = c;
        }
        c.parent = p;
        p.degree++;
        c.mark = false;
    }

    /**
     * 判断一个堆是否是空堆
     * @return true 空堆, false 非空堆
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * 返回堆的大小
     * @return 堆的大小
     */
    public int size(){
        return this.size;
    }

    /**
     * 堆节点
     * @param <T>
     */
    public static class Node<T extends Comparable<T>>{
        public Node(T data) {
            this.data = data;
        }
        private T data;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;
        private Node<T> child;
        private int degree;
        private boolean mark;

        public T getData() {
            return data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }


    /**
     * 调试用, 粗略展示堆的结构
     */
    public String show(){
        if(this.min == null){
            return "";
        }
        Node<T> cursor = this.min;
        List<Node<T>> children = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        do{
            sb.append(cursor.data).append(',');
            if(cursor.child != null){
                children.add(cursor.child);
            }
            cursor = cursor.right;
        }while (cursor != this.min);
        sb.append('\n');

        Set<Node<T>> nodes = new HashSet<>();
        List<Node<T>> nc = new ArrayList<>();
        while (!children.isEmpty()){
            for (Node<T> child : children) {
                cursor = child;
                do{
                    sb.append(cursor.data).append(',');
                    if(cursor.child != null){
                        nc.add(cursor.child);
                        if(!nodes.add(cursor.child)){
                            sb.append("error : ").append(cursor.data);
                            return sb.toString();
                        }
                    }
                    cursor = cursor.right;
                }while (cursor != child);
                sb.append('|');
            }
            sb.append('\n');
            List<Node<T>> temp = children;
            children = nc;
            temp.clear();
            nc = temp;
        }

        return sb.toString();
    }


}
