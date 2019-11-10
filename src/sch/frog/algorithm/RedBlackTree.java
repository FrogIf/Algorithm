package sch.frog.algorithm;

public class RedBlackTree<E extends Comparable<E>> {
    /*
     * 红黑树性质:
     * 1. 每个节点或是红色的或是黑色的
     * 2. 根节点是黑色的
     * 3. 每个叶节点(null)是黑色的
     * 4. 如果一个节点是红色的, 则它的两个子节点都是黑色的
     * 5. 每个节点, 从该节点到其所有后代节点的简单路径上, 均包含下相同数目的黑色节点
     */

    private static final byte RED = 0;

    private static final byte BLACK = 1;

    private byte color;

    private E data;

    private RedBlackTree<E> p;

    private RedBlackTree<E> left;

    private RedBlackTree<E> right;

    /**
     * 左旋
     */
    private static <E extends Comparable<E>> RedBlackTree<E> leftRotate(RedBlackTree<E> tree, RedBlackTree<E> cursor){
        RedBlackTree<E> nCursor = cursor.right;
        cursor.right = nCursor.left;
        nCursor.left = cursor;

        // 调整相关父节点
        nCursor.p = cursor.p;

        if(cursor.right != null){
            cursor.right.p = cursor;
        }

        RedBlackTree<E> parent = cursor.p;
        if(parent != null){
            if(parent.left == cursor){
                parent.left = nCursor;
            }else{
                parent.right = nCursor;
            }
        }else{
            tree = nCursor;
        }

        cursor.p = nCursor;

        return tree;
    }

    /**
     * 右旋
     */
    private static <E extends Comparable<E>> RedBlackTree<E> rightRotate(RedBlackTree<E> tree, RedBlackTree<E> cursor){
        RedBlackTree<E> nCursor = cursor.left;
        cursor.left = nCursor.right;
        nCursor.right = cursor;

        // 调整相关父节点
        nCursor.p = cursor.p;

        if(cursor.left != null){
            cursor.left.p = cursor;
        }

        if(cursor.p != null){
            if(cursor.p.left == cursor){
                cursor.p.left = nCursor;
            }else{
                cursor.p.right = nCursor;
            }
        }else{
            tree = nCursor;
        }

        cursor.p = nCursor;

        return tree;
    }

    /**
     * 插入数据
     */
    public RedBlackTree<E> insert(E data){
        if(this.data == null){
            this.data = data;
            this.color = BLACK;
            return this;
        }

        RedBlackTree<E> cursor = this;
        RedBlackTree<E> p = null;
        int mark = 0;
        while(cursor != null){
            p = cursor;
            mark =data.compareTo(cursor.data);
            if(mark == 0){
                throw new IllegalArgumentException("data exists.");
            }else if(mark > 0){
                cursor = cursor.right;
            }else{
                cursor = cursor.left;
            }
        }

        RedBlackTree<E> node = new RedBlackTree<>();
        node.color = RED;
        node.data = data;
        node.p = p;

        if(mark > 0){
            p.right = node;
        }else{
            p.left = node;
        }

        // 开始进行平衡操作
        return fixTree(this, node);
    }

    private static <E extends Comparable<E>> RedBlackTree<E> fixTree(RedBlackTree<E> tree, RedBlackTree<E> cursor){
        while(cursor.p != null && cursor.p.color == RED){
            if(cursor.p.p.left == cursor.p){
                RedBlackTree<E> uncle = cursor.p.p.right;
                if(uncle != null && uncle.color == RED){  // 叔节点是红色
                    uncle.color = BLACK;
                    cursor.p.color = BLACK;
                    cursor.p.p.color = RED;
                    cursor = cursor.p.p;
                }else{
                    if(cursor == cursor.p.right){   // 如果是右节点
                        cursor = cursor.p;
                        tree = leftRotate(tree, cursor);
                    }
                    cursor.p.p.color = RED;
                    cursor.p.color = BLACK;
                    tree = rightRotate(tree, cursor.p.p);
                }
            }else{
                RedBlackTree<E> uncle = cursor.p.p.left;
                if(uncle != null && uncle.color == RED){    // 叔节点是红色
                    uncle.color = BLACK;
                    cursor.p.color = BLACK;
                    cursor.p.p.color = RED;
                    cursor = cursor.p.p;
                }else{
                    if(cursor == cursor.p.left){
                        cursor = cursor.p;
                        tree = rightRotate(tree, cursor);
                    }
                    cursor.p.p.color = RED;
                    cursor.p.color = BLACK;
                    tree = leftRotate(tree, cursor.p.p);
                }
            }
        }
        tree.color = BLACK;
        return tree;
    }

    /**
     * 删除数据, 返回一棵新树
     * @param key
     * @return
     */
    public BinaryTree<E> delete(E key){
        
        return null;
    }

    public E search(E key){
        if(this.data == null){ return null; }   // empty tree

        RedBlackTree<E> cursor = this;
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

    public E minimum(){
        RedBlackTree<E> cursor = this;
        while(cursor.left != null){
            cursor = cursor.left;
        }
        return cursor.data;
    }

    public E maximum(){
        RedBlackTree<E> cursor = this;
        while(cursor.right != null){
            cursor = cursor.right;
        }

        return cursor.data;
    }


    private RedBlackTree<E> find(E key){
        if(this.data == null) { return null; }

        // 查找key所在节点
        RedBlackTree<E> cursor = this;
        RedBlackTree<E> node = null;
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

    public E successor(E key){
        RedBlackTree<E> node = find(key);

        if(node != null){
            if(node.right != null){
                return node.right.minimum();
            }else{
                RedBlackTree<E> p = node.p;
                while(p != null && p.right == node){
                    node = p;
                    p = node.p;
                }
                return p == null ? null : p.data;
            }
        }

        return null;
    }

    public E predecessor(E key){
        RedBlackTree<E> node = find(key);

        if(node != null){
            if(node.left != null){
                return node.left.maximum();
            }else {
                RedBlackTree<E> p = node.p;
                while(p != null && p.left == node){
                    node = p;
                    p = node.p;
                }

                return p == null ? null : p.data;
            }
        }
        return null;
    }


    /**
     * 中序遍历树
     */
    public static <E extends Comparable<E>> void foreachTree(RedBlackTree<E> tree, ITreeNodeOperator<E> operator){
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
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree = tree.insert(12);
        tree = tree.insert(1);
        tree = tree.insert(9);
        tree = tree.insert(100);
        tree = tree.insert(89);
        tree = tree.insert(5);
        tree = tree.insert(20);
        foreachTree(tree, System.out::println);
    }


}
