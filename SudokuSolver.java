import java.util.ArrayList;

/**
 * Created by Megan on 10/8/16.
 */
public class SudokuSolver {


    public static SudokuBoard dfs(SudokuBoard board) {

        if (board.isSolution()) return board;

        if (board.isKnownIncoherent()) return null;

        boolean reduced;
        do {
            reduced = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    ArrayList<Integer> canUse = board.getPossibilitiesAt(i, j);
                    if (canUse != null && canUse.size() == 1) {
                        reduced = true;
                        board = board.getBoardWithValue(i, j, canUse.get(0));
                    }
                }
            }
        } while (reduced); //while the spot has one possible entry

        if (board.isSolution()) return board;
        if (board.isKnownIncoherent()) return null;


            for (int size = 2; size < 10; size++) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        ArrayList<Integer> canUse = board.getPossibilitiesAt(i, j);
                        if (canUse != null && canUse.size() == size) {
                            for (int entry : canUse) {
                                SudokuBoard newOne = board.getBoardWithValue(i, j, entry);
                                SudokuBoard kept = dfs(newOne);
                                if (kept != null) return kept;
                            }
                        }

                    }

                }

            }

        return null;

    }

    public static void main(String[] args) {

        SudokuBoard board = SudokuBoard.getFromStdIn();
        System.out.println(dfs(board));

    }

}
