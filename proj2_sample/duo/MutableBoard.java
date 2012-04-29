package duo;

import static duo.Color.*;
import static duo.Pieces.*;

/** Represents a Blokus Duo(tm) game board that may be changed.  This
 *  is a subtype of Board so that Board itself can represent a
 *  non-modifiable game board.
 * @author Zack Mayeda
 */
class MutableBoard extends Board {

    /** This variable contains the game board, an array of
     *  arrays of colors. */
    private Color[][] mutBoard;


    /** Accessor for the game board.
     *  @return The mutable board that is an array of array
     *  of colors. */
    public Color[][] getMutBoard() {
        return mutBoard;
    }

    /** Return the current contents of the square in column COL
     *  and row Row for the mutable Board.
     *  @param col The number of the column you are looking for.
     *  @param row The number of the row you are looking for.
     *  @return The color that is in the square you are looking for.
     */
    Color mutGet(int col, int row) {
        return mutBoard[row][col];
    }

    /** A new, empty MutableBoard. */
    MutableBoard() {
        Color[][] b = new Color[SIZE][SIZE];
        for (int c = 0; c < SIZE; c += 1) {
            for (int r = 0; r < SIZE; r += 1) {
                b[r][c] = EMPTY;
            }
        }
        mutBoard = b;
    }

    /** A new MutableBoard whose initial contents are copied from
     *  BOARD.
     *  @param board take the contents of this board and copy
     *  it to the mutBoard. */
    MutableBoard(Board board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                mutBoard[row][col] = board.get(col, row);
            }
        }
    }

    /** Make the indicated MOVE on the current board for the player
     *  that is on move.
     *  @param move The string that is the move in PCRD form. */
    void makeMove(String move) {
        if (isWellFormed(move)) {
            String pieceName = getPieceMove(move);
            int colNum = getCol(move);
            int rowNum = getRow(move);
            int orientNum = getOrient(move);
            int[][] finalPiece = orient(pieceName, orientNum);
            Color playerColor = playerOnMove();
            moveOntoBoard(finalPiece, colNum, rowNum);
        }
    }

    /** Place piece on board, where piece is an array of array
     *  of colors.
     *  @param numPiece The color array that represents a piece.
     *  @param col The column where the piece will be placed.
     *  @param row The row where the piece will be placed. */
    void moveOntoBoard(int[][] numPiece, int col, int row) {
        Color[][] piece = toColor(numPiece);
        int pieceC = piece[0].length;
        int pieceR = piece.length;
        Color[][] tempBoard = getBoard();
        for (int bRow = pieceR - 1, p = 0; bRow >= 0; bRow--, p++) {
            for (int bCol = 0; bCol < pieceC; bCol++) {
                if (piece[bRow][bCol] == ORANGE) {
                    tempBoard[col + bCol][row + p] = ORANGE;
                }
                if (piece[bRow][bCol] == VIOLET) {
                    tempBoard[col + bCol][row + p] = VIOLET;
                }
            }
        }
        setGameBoard(tempBoard);
    }

     /** Change piece from array of 1's and 0's to array of colors.
      *  @param piece The array of array of 0's and 1's, where
      *  0's are empty spaces and 1's are colors.
      *  @return The given piece as an array of array of colors. */
    Color[][] toColor(int[][] piece) {
        int width = piece[0].length;
        int height = piece.length;
        Color playerColor = playerOnMove();
        Color[][] colorArray = new Color[height][width];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (piece[row][col] == 0) {
                    colorArray[row][col] = EMPTY;
                }
                if (piece[row][col] == 1) {
                    colorArray[row][col] = playerColor;
                }
            }
        }
        return colorArray;
    }
}
