package kr.co.kware.festival.sample;

/**
 * Created by xchans on 2016. 2. 22..
 */
public class Sample1 {
    public static void main(String[] args) {
        NQueens1 nQueens;
        for (int i = 1; i < 14; i++) {
            nQueens = new NQueens1(i);
            int results = nQueens.callPlaceNQueens();

            System.out.printf("n = %02d, solution count is %d.\n", i, results);
        }
    }
}

class NQueens1 {

    private int[] x;
    private int result;

    public NQueens1(int n) {
        x = new int[n];
        result = 0;
    }

    private boolean canPlaceQueen(int r, int c) {
        /**
         * Returns TRUE if a queen can be placed in row r and column c.
         * Otherwise it returns FALSE. x[] is a global array whose first (r-1)
         * values have been set.
         */
        for (int i = 0; i < r; i++) {
            if (x[i] == c || (i - r) == (x[i] - c) ||(i - r) == (c - x[i])) {
                return false;
            }
        }
        return true;
    }

    private void printQueens(int[] x) {
        int N = x.length;
        for (int aX : x) {
            for (int j = 0; j < N; j++) {
                if (aX == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void placeNQueens(int r, int n) {
        /**
         * Using backtracking this method prints all possible placements of n
         * queens on an n x n chessboard so that they are non-attacking.
         */
        for (int c = 0; c < n; c++) {
            if (canPlaceQueen(r, c)) {
                x[r] = c;
                if (r == n - 1) {
                    //printQueens(x);
                    result++;
                } else {
                    placeNQueens(r + 1, n);
                }
            }
        }
    }

    public int callPlaceNQueens() {
        placeNQueens(0, x.length);

        return result;
    }
}
