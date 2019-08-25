package sch.frog.leetcode;

public class RegularExpression {


    public static void main(String[] args){
//        System.out.println(!new Solution().isMatch("aa", "a"));
//        System.out.println(new Solution().isMatch("aa", "a*"));
//        System.out.println(new Solution().isMatch("ab", ".*"));
//        System.out.println(new Solution().isMatch("aab", "c*a*b"));
//        System.out.println(!new Solution().isMatch("mississippi", "mis*is*p*."));
//        System.out.println(new Solution().isMatch("mississippi", "mis*is*ip*."));
//        System.out.println(!new Solution().isMatch("ab", ".*c"));
//        System.out.println(!new Solution().isMatch("aaa", "aaaa"));
//        System.out.println(new Solution().isMatch("aaab", "a*ab"));
//        System.out.println(new Solution().isMatch("aaabab", "a*b*ab"));
//        System.out.println(!new Solution().isMatch("aaba", "ab*a*c*a"));
//        System.out.println(new Solution().isMatch("aaca", "ab*a*c*a"));
//        System.out.println(new Solution().isMatch("a", "ab*"));
//        System.out.println(new Solution().isMatch("a", "a*a"));
//        System.out.println(!new Solution().isMatch("a", ".*..a*"));
//        System.out.println(new Solution().isMatch("bbbba", ".*a*a"));
//        System.out.println(new Solution().isMatch("ab", ".*.."));
//        System.out.println(new Solution().isMatch("ab", "a.*b"));
        System.out.println(new Solution().isMatch("aasdfasdfasdfasdfas", "aasdf.*asdf.*asdf.*asdf.*s"));
    }

    private static class Solution {

        public boolean isMatch(String s, String p) {
            if(p.length() == 0){ return s.length() == 0; }

            char[] pArr = p.toCharArray();
            Node[] rule = new Node[pArr.length + 1];
            int k, end;

            Node tail = null;
            if(pArr.length == 1){
                rule[0] = new Node(pArr[0]);
                k = 1;
            }else{
                if(pArr[1] == '*'){
                    Node node = new Node('*');
                    tail = node.next = new Node(pArr[0]);
                    rule[0] = node;
                    k = 2;
                }else{
                    rule[0] = new Node(pArr[0]);
                    k = 1;
                }
            }
            end = 0;
            for(; k < pArr.length; k++){
                char ch = pArr[k];
                if(k + 1 < pArr.length && pArr[1 + k] == '*'){
                    if(rule[end].ch != '*'){
                        tail = rule[++end] = new Node('*');
                    }
                    tail.next = new Node(ch);
                    tail = tail.next;
                    k++;
                }else{
                    if(rule[end].ch == '*') {
                        Node cursor = rule[end].next;
                        boolean findIt = false;
                        while (cursor != null) {
                            if (cursor.ch == ch || cursor.ch == '.'){
                                findIt = true;
                                break;
                            }
                            cursor = cursor.next;
                        }
                        if(findIt) {
                            if(tail.ch == ch) {
                                tail.m++;
                            }else if(tail.ch != '.'){
                                tail.next = new Node(ch);
                                tail = tail.next;
                            }else{
                                tail.next = new Node(ch);
                                tail = tail.next;
                                tail.m = 1;
                            }
                            continue;
                        }
                    }
                    rule[++end] = new Node(ch);
                }
            }

            char[] sArr = s.toCharArray();
            Node pNode = rule[0];
            int j = 0;
            int c = 0;
            for(int i = 0; i < sArr.length; i++){
                if(pNode == null) return false;
                if(pNode.ch == '*'){
                    Node cursor = pNode.next;
                    boolean findIt = false;
                    while(cursor != null){
                        if(cursor.ch == '.' || cursor.ch == sArr[i]){
                            findIt = true;
                            if(cursor.ch == '.'){
                                Node head = cursor;
                                while(cursor != null){
                                    if(cursor.ch == sArr[i]){
                                        cursor.m--;
                                        break;
                                    }
                                    cursor = cursor.next;
                                }
                                head.m--;
                            }else{
                                cursor.m--;
                            }
                            break;
                        }else{
                            pNode.next = cursor.next;
                        }
                        cursor = cursor.next;
                    }
                    if(findIt){
                        c = 1;
                        continue;
                    }else{
                        if(j + 1 <= end){
                            pNode = rule[++j];
                        }
                    }
                }

                if(pNode.ch == '.' || pNode.ch == sArr[i]){
                    pNode = rule[++j];
                    c = 0;
                }else {
                    return false;
                }
            }

            if(j > end){
                return true;
            }else if(j == end){
                if(rule[end].ch == '*'){
                    Node cursor = rule[end].next;
                    while(cursor != null){
                        if(cursor.m > 0){
                            return false;
                        }
                        cursor = cursor.next;
                    }
                    return true;
                }else {
                    return c == 1;
                }
            }else{
                return false;
            }
        }

        private class Node{
            private char ch;
            private Node next;
            private int m = 0;
            private Node(char ch){
                this.ch = ch;
            }
        }
    }

}
