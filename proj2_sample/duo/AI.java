package duo;

import static duo.Color.*;
import java.util.ArrayList;

/** Abstract class of all players.
 * @author Zack Mayeda
 */
class AI extends Player {

    /** An AI named NAME, playing the COLOR pieces, and using UI
     *  for messages.  This kind of player computes its own moves. */
    AI(String name, UI ui) {
        super(name, ui);
    }

    /** The integer 15. */
    private static final int MAXNUM = 18;

    /** The overriden move method, which generates a move and sends
     *  it to move in game.
     */
    @Override
    void move() {
        if (_game.getNumMoves() == 0 || _game.getNumMoves() == 1) {
            _game.move(playCorner());
        } else {
            String aMove = minMove();
            if (aMove.length() < 4) {
                _game.move(getRandMove());
            } else {
                _game.move(aMove);
            }
        }
    }

    /** The integer 14. */
    private static final int FOURTEEN = 14;

    /** The integer 13. */
    private static final int THIRTEEN = 13;

    /** Give the instructions, in PCRD form, to play the W piece
     *  first, in one of the corners of the board.
     *  @return The move that starts in a corner, if first turn. */
    public String playCorner() {
        Board b = _game.getBoard();
        if (b.isLegal("W000")) {
            return "W000";
        } else {
            if (b.isLegal("W0b1")) {
                return "W0b1";
            } else {
                if (b.isLegal("Wbb0")) {
                    return "Wbb0";
                } else {
                    if (b.isLegal("Wb01")) {
                        return "Wb01";
                    }
                }
            }
        }
        return "L000";
    }

    /** Find the move that places a piece on the board as close to
     *  the center of the board as possible.
     *  @return The move, in PCRD form, that is closest to center
     *  of board. */
    public String minMove() {
        Color myColor = getColor();
        ArrayList<String> possibleMoves = new ArrayList<String>();
        possibleMoves = null;
        ArrayList<String> piecesLeft = _game.getPieceList(myColor);
        for (int count = piecesLeft.size() - 1; count >= 0; count--) {
            ArrayList<String> allMoves = getAllMoves(piecesLeft.get(count));
            if (!allMoves.isEmpty()) {
                possibleMoves = allMoves;
                break;
            }
        }
        return findMin(possibleMoves);
    }

    /** Given one piece, create an arraylist of all possible moves on
     *  the current board using only that piece.
     *  @param piece The name of the piece.
     *  @return The arrayList of all possible moves using the given
     *  piece. */
    public ArrayList<String> getAllMoves(String piece) {
        ArrayList<String> tempMoves = new ArrayList<String>();
        for (int col = 0; col < FOURTEEN; col++) {
            for (int row = 0; row < FOURTEEN; row++) {
                for (int orient = 0; orient < 8; orient++) {
                    String testmove = buildAMove(piece, col, row, orient);
                    if (_game.getBoard().isWellFormed(testmove)) {
                        if (_game.getBoard().isLegal(testmove)) {
                            tempMoves.add(testmove);
                        }
                    }
                }
            }
        }
        return tempMoves;
    }

    /** Given an arrayList of moves, return the move that puts a
     *  piece closest to the center of the board.
     *  @param moveList The arraylist of possible moves.
     *  @return The move closest to the center. */
    public String findMin(ArrayList<String> moveList) {
        String bestMove = " ";
        int bestVal = FOURTEEN;
        for (int count = 0; count < moveList.size(); count++) {
            String currentMove = moveList.get(count);
            int tempVal = findDistance(currentMove);
            if (tempVal < bestVal) {
                bestMove = currentMove;
                bestVal = tempVal;
            } else {
                continue;
            }
        }
        return bestMove;
    }

    /** Find the sum of a piece's distance from row 7 and distance from
     *  column 7.
     *  @param move The move to calculate the distance for.
     *  @return The sum of the vertical and horizontal distances to
     *  the center of the board.
     */
    public int findDistance(String move) {
        Board b = getGame().getBoard();
        int col = b.getCol(move);
        int row = b.getRow(move);
        int orient = b.getOrient(move);
        String pieceName = b.getPieceMove(move);
        int[][] piece = b.orient(pieceName, orient);
        int width = piece[0].length;
        int height = piece.length;
        if (col + width <= 7 && row + height <= 7) {
            return (7 - col) + (7 - row);
        } else {
            if (col + width <= 7 && row + height > 7) {
                return (7 - col) + (row + height - 7);
            } else {
                if (col + width > 7 && row + height <= 7) {
                    return (col + width - 7) + (7 - row);
                } else {
                    if (col + width > 7 && row + height > 7) {
                        return (col + width - 7) + (row + height - 7);
                    }
                }
            }
        }
        return 7;
    }

    /** Generate any legal move. This searches through every possible
     *  piece, orientation, column number, and row number and returns
     *  the first legal move it finds. It checks if there are legal
     *  moves using larger pieces first.
     *  @return A legal move. */
    public String getRandMove() {
        Color color = getColor();
        for (int col = 0; col < FOURTEEN; col++) {
            for (int row = 0; row < FOURTEEN; row++) {
                for (int index = _game.getPieceList(color).size() - 1;
                     index >= 0; index--) {
                    String piece = _game.getPieceList(color).get(index);
                    for (int orient = 0; orient < 8; orient++) {
                        String testmove = buildAMove(piece, col, row, orient);
                        if (_game.getBoard().isWellFormed(testmove)) {
                            if (_game.getBoard().isLegal(testmove)) {
                                return testmove;
                            }
                        }
                    }
                }
            }
        }
        return null;

    }

    /** The integer 10. */
    private static final int TN = 10;

    /** The integer 11. */
    private static final int ELEV = 11;

    /** The integer 12. */
    private static final int TWEL = 12;

    /** The integer 13. */
    private static final int THIRT = 13;

    /** Return the move, using correct move syntax, for the given
     *  piece, column, number, row number, and orientation.
     *  @param piece The piece to put in the move.
     *  @param col The column number to put in the move.
     *  @param row The row number to put in the move.
     *  @param orient The orientation number to put in the move.
     */
    public static String buildAMove(String piece, int col,
                                      int row, int orient) {
        String colStr = toAlpha(col);
        String rowStr = toAlpha(row);
        String orientStr = Integer.toString(orient);
        String finalPiece = piece;
        if (piece.equals("one")) {
            finalPiece = "1";
        }
        if (piece.equals("two")) {
            finalPiece = "2";
        }
        if (piece.equals("three")) {
            finalPiece = "3";
        }
        String finalMove = (finalPiece + colStr + rowStr + orientStr);
        return finalMove;
    }

    /** Takes a string as argument and changes two digit numbers in the
     *  string into letters.
     *  @param rawNum The number from 0 - 13 that will be changed to
     *  a number from 0 - 9 and a - d.
     *  @return The hexadecimal version of the argument. */
    public static String toAlpha(int rawNum) {
        if (rawNum == TN) {
            return "a";
        } else {
            if (rawNum == ELEV) {
                return "b";
            } else {
                if (rawNum == TWEL) {
                    return "c";
                } else {
                    if (rawNum == THIRT) {
                        return "d";
                    } else {
                        return Integer.toString(rawNum);
                    }
                }
            }
        }
    }

}
