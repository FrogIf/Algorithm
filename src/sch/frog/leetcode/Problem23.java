package sch.frog.leetcode;

/**
 * merge k sorted lists
 */
public class Problem23 {

    public static void main(String[] args){
        Problem23 p = new Problem23();
        // [[-10,-9,-9,-3,-1,-1,0],[-5],[4],[-8],[],[-9,-6,-5,-4,-2,2,3],[-3,-3,-2,-1,0]]
        // [-10,-9,-9,-9,-8,-6,-5,-5,-4,-3,-3,-3,-2,-2,-1,-1,-1,0,0,2,3,4]
        int[][] arr = {{-10,-9,-9,-3,-1,-1,0},{-5},{4},{-8},{},{-9,-6,-5,-4,-2,2,3},{-3,-3,-2,-1,0}};
//        int[][] arr = {
//                {1}
//        };
//        int[][] arr = {
//                {1, 4, 5},
//                {1, 3, 4},
//                {2, 6}
//        };

        ListNode[] input = new ListNode[arr.length];
        for(int i = 0; i < arr.length; i++){
            int[] t = arr[i];
            if(t.length == 0){
                input[i] = null;
            }else{
                ListNode cursor = input[i] = p.new ListNode(t[0]);
                for (int j = 1; j < t.length; j++) {
                    cursor.next = p.new ListNode(t[j]);
                    cursor = cursor.next;
                }
            }
        }

        ListNode listNode = p.new Solution().mergeKLists(input);
        ListNode cursor = listNode;
        while(cursor != null){
            System.out.print(cursor.val + ",");
            cursor = cursor.next;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    class Solution {

        public ListNode mergeKLists(ListNode[] lists) {
            if(lists != null && lists.length > 0){
                ListNode[] nodes = new ListNode[lists.length];
                int j = 0;
                for (ListNode list : lists) {
                    if (list != null) {
                        nodes[j++] = list;
                    }
                }
                if(j == 0){ return null; }

                quickSort(nodes, 0, j - 1);

                int[] start = {-1, 0};
                popQueue(nodes, start, j);
                ListNode result = new ListNode(start[0]);
                ListNode cursor = result;
                while(start[1] < j){
                    popQueue(nodes, start, j);
                    cursor.next = new ListNode(start[0]);
                    cursor = cursor.next;
                }
                return result;
            }
            return null;
        }

        private void popQueue(ListNode[] queue, int[] delivery, int len){
            int i = delivery[1];
            ListNode node = null;
            for(; i < len; i++){
                if((node = queue[i]) != null){
                    break;
                }
            }

            if(node != null){
                ListNode newNode = node.next;

                if(newNode != null){
                    int val = newNode.val;
                    for(++i; i < len; i++){
                        ListNode n = queue[i];
                        if(n.val >= val){
                            break;
                        }
                        queue[i - 1] = n;
                    }
                    queue[i - 1] = newNode;
                }else{
                    delivery[1]++;
                }

                delivery[0] = node.val;
            }
        }

        private void quickSort(ListNode[] nodes, int start, int end){
            if(start < end){
                int s = start;
                int e = end;
                ListNode n = nodes[s];
                int pivot = n.val;
                while(s < e){
                    while(s < e && nodes[e].val > pivot){
                        e--;
                    }
                    if(s < e){
                        nodes[s++] = nodes[e];
                    }
                    while(s < e && nodes[s].val < pivot){
                        s++;
                    }
                    if(s < e){
                        nodes[e--] = nodes[s];
                    }
                }

                nodes[s] = n;
                quickSort(nodes, start, s - 1);
                quickSort(nodes, s + 1, end);
            }
        }

    }

}
