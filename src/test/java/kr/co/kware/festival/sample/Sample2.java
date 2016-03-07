package kr.co.kware.festival.sample;


/**
 * Created by xchans on 2016. 2. 22..
 */
public class Sample2 {
    public static void main(String[] args) {
        NQueens2 nQueens;
        for (int i = 1; i < 14; i++) {
            nQueens = new NQueens2(i);
            int results = nQueens.callPlaceNQueens();

            System.out.printf("n = %02d, solution count is %d.\n", i, results);
        }
    }
}

class NQueens2 {

    private int[] board;
    private int result;
    NQueens2(int n) {
        board = new int[n];
        result = 0;
    }

    public int callPlaceNQueens() {
        placeQueenOnBoard(0);
        return result;
    }

    private void placeQueenOnBoard(int Qi) {
        int n = board.length;
        //base case
        if (Qi == n) {// a valid configuration found.
            //System.out.println(Arrays.toString(board));
            result = board.length;
        } else {
            //try to put the ith Queen (Qi) in all of the columns
            for (int column = 0; column < n; column++) {
                if (isSafePlace(column, Qi)) {
                    board[Qi] = column;
                    //then place remaining queens.
                    placeQueenOnBoard(Qi + 1);
                    /**
                     * backtracking. It is not required in this as we only look previously
                     * placed queens in isSafePlace method and it does not care what values
                     * are available in next positions.*
                     */
                    board[Qi] = -1;
                }
            }
        }
    }

    //check if the column is safe place to put Qi (ith Queen)
    private boolean isSafePlace(int column, int Qi) {

        //check for all previously placed queens
        for (int i = 0; i < Qi; i++) {
            if (board[i] == column) { // the ith Queen(previous) is in same column
                return false;
            }
            //the ith Queen is in diagonal
            //(r1, c1) - (r2, c1). if |r1-r2| == |c1-c2| then they are in diagonal
            if (Math.abs(board[i] - column) == Math.abs(i - Qi)) {
                return false;
            }
        }
        return true;
    }
}
