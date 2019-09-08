package sch.frog.algorithm.extend;

public class Fibonacci {

    public static void main(String[] args){
        System.out.println(simpleRecursion(30));
        System.out.println(simpleLoop(30));
        System.out.println(matrix(30));
    }

    /*
     * O(lgn)
     * 涉及知识: 矩阵快速幂
     */
    private static int matrix(int n){
        return fibonacciVector(n)[1];
    }

    private static int[] fibonacciVector(int n){
        if(n == 0){
            return new int[]{ 1, 0 };
        }
        int[] v = fibonacciVector(n / 2);
        int fm1 = v[0];
        int fm = v[1];

        if(n % 2 == 1){
            return new int[]{
                    fm1 * (fm1 + 2 * fm),
                    fm1 * fm1 + fm * fm
            };
        }else{
            return new int[]{
                    fm1 * fm1 + fm * fm,
                    fm * (2 * fm1 - fm)
            };
        }
    }

    /*
    * O(n)
    * */
    public static int simpleLoop(int n){
        if(n == 1){ return 1; }
        else if(n == 0){ return 0; }

        int a = 0, b = 1;
        for(int i = 0, j = 1; j < n; i++, j++){
            int t = b;
            b = a + b;
            a = t;
        }

        return b;
    }

    /*
     * O(2^n)
     */
    private static int simpleRecursion(int n){
        if(n == 1){
            return 1;
        }else if(n == 0){
            return 0;
        }else{
            return simpleRecursion(n - 1) + simpleRecursion(n - 2);
        }
    }

}
