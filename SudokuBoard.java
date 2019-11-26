import java.util.ArrayList;
import java.util.Scanner;

/**
 * Encodes a completely or partially filled out Sudoku board
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 1.0
 */

public class SudokuBoard {

    int[][] board = new int[9][9];
    static final int BOARD_SIZE = 9;

    /**
     * Reads a Sudoku board from StdIn
     *
     * @return a <code>SudokuBoard</code>
     */
    public static SudokuBoard getFromStdIn() {

        Scanner scanner = new Scanner(System.in);

        SudokuBoard board = new SudokuBoard();

        for (int i = 0; i < BOARD_SIZE; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < BOARD_SIZE; j++) {
                int digit = Character.digit(line.charAt(j), 10);
                if (digit != 0) board.setValue(i, j, digit);
            }

        }

        return board;
    }
    @Override
    public String toString() {
        StringBuilder makeString = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE ; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                makeString.append(board[i][j]);
            }
            makeString.append("\n");
        }

        return makeString.toString();
            }

    //from Prof. Knapp
    private void setValue(int cancelRow, int cancelColumn, int digit) {
        if (digit == 0) throw new IllegalArgumentException();
        board[cancelRow][cancelColumn] = digit;
    }


    /**
     * @return a list of digits at row <code>row</code> and column
     * <code>col</code> which do not violate any of the board's known
     * constraints. If the entry has been set to a definite value, the
     * returned list will be <code>null</code>. (If there is no
     * possible entry, the list will be <em>empty</em>.
     */
    public ArrayList<Integer> getPossibilitiesAt(int row, int col) {

        if(board[row][col] != 0) return null;

        ArrayList<Integer> badInts = new ArrayList<>();
        ArrayList<Integer> goodInts = new ArrayList<>();

        // check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[row][i] > 0) {
                badInts.add(board[row][i]);
            }
        }

        // check columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][col] > 0) {
                badInts.add(board[i][col]);
            }
        }

        int subGridRow = ((row/3)*3);
        int subGridCol = ((col/3)*3);

        // check 3 X 3 squares
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 ; j++) {
                if (board[subGridRow + i][subGridCol + j] > 0) {
                    badInts.add(board[subGridRow + i][subGridCol + j]);
                }
            }
        }

        for (int i = 1; i <= BOARD_SIZE; i++) {
            if (!badInts.contains(i)) {
                goodInts.add(i);
            }
        }
        return goodInts;
    }

    /**
     * Describe <code>getBoardWithValue</code> method here.
     *
     * @param row row of entry
     * @param col column of entry
     * @param val value to set
     * @return a new <code>SudokuBoard</code> which has the digit
     * <code>val</code> in row <code>row</code> and column <code>col</code>
     * @throws IllegalArgumentException if <code>row</code> or
     *                                  <code>col</code> are not valid
     *                                  rows or columns or if
     *                                  <code>val</code> is not an
     *                                  allowed value in row
     *                                  <code>row</code> and column
     *                                  <code>col</code>, of if this
     *                                  entry is already set
     */
    public SudokuBoard getBoardWithValue(int row, int col, int val) {

        if (board[row][col] != 0) throw new IllegalArgumentException("There is already an entry there");

        if (row >= 10 || row < 0) throw new IllegalArgumentException("Illegal row value");

        if (col >= 10 || col < 0) throw new IllegalArgumentException("Illegal column value");

        ArrayList<Integer> possibilities = this.getPossibilitiesAt(row,col);

        if (!possibilities.contains(val)) throw new IllegalArgumentException("That entry can't go there");

        SudokuBoard copy = new SudokuBoard();

        //copies board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                copy.board[i][j] = this.board[i][j];
            }

            copy.board[row][col] = val;
        }

        return copy;
    }

    /**
     * @return <code>true</code> if this board is a completely filled out, valid
     * solution, <code>false</code> otherwise
     */
    public boolean isSolution() {

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.board[i][j] == 0 ) return false;
            }
        }
        return true;
    }

    /**
     * @return <code>true</code> if there is at least one entry for which there
     * is no possible allowed digit, <code>false</code> otherwise
     */
    public boolean isKnownIncoherent() {

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ArrayList<Integer> possibilities = this.getPossibilitiesAt(i,j);
                if (possibilities != null && possibilities.size() <= 0 ) return true;
            }
        }
        return false;
    }

}


