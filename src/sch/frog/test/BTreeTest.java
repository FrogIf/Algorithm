package sch.frog.test;

import sch.frog.clrs.BTree;

public class BTreeTest {

    public static void main(String[] args){
        BTree<Integer> tree = new BTree<>();
        int end = 20;
        for(int i = 0; i <= end; i++){
            System.out.println(i + "-" + tree.insert(i));
        }

        for (int i = 0; i <= end; i++){
            System.out.println(i + "-" + tree.search(i));
        }

        for(int i = 0; i <= end; i++){
            System.out.println(i + "-" + tree.delete(i));
        }

        for(int i = 0; i <= end; i++){
            System.out.println(i + "-" + tree.delete(i));
        }
    }
}
