package sch.frog.test;

import sch.frog.clrs.BTree;

public class BTreeTest {

    public static void main(String[] args){
        BTree<Integer> tree = new BTree<>();
        for(int i = 0; i < 20; i++){
            System.out.println(tree.insert(i));
        }

        for (int i = 0; i < 20; i++){
            System.out.println(tree.search(i));
        }
    }
}
