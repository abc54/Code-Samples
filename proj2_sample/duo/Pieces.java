package duo;

import java.util.HashMap;

import static duo.Color.*;

/** A list of the pieces used in the game.
 *  @author Zack Mayeda
 */
public class Pieces {

    /** The W piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEW = {{0, 0, 1}, {0, 1, 1}, {1, 1, 0}};
    /** The Z piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEZ = {{1, 1, 0}, {0, 1, 0}, {0, 1, 1}};
    /** The I piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEI = {{1}, {1}, {1}, {1}, {1}};
    /** The L piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEL = {{1, 0}, {1, 0}, {1, 0}, {1, 1}};
    /** The L piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECET = {{0, 1, 0}, {0, 1, 0}, {1, 1, 1}};
    /** The U piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEU = {{1, 1}, {1, 0}, {1, 1}};
    /** The X piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEX = {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
    /** The V piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEV = {{0, 0, 1}, {0, 0, 1}, {1, 1, 1}};
    /** The F piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEF = {{0, 1, 1}, {1, 1, 0}, {0, 1, 0}};
    /** The P piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEP = {{1, 1}, {1, 1}, {1, 0}};
    /** The Y piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEY = {{1, 0}, {1, 1}, {1, 0}, {1, 0}};
    /** The N piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECEN = {{1, 0}, {1, 0}, {1, 1}, {0, 1}};
    /** The z piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECESMALLZ = {{1, 1, 0}, {0, 1, 1}};
    /** The i piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECESMALLI = {{1}, {1}, {1}, {1}};
    /** The d piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECESMALLD = {{0, 1}, {0, 1}, {1, 1}};
    /** The s piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECESMALLS = {{1, 1}, {1, 1}};
    /** The t piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECESMALLT = {{0, 1, 0}, {1, 1, 1}};
    /** The 3 piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECE3 = {{1}, {1}, {1}};
    /** The v piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECESMALLV = {{1, 0}, {1, 1}};
    /** The 2 piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECE2 = {{1}, {1}};
    /** The 1 piece, represented in an int[][] of 0's and 1's. */
    private static final int[][] PIECE1 = {{1}};

    /** The HashMap that contains names of pieces as keys and
     * int[][] of 0's and 1's as the values. 0's represent
     * empty spaces and 1's represent colored spaces.
     */
    private static HashMap<String, int[][]> pieceTable =
            new HashMap<String, int[][]>();

    /** Fill the pieceTable with the pieces. */
    static {
        pieceTable.put("W", PIECEW);
        pieceTable.put("Z", PIECEZ);
        pieceTable.put("I", PIECEI);
        pieceTable.put("L", PIECEL);
        pieceTable.put("T", PIECET);
        pieceTable.put("U", PIECEU);
        pieceTable.put("X", PIECEX);
        pieceTable.put("V", PIECEV);
        pieceTable.put("F", PIECEF);
        pieceTable.put("P", PIECEP);
        pieceTable.put("Y", PIECEY);
        pieceTable.put("N", PIECEN);
        pieceTable.put("z", PIECESMALLZ);
        pieceTable.put("i", PIECESMALLI);
        pieceTable.put("d", PIECESMALLD);
        pieceTable.put("s", PIECESMALLS);
        pieceTable.put("t", PIECESMALLT);
        pieceTable.put("three", PIECE3);
        pieceTable.put("v", PIECESMALLV);
        pieceTable.put("two", PIECE2);
        pieceTable.put("one", PIECE1);
    }

    /** Get the array of 0's and 1's representing the initial
     * shape of any piece.
     * @param name The name of a desired piece, like W.
     * @return An array of array of 0's and 1's that represent
     * the shape of the piece.
     */
    public static int[][] getPiece(String name) {
        if (pieceTable.containsKey(name)) {
            return pieceTable.get(name);
        } else {
            return null;
        }
    }

}
