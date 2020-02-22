package sch.frog.leetcode;

/**
 * 解数独
 */
public class Sudoku {
    public static void main(String[] args){
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        sudoku(board);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    // n & (n - 1)消去最右边的1
    private static void sudoku(char[][] board){
        Blank[][] record = new Blank[9][9];  // 记录待填充的空白块

        int[][] blocks = new int[3][3]; // 记录块中已存在的数
        int[] rows = new int[9];    // 记录行中已经存在的数
        int[] cols = new int[9];    // 记录列中已经存在的数

        // 转换信息
        for(int r = 0; r < 9; r++){ // 遍历行
            for(int c = 0; c < 9; c++){ // 遍历列
                char ch = board[r][c];
                if(ch == '.'){
                    record[r][c] = new Blank();
                }else{
                    int v = (1 << ch - '1');
                    rows[r] |= v;
                    cols[c] |= v;
                    blocks[r / 3][c / 3] |= v;
                }
            }
        }

        int full = 0x1ff;

        boolean hasBlank = false;
        // 设置可选值
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                Blank blank = record[r][c];
                if(blank != null){
                    hasBlank = true;
                    blank.guess = blank.option = (rows[r] | cols[c] | blocks[r / 3][c / 3]) ^ full;
                }
            }
        }


        // 试数
        while (hasBlank){
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    Blank start = record[i][j];
                    if(start != null){
                        hasBlank = true;
                        int option = start.option;
                        int res = 0;
                        int resn = 0;
                        for(int n = 0; n < 9; n++){
                            int v = 1 << n;
                            if((option & v) > 0){  // 说明是可选值
                                rows[i] |= v;
                                cols[j] |= v;
                                blocks[i / 3][j / 3] |= v;
                                boolean suc = true;
                                mark : for(int r = 0; r < 9; r++){
                                    for(int c = 0; c < 9; c++){
                                        Blank blank = record[r][c];
                                        if(blank != null){
                                            if(((rows[r] | cols[c] | blocks[r / 3][c / 3]) ^ full) == 0){
                                                suc = false;
                                                break mark;
                                            }
                                        }
                                    }
                                }
                                if(suc){
                                    res |= v;
                                    resn = n;
                                }
                            }
                        }
                        if((res & (res - 1)) == 0){
                            board[i][j] = (char)('1' + resn);
                            hasBlank = false;
                        }
                    }
                }
            }
        }
    }

    private static class Blank {
        private int option; // 可选值
        private int guess;  // 估计值
    }

}
