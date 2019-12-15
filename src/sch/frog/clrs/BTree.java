package sch.frog.clrs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class BTree<T extends Comparable<T>> {

    /*
     * 1. 每一个叶节点具有相同的深度
     * 2. 每个叶节点包含的key数目有上界和下界:
     *      (1) 上界 : 2 * degree - 1
     *      (2) 下界(根节点除外) : degree - 1
     */

    private static final int DEGREE = 2;

    private Node<T> root = new Node<>();

    public void delete(T k){
        Node<T> cursor = this.root;
        while(true){
            Iterator<Key<T>> itr = cursor.keys.descendingIterator();
            Node<T> tc = null;  // k所处的节点
            Key<T> x = null;
            Key<T> px = null;
            while(itr.hasNext()){
                px = x;
                x = itr.next();
                int c = x.data.compareTo(k);
                if(c == 0){
                    // 执行删除
                    if(cursor.leaf){
                        itr.remove();
                        return; // 删除完成
                    }else{
                        if(x.pre.keys.size() >= DEGREE){
                            Key<T> tKey = maxKey(x.pre);
                            this.delete(tKey.data);
                            x.data = tKey.data;
                            return;
                        }else if(x.next.keys.size() >= DEGREE){
                            Key<T> tKey = minKey(x.next);
                            this.delete(tKey.data);
                            x.data = tKey.data;
                            return;
                        }else{
                            Node<T> nNode = new Node<>();
                            if(itr.hasNext()){
                                itr.next().next = nNode;
                            }
                            if(px != null){
                                px.pre = nNode;
                            }
                            x.pre = x.pre.keys.getLast().next;
                            x.next = x.next.keys.getFirst().pre;
                            nNode.keys = x.pre.keys;
                            nNode.keys.add(x);
                            nNode.keys.addAll(x.next.keys);
                            tc = nNode;
                        }
                    }
                    break;
                }else if(c < 0){
                    tc = x.next;
                    break;
                }
            }

            assert x != null;

            Node<T> tcp = null;
            Node<T> tcn = px == null ? null : px.next;
            if(tc == null){
                tc = x.pre;
            }else{
                tcp = x.pre;
            }

            if(tc.keys.size() < DEGREE){
                if(tcp != null && tcp.keys.size() >= DEGREE){
                    Key<T> last = tcp.keys.getLast();
                    tcp.keys.removeLast();

                    Key<T> nk = new Key<>();
                    nk.data = x.data;
                    nk.next = tc.keys.getFirst().pre;
                    nk.pre = last.next;

                    x.data = last.data;

                    tc.keys.addFirst(nk);
                }else if(tcn != null && tcn.keys.size() >= DEGREE){
                    Key<T> first = tcn.keys.getFirst();
                    tcn.keys.removeFirst();

                    Key<T> nk = new Key<>();
                    nk.data = x.data;
                    nk.pre = tc.keys.getLast().next;
                    nk.next = first.pre;

                    x.data = first.data;

                    tc.keys.addLast(nk);
                }else{
                    
                }
            }
            cursor = tc;
        }
    }

    /**
     * 获取该节点下最大key
     * @param node
     * @param <T>
     * @return
     */
    private static <T> Key<T> maxKey(Node<T> node){
        Node<T> cursor = node;
        while(!cursor.leaf){
            cursor = cursor.keys.getLast().next;
        }
        return cursor.keys.getLast();
    }

    /**
     * 获取该节点最小值key
     * @param node
     * @param <T>
     * @return
     */
    private static <T> Key<T> minKey(Node<T> node){
        Node<T> cursor = node;
        while(!cursor.leaf){
            cursor = cursor.keys.getLast().next;
        }
        return cursor.keys.getFirst();
    }

    /**
     * 向b树中插入数据
     * @param data 待插入的数据
     * @return 如果b树中已经存在与其相等的数据, 则返回false, 表示插入失败
     */
    public boolean insert(T data){
        if(this.root.keys.size() > (DEGREE << 1)){  // 说明根节点已满
            /*
             * 根节点的分裂是唯一可以引起树高度变化的操作
             * 根节点分裂会新建一个根节点作为原根节点的父节点, 然后分裂原根节点
             */
            Node<T> nr = new Node<>();
            nr.leaf = false;

            Iterator<Key<T>> itr = this.root.keys.iterator();
            Node<T> newLeft = new Node<>();
            Node<T> newRight = new Node<>();
            newLeft.leaf = newRight.leaf = this.root.leaf;
            int i = 0;
            while(itr.hasNext()  && i < DEGREE){
                newLeft.keys.add(itr.next());
                i++;
            }
            Key<T> midKey = itr.next();
            while(itr.hasNext()){
                newRight.keys.add(itr.next());
            }
            midKey.pre = newLeft;
            midKey.next = newRight;
            nr.keys.add(midKey);
            this.root = nr;
        }

        // 此时根节点一定是非满的
        Node<T> cursor = this.root;
        while(true){
            Iterator<Key<T>> itr = cursor.keys.descendingIterator();
            int m = 0;  // 记录待插入节点后面的节点的个数
            Key<T> k = null;
            while(itr.hasNext()){
                k = itr.next();
                int mark = k.data.compareTo(data);
                if(mark == 0){
                    return false;
                }else if(mark < 0){
                    break;
                }
                m++;
            }
            if(cursor.leaf){    // 如果是叶子节点
                Key<T> nk = new Key<>();
                nk.data = data;
                cursor.keys.add(cursor.keys.size() - m, nk);
                break;
            }else{
                assert k != null;
                Node<T> tc = k.next;
                if(tc.keys.size() > (DEGREE << 1)){
                    Key<T> nK = split(cursor, cursor.keys.size() - m);
                    int c = nK.data.compareTo(data);
                    if(c == 0){
                        return false;
                    }else if(c > 0){
                        tc = nK.pre;
                    }else {
                        tc = nK.next;
                    }
                }
                cursor = tc;
            }
        }
        return true;
    }

    /**
     * 页分裂
     * @param pNode 待分裂页的上级节点
     * @param pos pNode中待分裂页的key的位置, 该key.next即为要分裂的页
     */
    private Key<T> split(Node<T> pNode, int pos){
        ListIterator<Key<T>> itr = pNode.keys.listIterator();
        Key<T> sk = null;
        while(itr.hasNext() && pos > 0){
            sk = itr.next();
            pos--;
        }

        Node<T> splitNode = null;
        if(sk == null){
            splitNode = pNode.keys.get(0).pre;
        }else{
            splitNode = sk.next;
        }

        Node<T> newLeft = new Node<>();
        Node<T> newRight = new Node<>();
        newRight.leaf = newLeft.leaf = splitNode.leaf;
        Iterator<Key<T>> sItr = splitNode.keys.iterator();
        int i = 0;
        while (sItr.hasNext() && i < DEGREE){
            newLeft.keys.add(sItr.next());
            i++;
        }

        Key<T> midKey = sItr.next();

        while(sItr.hasNext()){
            newRight.keys.add(sItr.next());
        }

        midKey.pre = newLeft;
        midKey.next = newRight;

        if(sk != null){
            sk.next = midKey.pre;
        }

        itr.add(midKey);

        if(itr.hasNext()){
            itr.next().pre = midKey.next;
        }

        return midKey;
    }

    /**
     * 从B树中查找元素
     * @param k 待查找的元素
     * @return 查找结果, 如果返回null, 则表示没有找到
     */
    public T search(T k){
        Node<T> cursor = this.root;
        while(cursor != null){
            Iterator<Key<T>> itr = cursor.keys.descendingIterator();
            cursor = null;
            while(itr.hasNext()){
                Key<T> key = itr.next();
                int c = key.data.compareTo(k);
                if(c == 0){
                    return key.data;
                }else if(c < 0){
                    cursor = key.next;
                    break;
                }else{
                    cursor = key.pre;
                }
            }
        }
        return null;
    }

    private static class Key<T>{
        private Node<T> pre;
        private Node<T> next;
        private T data;
    }

    private static class Node<T>{
        private boolean leaf = true;
        private LinkedList<Key<T>> keys = new LinkedList<>();
    }


}
