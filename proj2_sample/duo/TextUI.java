package duo;

import java.util.Scanner;

import static duo.Color.*;

/** A simple textual implementation of the user interface.
 *  @author Zack Mayeda
 */
class TextUI implements UI {

    /** The scanner that reads the standard input to the game, which
     *  should be moves entered by a human player.
     */
    private Scanner inputInfo = new Scanner(System.in);

    /** The integer 13. */
    public static final int TTEEN = 13;

    @Override
    public String getMove(Color color, int numMoves, String prevMove) {
        while (inputInfo.hasNext()) {
            String inputLine = inputInfo.next();
            return inputLine;
        }
        return null;
    }

    @Override
    public void reportWinner(int orangeScore, int violetScore) {
        if (orangeScore > violetScore) {
            report("Orange wins (" + orangeScore + "-" + violetScore + ")");
        }
        if (violetScore > orangeScore) {
            report("Violet wins (" + orangeScore + "-" + violetScore + ")");
        }
        if (violetScore == orangeScore) {
            report("Tie game (" + orangeScore + "-" + violetScore + ")");
        }
    }

    @Override
    public void reportBoard(Board board) {
        Color[][] gameBoard = board.getBoard();
        for (int col = TTEEN; col >= 0; col--) {
            System.out.print("  ");
            for (int row = 0; row <= TTEEN; row++) {
                if (gameBoard[row][col] == EMPTY) {
                    System.out.print("-");
                }
                if (gameBoard[row][col] == VIOLET) {
                    System.out.print("V");
                }
                if (gameBoard[row][col] == ORANGE) {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void reportBoardStandard(Board board) {
        System.out.println("===");
        reportBoard(board);
        System.out.println("===");
    }

    @Override
    public void reportMove(Color color, int numMoves, String move) {
        System.out.println("Play #" + numMoves + ": " + color
                           + " played " + move);
    }

    @Override
    public void reportError(String message) {
        System.err.println(message);
    }

    @Override
    public void report(String message) {
        System.out.println(message);
    }

}
