package sch.frog.leetcode;

import java.util.*;

public class Parentheses {

    public static void main(String[] args){

    }

    private static List<String> generateParenthesis(int n){
        List<Set<String>> res = new ArrayList<>(n);
        Set<String> focus = new HashSet<>();
        res.add(focus);
        focus.add("()");
        for(int i = 2; i <= n; i++){
            focus = new HashSet<>();
            res.add(focus);
            for(int a = i - 1; a >= (i >> 1); a--){
                int b = i - a;
                Set<String> sa = res.get(a - 1);
                if(b == 1){
                    for (String s : sa) {
                        focus.add('(' + s + ')');
                        focus.add("()" + s);
                        focus.add(s + "()");
                    }
                }else{
                    Set<String> sb = res.get(b - 1);
                    for (String s1 : sa) {
                        for (String s2 : sb) {
                            focus.add(s1 + s2);
                            focus.add(s2 + s1);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(res.get(n - 1));
    }

}
