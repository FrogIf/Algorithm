package sch.frog.clrs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * B树
 * @param <T>
 */
public class BTree<T extends Comparable<T>> {

    /*
     * 1. 每一个叶节点具有相同的深度
     * 2. 每个叶节点包含的key数目有上界和下界:
     *      (1) 上界 : 2 * degree - 1
     *      (2) 下界(根节点除外) : degree - 1
     */

    private static final int DEGREE = 2;

    private Page<T> root = new Page<>();

    public boolean delete(T k){
        Page<T> cursor = this.root;
        while(!cursor.keys.isEmpty()){
            Iterator<Key<T>> itr = cursor.keys.descendingIterator();
            Page<T> tc = null;  // k所处的节点, 或者k所处的节点的一棵子树的根
            Key<T> x = null;    // 待删除节点
            Key<T> px = null;   // 待删除节点的上一个节点(由于是倒序遍历, 实际上是下一个节点)
            while(itr.hasNext()){   // 遍历一个页
                px = x;
                x = itr.next();
                int c = x.data.compareTo(k);
                if(c == 0){ // 找到待删除节点
                    if(cursor.leaf){ // 1. 如果是叶节点, 直接删除
                        itr.remove();
                    }else{  // 2. 待删除节点是内部节点
                        if(x.pre.keys.size() >= DEGREE){    // 2a. 找到前驱节点补位, 并删除前驱节点
                            Key<T> tKey = maxKey(x.pre);
                            this.delete(tKey.data);
                            x.data = tKey.data;
                        }else if(x.next.keys.size() >= DEGREE){ // 2b. 找到后继节点补位, 并删除后继节点
                            Key<T> tKey = minKey(x.next);
                            this.delete(tKey.data);
                            x.data = tKey.data;
                        }else{  // 2c. 没有合适的补位页, 将该节点前后两子页合并
                            Page<T> nPage = new Page<>();   // 合并后的子页

                            // 将子叶放入正确的位置
                            if(itr.hasNext()){
                                itr.next().next = nPage;
                            }
                            if(px != null){
                                px.pre = nPage;
                            }

                            itr.remove();   // 删除x

                            // 构建结构
                            x.pre = x.pre.keys.getLast().next;
                            x.next = x.next.keys.getFirst().pre;
                            nPage.keys = x.pre.keys;
                            nPage.leaf = x.pre.leaf;
                            nPage.keys.add(x);
                            nPage.keys.addAll(x.next.keys);
                            tc = nPage;

                            if(cursor.keys.isEmpty()){  // 树高度降低
                                this.root = nPage;
                            }
                            break;  // 这里没有直接删掉x, 交由下次循环来删
                        }
                    }
                    return true;
                }else if(c < 0){    // 说明待删除节点在树的下一层
                    tc = x.next;
                    if(tc == null){ // 当前页是叶节点, 说明没有待删除的元素
                        return false;
                    }else{
                        break;
                    }
                }
            }

            // 3. k在tc为根的子树中
            assert x != null;

            Page<T> tcp = null; // tc的左兄弟页
            Page<T> tcn = px == null ? null : px.next;  // tc的右兄弟页
            if(tc == null){ // 只有一种引起tc==null的情况, x是页的起始key, 这时tcp一定为null
                tc = x.pre;
                tcn = x.next;
            }else{
                tcp = x.pre;
            }

            if(tc.keys.size() < DEGREE){    // 防止删除导致页内key个数不符合B-Tree的性质
                if(tcp != null && tcp.keys.size() >= DEGREE){   // 3a
                    Key<T> last = tcp.keys.getLast();
                    tcp.keys.removeLast();

                    Key<T> nk = new Key<>();
                    nk.data = x.data;
                    nk.next = tc.keys.getFirst().pre;
                    nk.pre = last.next;

                    x.data = last.data;

                    tc.keys.addFirst(nk);
                }else if(tcn != null && tcn.keys.size() >= DEGREE){ // 3a
                    Key<T> first = tcn.keys.getFirst();
                    tcn.keys.removeFirst();

                    Key<T> nk = new Key<>();
                    nk.data = x.data;
                    nk.pre = tc.keys.getLast().next;
                    nk.next = first.pre;

                    x.data = first.data;

                    tc.keys.addLast(nk);
                }else{  // 3b, tc的两个兄弟节点都不能将key调给tc, 则将tc与其中一个兄弟合并
                    Page<T> nPage = new Page<>();   // 合并后的页
                    nPage.leaf = tc.leaf;

                    itr.remove();   // 删除x, 由于BTree性质, 与上面的itr.remove()不会出现重复调用
                    if(cursor.keys.isEmpty()){
                        this.root = nPage;
                    }

                    if(tcp != null){
                        if(itr.hasNext()){
                            Key<T> key = itr.next();
                            key.next = nPage;
                        }
                        if(px != null){
                            px.pre = nPage;
                        }

                        x.next = tc.keys.getFirst().pre;
                        x.pre = tcp.keys.getLast().next;

                        nPage.keys.addAll(tcp.keys);
                        nPage.keys.add(x);
                        nPage.keys.addAll(tc.keys);
                    }else{  // 此时, x一定是页的起始key
                        if(px != null){
                            px.pre = nPage;
                        }

                        x.pre = tc.keys.getLast().next;
                        assert tcn != null;
                        x.next = tcn.keys.getFirst().pre;

                        nPage.keys.addAll(tc.keys);
                        nPage.keys.add(x);
                        nPage.keys.addAll(tcn.keys);

                        tc = nPage;
                    }
                }
            }

            cursor = tc;
        }
        return false;
    }

