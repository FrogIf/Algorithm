package sch.frog.algorithm;

/**
 * 两种矩阵乘法算法:
 *   1. 普通矩阵乘法
 *   2. Strassen算法
 */
public class MatrixStrassen {

    public static void main(String[] args){
        int[][] A = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };  // 3 * 4

        int[][] B = new int[][]{
                {1, 2, 3},
                {5, 6, 7},
                {9, 10, 11},
                {12, 13, 14}
        };  // 4 * 3

        show(commonMatrixMultiple(A, B));
        System.out.println("------------------");
        show(strassenMatrixMultiple(A, B));
    }

    private static void show(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(j != 0){ System.out.print(","); }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Strassen 矩阵乘法
     * @param A
     * @param B
     * @return
     */
    public static int[][] strassenMatrixMultiple(int[][] A, int[][] B){
        int ah = A.length;
        int aw = A[0].length;
        int bh = B.length;
        int bw = B[0].length;

        int amw = ah > aw ? ah : aw;
        int bmw = bh > bw ? bh : bw;
        int w = minWidth(amw > bmw ? amw : bmw);

        int[][] result = strassenMatrixMultiple(fixMatrix(A, w), 0, 0, fixMatrix(B, w), 0, 0, w);

        int[][] realResult = new int[ah][bw];
        for(int i = 0; i < ah; i++){
            for(int j = 0; j < bw; j++){
                realResult[i][j] = result[i][j];
            }
        }
        return realResult;
    }

    private static int[][] strassenMatrixMultiple(int[][] A, int ax, int ay, int[][] B, int bx, int by, int w){
        if(w == 1){
            int[][] result = new int[1][1];
            result[0][0] = A[ax][ay] * B[bx][by];
            return result;
        }else{
            int halfWidth = w / 2;

            int ax11 = ax;
            int ay11 = ay;

            int ax22 = ax + halfWidth;
            int ay22 = ay + halfWidth;

            int ax12 = ax11;
            int ay12 = ay22;

            int ax21 = ax22;
            int ay21 = ay11;

            int bx11 = bx;
            int by11 = by;

            int bx22 = bx + halfWidth;
            int by22 = by + halfWidth;

            int bx12 = bx11;
            int by12 = by22;

            int bx21 = bx22;
            int by21 = by11;

            int[][] S1 = sub(B, bx12, by12, B, bx22, by22, halfWidth);
            int[][] S2 = add(A, ax11, ay11, A, ax12, ay12, halfWidth);
            int[][] S3 = add(A, ax21, ay21, A, ax22, ay22, halfWidth);
            int[][] S4 = sub(B, bx21, by21, B, bx11, by11, halfWidth);
            int[][] S5 = add(A, ax11, ay11, A, ax22, ay22, halfWidth);
            int[][] S6 = add(B, bx11, by11, B, bx22, by22, halfWidth);
            int[][] S7 = sub(A, ax12, ay12, A, ax22, ay22, halfWidth);
            int[][] S8 = add(B, bx21, by21, B, bx22, by22, halfWidth);
            int[][] S9 = sub(A, ax11, ay11, A, ax21, ay21, halfWidth);
            int[][] S10 = add(B, bx11, by11, B, bx12, by12, halfWidth);

            int[][] P1 = strassenMatrixMultiple(A, ax11, ay11, S1, 0, 0, halfWidth);
            int[][] P2 = strassenMatrixMultiple(S2, 0, 0, B, bx22, by22, halfWidth);
            int[][] P3 = strassenMatrixMultiple(S3, 0, 0, B, bx11, by11, halfWidth);
            int[][] P5 = strassenMatrixMultiple(S5, 0, 0, S6, 0, 0, halfWidth);
            int[][] P6 = strassenMatrixMultiple(S7, 0, 0, S8, 0, 0, halfWidth);
            int[][] P7 = strassenMatrixMultiple(S9, 0, 0, S10, 0, 0, halfWidth);
            int[][] P4 = strassenMatrixMultiple(A, ax22, ay22, S4, 0, 0, halfWidth);

            int[][] C11 = add(sub(add(P5, P4), P2), P6);
            int[][] C12 = add(P1, P2);
            int[][] C21 = add(P3, P4);
            int[][] C22 = sub(sub(add(P5, P1), P3), P7);


            int[][] result = new int[w][w];

            copyTo(C11, result, 0, 0);
            copyTo(C12, result, 0, halfWidth);
            copyTo(C21, result, halfWidth, 0);
            copyTo(C22, result, halfWidth, halfWidth);

            return result;
        }
    }

    private static void copyTo(int[][] source, int[][] aim, int x, int y){
        for(int i = 0; i < source.length; i++){
            for(int j = 0; j < source.length; j++){
                aim[x + i][y + j] = source[i][j];
            }
        }
    }

    private static int[][] fixMatrix(int[][] matrix, int width){
        int h = matrix.length;
        int w = matrix[0].length;

        int[][] fixMatrix;
        if(h != width || w != width){
            fixMatrix = new int[width][width];
            for(int i = 0; i < h; i++){
                for(int j = 0; j < w; j++){
                    fixMatrix[i][j] = matrix[i][j];
                }
            }
        }else{
            fixMatrix = matrix;
        }

        return fixMatrix;
    }


    private static int[][] add(int[][] A, int[][] B){
        return add(A, 0, 0, B, 0, 0, A.length);
    }

    private static int[][] sub(int[][] A, int[][] B){
        return sub(A, 0, 0, B, 0, 0, A.length);
    }

    private static int[][] add(int[][] matrixA, int ax, int ay, int[][] matrixB, int bx, int by, int width){
        int[][] result = new int[width][width];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                result[i][j] = matrixA[i + ax][j + ay] + matrixB[i + bx][j + by];
            }
        }
        return result;
    }

    private static int[][] sub(int[][] matrixA, int ax, int ay, int[][] matrixB, int bx, int by, int width){
        int[][] result = new int[width][width];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < width; j++){
                result[i][j] = matrixA[i + ax][j + ay] - matrixB[i + bx][j + by];
            }
        }
        return result;
    }

    private static int minWidth(int w){
        int rw = 1;
        w = w - 1;
        while(w != 0){
            w = w >> 1;
            rw *= 2;
        }
        return rw;
    }


    /**
     * 平凡矩阵乘法
     * @param A
     * @param B
     * @return
     */
    public static int[][] commonMatrixMultiple(int[][] A, int[][] B){
        int h = A.length;
        int w = B[0].length;
        int m = B.length;

        int[][] result = new int[h][w];

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                int s = 0;
                for(int k = 0; k < m; k++){
                    s += A[i][k] * B[k][j];
                }
                result[i][j] = s;
            }
        }

        return result;
    }

}
