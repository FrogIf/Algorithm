package sch.frog.algorithm;

/**
 * 二叉树
 */
public class BinaryTree<E extends Comparable<E>> {

    private E data;

    private BinaryTree<E> left;

    private BinaryTree<E> right;

    private BinaryTree<E> parent;

    /**
     * 查找
     */
    public E search(E key){
        if(this.data == null){ return null; }

        BinaryTree<E> cursor = this;
        while(cursor != null){
            int mark = key.compareTo(cursor.data);
            if(mark == 0){
                return cursor.data;
            }else if(mark > 0){
                cursor = cursor.right;
            }else{
                cursor = cursor.left;
            }
        }
        return null;
    }

    private BinaryTree<E> minimumNode(){
        BinaryTree<E> cursor = this;
        while(cursor.left != null){
            cursor = cursor.left;
        }
        return cursor;
    }

    /**
     * 最小值
     */
    public E minimum(){
        return minimumNode().data;
    }

    /**
     * 最大值
     */
    public E maximum(){
        BinaryTree<E> cursor = this;
        while(cursor.right != null){
            cursor = cursor.right;
        }
        return cursor.data;
    }

    private BinaryTree<E> searchNode(E key){
        if(this.data == null){ return null; }

        BinaryTree<E> cursor = this;
        BinaryTree<E> node = null;
        while(cursor != null){
            int mark = key.compareTo(cursor.data);
            if(mark == 0){
                node = cursor;
                break;
            }else if(mark > 0){
                cursor = cursor.right;
            }else{
                cursor = cursor.left;
            }
        }
        return node;
    }

    private BinaryTree<E> successorNode(){
        if(this.right != null){
            return this.right.minimumNode();
        }else{
            BinaryTree<E> child = this;
            BinaryTree<E> cursor = child.parent;
            while(cursor != null && cursor.right == child){
                child = cursor;
                cursor = child.parent;
            }
            return cursor;
        }
    }

    /**
     * 后继
     */
    public E successor(E key){
        BinaryTree<E> node = searchNode(key);
        if(node != null){
            if(node.right != null){
                return node.minimum();
            }else{
                BinaryTree<E> child = node;
                BinaryTree<E> cursor = child.parent;
                while(cursor != null && cursor.right == child){
                    child = cursor;
                    cursor = child.parent;
                }
                return cursor == null ? null : cursor.data;
            }
        }

        return null;
    }

    /**
     * 前驱
     */
    public E predecessor(E key){
        BinaryTree<E> node = searchNode(key);
        if(node != null){
            if(node.left != null){
                return node.maximum();
            }else{
                BinaryTree<E> child = node;
                BinaryTree<E> cursor = child.parent;
                while(cursor != null && cursor.left == child){
                    child = cursor;
                    cursor = cursor.parent;
                }
                return cursor == null ? null : cursor.data;
            }
        }
        return null;
    }

    /**
     * 插入
     */
    public boolean insert(E e){
        if(this.data == null){
            this.data = e;
            return true;
        }

        BinaryTree<E> p = null;
        BinaryTree<E> cursor = this;
        // 找到e的父节点
        int mark = 0;
        while(cursor != null){
            p = cursor;
            mark = e.compareTo(cursor.data);
            if(mark == 0){
                return false;
            }else if(mark > 0){
                cursor = cursor.right;
            }else{
                cursor = cursor.left;
            }
        }

        BinaryTree<E> node = new BinaryTree<>();
        node.data = e;
        node.parent = p;

        if(mark > 0){
            p.right = node;
        }else{
            p.left = node;
        }

        return true;
    }

    /**
     * 删除
     * 返回一棵新书
     */
    public BinaryTree<E> delete(E key){
        BinaryTree<E> node = searchNode(key);

        if(node == null){ return this; }

        if(node.left == null){
            transplant(node, node.right);
            if(node.parent == null){
                if(node.right == null){
                    node.data = null;
                }
                return node;
            }else{
                return this;
            }
        }else if(node.right == null){
            transplant(node, node.left);
            if(node.parent == null){
                return node;
            }else{
                return this;
            }
        }else{
            BinaryTree<E> s = node.successorNode();
            assert s != null;
            if(s.parent != node){
                transplant(s, s.right); // 既然s已经是node的后继, s一定不会有left节点
                s.right = node.right;
                s.right.parent = s;
            }
            transplant(node, s);
            s.left = node.left;
            s.left.parent = s;

            if(s.parent == null){
                return s;
            }else{
                return this;
            }
        }
    }

    /**
     * 将树上节点aim替换为节点s
     */
    private static <E extends Comparable<E>> void transplant(BinaryTree<E> aim, BinaryTree<E> s){
        if(aim.parent != null){
            if(aim.parent.left == aim){
                aim.parent.left = s;
            }else{
                aim.parent.right = s;
            }
        }
        if(s != null){
            s.parent = aim.parent;
        }
    }

    /**
     * 中序遍历树
     */
    public static <E extends Comparable<E>> void foreachTree(BinaryTree<E> tree, ITreeNodeOperator<E> operator){
        if(tree != null){
            foreachTree(tree.left, operator);
            operator.operate(tree.data);
            foreachTree(tree.right, operator);
        }
    }

    public interface ITreeNodeOperator<E extends Comparable<E>>{
        void operate(E data);
    }


    public static void main(String[] args){
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(12);
        tree.insert(1);
        tree.insert(9);
        tree.insert(100);
        tree.insert(89);
        tree.insert(5);
        tree.insert(20);
        foreachTree(tree, System.out::println);
        System.out.println("------------");
        System.out.println(tree.minimum());
        System.out.println(tree.maximum());
        System.out.println("------------");
        System.out.println(tree.predecessor(20));
        System.out.println(tree.successor(20));
        System.out.println("------------");
        tree = tree.delete(12);
        foreachTree(tree, System.out::println);

    }

}