    /**
     * 获取该节点下最大key
     * @param page
     * @param <T>
     * @return
     */
    private static <T> Key<T> maxKey(Page<T> page){
        Page<T> cursor = page;
        while(!cursor.leaf){
            cursor = cursor.keys.getLast().next;
        }
        return cursor.keys.getLast();
    }

    /**
     * 获取该节点最小值key
     * @param page
     * @param <T>
     * @return
     */
    private static <T> Key<T> minKey(Page<T> page){
        Page<T> cursor = page;
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
            Page<T> nr = new Page<>();
            nr.leaf = false;

            Iterator<Key<T>> itr = this.root.keys.iterator();
            Page<T> newLeft = new Page<>();
            Page<T> newRight = new Page<>();
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
        Page<T> cursor = this.root;
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
            if(cursor.leaf){    // 如果是叶子节点, 显然所有插入操作到时在叶子节点上进行的
                Key<T> nk = new Key<>();
                nk.data = data;
                cursor.keys.add(cursor.keys.size() - m, nk);
                break;
            }else{
                assert k != null;
                Page<T> tc = k.next;
                if(tc.keys.size() == (DEGREE << 1) - 1){
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
     * @param pPage 待分裂页的上级节点
     * @param pos pPage中待分裂页的key的位置, 该key.next即为要分裂的页
     */
    private Key<T> split(Page<T> pPage, int pos){
        ListIterator<Key<T>> itr = pPage.keys.listIterator();
        Key<T> sk = null;
        while(itr.hasNext() && pos > 0){
            sk = itr.next();
            pos--;
        }

        Page<T> splitPage = null;
        if(sk == null){
            splitPage = pPage.keys.get(0).pre;
        }else{
            splitPage = sk.next;
        }

        Page<T> newLeft = new Page<>();
        Page<T> newRight = new Page<>();
        newRight.leaf = newLeft.leaf = splitPage.leaf;
        Iterator<Key<T>> sItr = splitPage.keys.iterator();
        int i = 0;
        while (sItr.hasNext() && i < DEGREE - 1){   // 当size == degree - 1时, 跳出
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
        Page<T> cursor = this.root;
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
        private Page<T> pre;
        private Page<T> next;
        private T data;

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private static class Page<T>{
        private boolean leaf = true;
        private LinkedList<Key<T>> keys = new LinkedList<>();
    }


}
