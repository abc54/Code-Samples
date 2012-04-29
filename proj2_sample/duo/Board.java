package duo;

import static duo.Color.*;

import java.util.HashMap;
import java.util.regex.Pattern;

/** Represents a Blokus Duo(tm) game board.
 * @author Zack Mayeda
 */
class Board {

    /** The length and width of the board. */
    public static final int SIZE = 14;

    /** Return the length and width of the board.
     * @return the variable SIZE. */
    public static int getSize() {
        return SIZE;
    }

    /** This variable contains the game board, which is an array of
     * array of colors.
     */
    private Color[][] gameBoard;

    /** Accessor for the game board.
     * @return the gameBoard which is an array of array of colors.
     */
    public Color[][] getBoard() {
        return gameBoard;
    }

    /** A new, empty Immutable board. */
    Board() {
        Color[][] emptyBoard = new Color[SIZE][SIZE];
        for (int c = 0; c < SIZE; c++) {
            for (int r = 0; r < SIZE; r++) {
                emptyBoard[r][c] = EMPTY;
            }
        }
        gameBoard = emptyBoard;
    }

    /** Takes a game board (array of colors) as an argument
     *  and sets it to be the current gameBoard.
     *  @param newBoard is an array of arrays of colors
     *  that will become the new gameBoard.
     */
    public void setGameBoard(Color[][] newBoard) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                gameBoard[row][col] = newBoard[row][col];
            }
        }
    }

    /** Return the current contents of the square in column COL and row ROW.
     * @param col is the number of the column of the square to retrieve.
     * @param row is the number of the row of the square to retrieve.
     * @return the color that is the contents of the square.
     */
    Color get(int col, int row) {
        try {
            return gameBoard[row][col];
        } catch (ArrayIndexOutOfBoundsException n) {
            return null;
        }
    }

    /** Return the color of player whose turn it is.
     * @return the color of the player whose turn it is (orange or violet).
     */
    Color playerOnMove() {
        if (Game.getNumMoves() % 2 == 0) {
            return ORANGE;
        } else {
            return VIOLET;
        }
    }

    /** Return the number of the player whose turn it is. */
    int playerOnNum() {
        if (Game.getNumMoves() % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /** Return your opponent's color.
     * @return the color of your opponent (orange or violet).
     */
    public Color opponentColor() {
        if (playerOnMove() == ORANGE) {
            return VIOLET;
        } else {
            if (playerOnMove() == VIOLET) {
                return Color.ORANGE;
            }
        }
        return EMPTY;
    }
    /** Returns true iff MOVE is a syntactically correct move.
     *  @param move The move of the form PCRD (piece, column, row, rotation),
     *  that is input by the player.
     *  @return true iff the move follows the rules for move syntax.
     */
    static boolean isWellFormed(String move) {
        if (Pattern.matches("[WZILUTXVFPYNzidst3v21][0-9a-d][0-9a-d][0-7]",
                            move)) {
            return true;
        } else {
            return (move.startsWith("b") || move.startsWith("q"));
        }
    }

    /** Rotate the piece to the provided orientation by checking to make
     *  sure that is the smallest orientation number that returns
     *  the same shape, and then calling orientHelper of the piece
     *  to retrieve the rotated piece.
     * @param pieceName The single character name of the piece that you
     * want to rotate
     * @param orient The number of the orientation desired.
     * @return the array of array of ints that represents the rotated piece as
     * 1's and 0's, where 1's are colored and 0's are empty.
     */
    public int[][] orient(String pieceName, int orient) {
        int min = 0;
        min = orient;
        for (int numTest = orient - 1; numTest >= 0; numTest--) {
            if (orientHelper(pieceName, orient)
                == orientHelper(pieceName, numTest)) {
                min = numTest;
            }
        }
        return orientHelper(pieceName, min);
    }

    /** This does the actual rotation of pieces, with no checking of
     *  smallest orientation number.
     * @param piece The single character name of the piece that we
     *  want to rotate.
     * @param orientNum The number of the orientation desired.
     * @return the rotated piece in the form of an array of array of
     *  0's and 1's.
     */
    public int[][] orientHelper(String piece, int orientNum) {
        int[][] pieceArray = Pieces.getPiece(piece);
        int[][] orientedPiece = orient0(pieceArray);
        switch(orientNum) {
        case 0:
            orientedPiece = orient0(pieceArray);
            break;
        case 1:
            orientedPiece = orient1(pieceArray);
            break;
        case 2:
            orientedPiece =  orient2(pieceArray);
            break;
        case 3:
            orientedPiece =  orient3(pieceArray);
            break;
        case 4:
            orientedPiece =  orient4(pieceArray);
            break;
        case 5:
            orientedPiece =  orient5(pieceArray);
            break;
        case 6:
            orientedPiece =  orient6(pieceArray);
            break;
        case 7:
            orientedPiece = orient7(pieceArray);
            break;
        default:
            break;
        }
        return orientedPiece;
    }

    /** Change the orientation of the piece to 0.
     * @param piece A piece in array of array of int form. In this form,
     * 0's represent empty, and 1's represent a colored space.
     * @return The same piece in array of array of ints form,
     * not rotated because this is orientation 0.
     */
    public int[][] orient0(int[][] piece) {
        return piece;
    }

    /** Change the orientation of the piece to 1.
     * @param piece A piece in array of array of int form.
     * @return The piece in array of array of int form,
     * rotated to orientation 1.
     */
    public int[][] orient1(int[][] piece) {
        return rotateRight(piece);
    }

    /** Change the orientation of the piece to 2.
     * @param piece A piece in array of array of int form.
     * @return The piece in array of array of int form,
     * rotated to orientation 2.
     */
    public int[][] orient2(int[][] piece) {
        return horizFlip(vertFlip(piece));
    }

    /** Change the orientation of the piece to 3.
      * @param piece A piece in array of array of int form.
      * @return The piece in array of array of int form,
      * rotated to orientation 3.
      */
    public int[][] orient3(int[][] piece) {
        return vertFlip(horizFlip(rotateRight(piece)));
    }

    /** Change the orientation of the piece to 4.
      * @param piece A piece in array of array of int form.
      * @return The piece in array of array of int form,
      * rotated to orientation 4.
      */
    public int[][] orient4(int[][] piece) {
        return horizFlip(piece);
    }

    /** Change the orientation of the piece to 5.
      * @param piece A piece in array of array of int form.
      * @return The piece in array of array of int form,
      * rotated to orientation 5.
      */
    public int[][] orient5(int[][] piece) {
        return horizFlip(rotateRight(piece));
    }

    /** Change the orientation of the piece to 6.
      * @param piece A piece in array of array of int form.
      * @return The piece in array of array of int form,
      * rotated to orientation 6.
      */
    public int[][] orient6(int[][] piece) {
        return vertFlip(piece);
    }

    /** Change the orientation of the piece to 7.
      * @param piece A piece in array of array of int form.
      * @return The piece in array of array of int form,
      * rotated to orientation 7.
      */
    public int[][] orient7(int[][] piece) {
        return vertFlip(rotateRight(piece));
    }

    /** Flip a piece horizontally.
      * @param piece A piece in array of array of int form.
      * @return The piece in array of array of int form,
      * flipped horizontally.
      */
    public int[][] horizFlip(int[][] piece) {
        int height = piece.length;
        int width = piece[0].length;
        int[][] newPiece = new int[height][width];
        for (int row = 0; row < height; row++) {
            int[] newRow = new int[width];
            for (int col = 0; col < width; col++) {
                newRow[col] = piece[row][width - col - 1];
            }
            newPiece[row] = newRow;
        }
        return newPiece;
    }

    /** Flip a shape vertically.
     * @param piece A piece in array of array of int form.
     * @return The piece in array of array of int form,
     *  vertically flipped. */
    public int[][] vertFlip(int[][] piece) {
        int height = piece.length;
        int width = piece[0].length;
        int[][] newPiece = new int[height][width];
        for (int row = 0; row < height; row++) {
            newPiece[row] = piece[height - row - 1];
        }
        return newPiece;
    }

    /** Rotate a piece clockwise by 90 degrees.
     * @param piece A piece in array of array of int form.
     * @return The piece in array of array of int form, rotated
     *  clockwise 90 degrees.
     */
    public int[][] rotateRight(int[][] piece) {
        int height = piece.length;
        int width = piece[0].length;
        int[][] rotPiece = new int[width][height];
        for (int h = height - 1, count = 0; h >= 0; h--, count++) {
            for (int w = 0; w < width; w++) {
                rotPiece[w][count] = piece[h][w];
            }
        }
        return rotPiece;
    }

    /** Get the piece name from a move.
     * @param move The move in PCRD form.
     * @return The single character representation of a piece from the
     * given move.
     */
    public String getPieceMove(String move) {
        return getPieceName(move.substring(0, 1));
    }

    /** Return the name of the piece. If given a number 1, 2, or 3. This
     *  will return one, two, and three. This just handles the special
     *  cases and passes on the rest.
     *  @param rawName is the name of the piece, taken from the move.
     */
    public String getPieceName(String rawName) {
        if (rawName.equals("1")) {
            return "one";
        } else {
            if (rawName.equals("2")) {
                return "two";
            } else {
                if (rawName.equals("3")) {
                    return "three";
                } else {
                    return rawName;
                }
            }
        }
    }

    /** Get the column number from a move.
     * @param move The move in PCRD form.
     * @return The number of the column of the position from the given move.
     */
    public int getCol(String move) {
        return getBoardNum(move.substring(1, 2));
    }

    /** Get the row number from a move.
     * @param move The move in PCRD form.
     * @return The number of the row of the position from the given move.
     */
    public int getRow(String move) {
        return getBoardNum(move.substring(2, 3));
    }


    /** Get the orientation number from a move.
     * @param move The move in PCRD form.
     * @return The orientation number from the given move.
     */
    public int getOrient(String move) {
        return Integer.parseInt(move.substring(3, 4));
    }

    /** Given a string that represents the column or row number,
     *  returns the number of the column or row. For rows and columns
     *  greater than 9, "a" represents 10, "b" represents 11,
     *  "c" represents 12, and "d" represents 13.
     * @param numMove The column or row number in string form.
     * @return The number of the column or row.
     */
    public int getBoardNum(String numMove) {
        if (Pattern.matches("[0-9]", numMove)) {
            return Integer.parseInt(numMove);
        } else {
            return boardNums.get(numMove);
        }
    }

    /** The integer ten. */
    private static final int TEN = 10;

    /** The integer eleven. */
    private static final int ELEVEN = 11;

    /** The integer twelve. */
    private static final int TWELVE = 12;

    /** The integer thirteen. */
    private static final int THIRTEEN = 13;

    /** Hashmap to lookup values for a, b, c, and d for column
     *  and row number. */
    private static HashMap<String, Integer> boardNums =
        new HashMap<String, Integer>();

    static {
        boardNums.put("a", TEN);
        boardNums.put("b", ELEVEN);
        boardNums.put("c", TWELVE);
        boardNums.put("d", THIRTEEN);
    }

    /** Returns true iff MOVE is currently legal. It returns true after
     *  the finding one legal move. It checks to see if you can make any
     *  legal move using any piece, any col, any row, any orientation.
     * @param move The move in PCRD form. */
    boolean isLegal(String move) {
        String thePiece = getPieceMove(move);
        int[][] pieceArray = Pieces.getPiece(thePiece);
        int col = getCol(move);
        int row = getRow(move);
        int orient = getOrient(move);
        int[][] orientedPiece = orient(thePiece, orient);
        if (emptySpaceCheck(orientedPiece, col, row)) {
            if (onBoardCheck(orientedPiece, col, row)) {
                if (touchCornerCheck(orientedPiece, col, row)) {
                    if (noTouchSameCheck(orientedPiece, col, row)) {
                        if (startCornerCheck(move)) {
                            return true;

                        }
                    }

                }
            }
        }
        return false;
    }

    /** Check if board has empty spaces where you want to place the piece.
     * @param piece The piece in array of array of int form.
     * @param col The number of desired column position.
     * @param row The number of desired row position.
     * @return True if the board has empty spaces where you want
     *  to put the piece.
     */
    public boolean emptySpaceCheck(int[][] piece, int col, int row) {
        int height = piece.length;
        int width = piece[0].length;
        for (int h = height - 1, r = row; h >= 0; h--, r++) {
            for (int w = 0, c = col; w < width; w++, c++) {
                if (piece[h][w] == 1) {
                    if (get(r, c) == EMPTY || get(r, c) == null) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** Check if the piece you want to place touches the corner of at
     *  least one other piece of the same color.
     * @param piece The piece in array of array of int form.
     * @param col The number of the column of desired position.
     * @param row The number of the row of desired position.
     * @return True if the piece will touch at least on corner of a piece of the
     * same color.
     */
    public boolean touchCornerCheck(int[][] piece, int col, int row) {
        int height = piece.length;
        int width = piece[0].length;
        Color pColor = playerOnMove();
        if (Game.getNumMoves() > 1) {
            for (int h = height - 1, r = row; h >= 0; h--, r++) {
                for (int w = 0, c = col; w < width; w++, c++) {
                    if (piece[h][w] == 1) {
                        if (get(r - 1, c - 1) == pColor
                            || get(r - 1, c + 1) == pColor
                            || get(r + 1, c - 1) == pColor
                            || get(r + 1, c + 1) == pColor) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    /** Check if a piece will touch another piece of the same color
      * on the side, top, or bottom of any block of the piece.
     * @param piece The piece in array of array of int form.
     * @param col The number of the column of desired position.
     * @param row The number of the row of the desired position.
     * @return True if the piece doesn't touch a piece of the same color on
     * its side, top, or bottom.
     */
    public boolean noTouchSameCheck(int[][] piece, int col, int row) {
        int height = piece.length;
        int width = piece[0].length;
        for (int h = height - 1, r = row; h >= 0; h--, r++) {
            for (int w = 0, c = col; w < width; w++, c++) {
                if (piece[h][w] == 1) {
                    if (get(r - 1, c) == playerOnMove()
                        || get(r + 1, c) == playerOnMove()
                        || get(r, c - 1) == playerOnMove()
                        || get(r, c + 1) == playerOnMove()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** The integer fourteen. */
    private static final int FORTEEN = 14;

    /** Check if the piece is completely on the board (no parts of the
     *  piece go off of the board.
     * @param piece The piece in array of array of int form.
     * @param col The number of the column of desired position.
     * @param row The number of the row of desired position.
     * @return True if the piece will be completely on the board.
     */
    public boolean onBoardCheck(int[][] piece, int col, int row) {
        int height = piece.length;
        int width = piece[0].length;
        if (row + height  > FORTEEN) {
            return false;
        }

        if (col + width > FORTEEN) {
            return false;
        }
        return true;
    }

    /** Check if the first move for a player puts a piece in a corner.
     * @param move The move in PCRD form.
     * @return True if the move is not the first move for a player,
     *  or if it is the first play and puts a piece in a corner
     *  of the board.
     */
    public boolean startCornerCheck(String move) {
        int col = getCol(move);
        int row = getRow(move);
        int orient = getOrient(move);
        String piece = getPieceMove(move);
        int[][] rotatedPiece = orient(piece, orient);
        int width = rotatedPiece[0].length;
        int height = rotatedPiece.length;
        if (Game.getNumMoves() == 0
            || Game.getNumMoves() == 1) {
            if (col == 0
                && row == 0) {
                if (rotatedPiece[height - 1][0] == 1) {
                    return true;
                }
            }
            if (col == 0
                && row + height - 1 == THIRTEEN) {
                if (rotatedPiece[0][0] == 1) {
                    return true;
                }
            }
            if (col + width - 1 == THIRTEEN
                && row == 0) {
                if (rotatedPiece[height - 1][width - 1] == 1) {
                    return true;
                }
            }
            if (col + width - 1 == THIRTEEN
                && row + height - 1 == THIRTEEN) {
                if (rotatedPiece[0][width - 1] == 1) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

}
