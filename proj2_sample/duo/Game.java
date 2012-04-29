package duo;

import java.util.Random;
import static duo.Color.*;
import java.util.ArrayList;

/** Supervisor for a game of Duo.
 *  @author Zack Mayeda
 */
class Game {

    /** A new Game representing one game between ORANGEPLAYER and
     *  VIOLETPLAYER.  Initially, the board is empty. Uses UI for input
     *  and output.  If SEED is non-zero, uses it to seed a random
     *  number generator. */
    Game(Player orangePlayer, Player violetPlayer, UI ui, long seed) {
        _player[0] = orangePlayer;
        _player[1] = violetPlayer;
        _ui = ui;
        if (seed == 0) {
            _rand = new Random();
        } else {
            _rand = new Random(seed);
        }
        _board = new MutableBoard();
    }

    /** Returns the current board (immutably). */
    public Board getBoard() {
        return _board;
    }

    /** Returns the number of moves that have been made (VIOLET and
     *  ORANGE). */
    public static int getNumMoves() {
        return _moveCount;
    }

    /** Updates _moveCount to a new integer.
     *  @param num The integer to set as the current number of moves. */
    public static void setNumMoves(int num) {
        _moveCount = num;
    }

    /** Perform MOVE on the current board (the color of the piece
     *  placed depends on whose move it is). */
    void move(String move) {
        Color playerColor = _board.playerOnMove();
        Color opponentColor = getOpponentColor(playerColor);
        String piece = _board.getPieceMove(move);
        ArrayList<String> pieceList = getPieceList(playerColor);
        if (_board.isWellFormed(move)) {
            if (move.startsWith("b")) {
                _ui.reportBoardStandard(_board);
            } else {
                if (move.startsWith("q")) {
                    System.exit(0);
                }
                if (pieceList.contains(piece)) {
                    if (_board.isLegal(move)) {
                        _board.makeMove(move);
                        setNumMoves(1 + getNumMoves());
                        removePiece(playerColor, piece);
                        setPrevMove(playerColor, move);
                        _ui.reportMove(playerColor, getNumMoves(), move);

                    } else {
                        _ui.reportError("error, that is not a legal move");
                    }
                } else {
                    _ui.reportError("error, this piece has already been used");
                }
            }
        } else {
            _ui.reportError("error, move is not well formed");
        }
    }


    /** Given one player's color, this will return the opponent's
     *  color.
     *  @param color The color of the current player.
     */
    public Color getOpponentColor(Color color) {
        if (color == ORANGE) {
            return VIOLET;
        } else {
            if (color == VIOLET) {
                return ORANGE;
            }
        }
        return EMPTY;
    }

    /** Starting from the current board, complete a game between the
     *  two players, reporting all results on my Reporter. */
    void play() {
        Color opponentColor = _board.opponentColor();
        _player[0].startGame(this, ORANGE);
        _player[1].startGame(this, VIOLET);
        while (true) {
            Color playerColor = _board.playerOnMove();
            int playerNum = _board.playerOnNum();
            if (hasMove(playerColor)) {
                if (getPieceList(playerColor).isEmpty()) {
                    break;
                } else {
                    if (playerNum % 2 == 0) {
                        _player[0].move();
                        continue;
                    } else {
                        _player[1].move();
                        continue;
                    }
                }
            } else {
                break;
            }
        }
        _ui.reportWinner(getScore(ORANGE), getScore(VIOLET));
        System.exit(0);
    }

    /** Returns a uniformly distributed pseudo-random integer between
     *  0 and N-1 (inclusive).  Assumes N > 0. */
    int nextRand(int n) {
        return _rand.nextInt(n);
    }

    /** Returns a uniformly distributed pseudo-random int. */
    int nextRand() {
        return _rand.nextInt();
    }

    /** The integer 14. */
    private static final int FRTEEN = 14;

    /** The integer 94. */
    private static final int WIN = 94;

    /** Returns the score of a color on the current board, by counting
     *  all of the spaces of the array that contain that color.
     *  @param color The color of the player whose score you
     * want to retrieve.
     */
    public int getScore(Color color) {
        int score = 0;
        int size = FRTEEN;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++)  {
                if (_board.get(col, row) == color) {
                    score = 1 + score;
                }
            }
        }
        if (getPrevMove(ORANGE).startsWith("1")
            && getPrevMove(VIOLET).startsWith("1")
            && getPieceList(ORANGE).isEmpty()
            && getPieceList(VIOLET).isEmpty()) {
            score = WIN;
            return score;
        }
        if (getPrevMove(color).startsWith("1")) {
            if (getPieceList(color).isEmpty()) {
                score = 5 + score;
            }
        }
        return score;
    }


    /** Returns true if the player of given color has any
     *  possible moves left on the board.
     *  @param color The color of the player for which we want to check
     *  if they have any legal moves left. */
    public boolean hasMove(Color color) {
        int num = getPieceList(color).size();
        for (int col = 0; col < FRTEEN; col++) {
            for (int row = 0; row < FRTEEN; row++) {
                for (int index = 0; index < num; index++) {
                    String piece = getPieceList(color).get(index);
                    for (int orient = 0; orient < 8; orient++) {
                        String testmove = buildMoveArg(piece, col, row, orient);
                        if (_board.isWellFormed(testmove)) {
                            if (_board.isLegal(testmove)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /** The integer 10. */
    private static final int TN = 10;

    /** The integer 11. */
    private static final int ELEV = 11;

    /** The integer 12. */
    private static final int TWEL = 12;

    /** The integer 13. */
    private static final int THIRT = 13;

    /** Return the move, using correct move syntax, for the given piece,
        column, number, row number, and orientation.
     *  @param piece The piece to put in the move.
     *  @param col The column number to put in the move.
     *  @param row The row number to put in the move.
     *  @param orient The orientation number to put in the move.
     */
    public static String buildMoveArg(String piece, int col,
                                      int row, int orient) {
        String colStr = toLetters(col);
        String rowStr = toLetters(row);
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

    /** Takes string as argument and changes two digit numbers in the
     *  string into letters.
     *  @param rawNum The number from 0 - 13 that will be changed to
     *  a number from 0 - 9 and a - d.
     *  @return The hexadecimal version of the argument. */
    public static String toLetters(int rawNum) {
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

    /** An arraylist of current possible pieces for orange. */
    private static ArrayList<String> pieceListO = new ArrayList<String>();

    /** An arrayList of current  possible pieces for violet. */
    private static ArrayList<String> pieceListV = new ArrayList<String>();

    static {
        pieceListO.add("one");
        pieceListO.add("two");
        pieceListO.add("v");
        pieceListO.add("three");
        pieceListO.add("s");
        pieceListO.add("t");
        pieceListO.add("d");
        pieceListO.add("i");
        pieceListO.add("z");
        pieceListO.add("P");
        pieceListO.add("F");
        pieceListO.add("Y");
        pieceListO.add("N");
        pieceListO.add("V");
        pieceListO.add("X");
        pieceListO.add("T");
        pieceListO.add("U");
        pieceListO.add("L");
        pieceListO.add("I");
        pieceListO.add("Z");
        pieceListO.add("W");

        pieceListV.add("one");
        pieceListV.add("two");
        pieceListV.add("v");
        pieceListV.add("three");
        pieceListV.add("s");
        pieceListV.add("t");
        pieceListV.add("d");
        pieceListV.add("i");
        pieceListV.add("z");
        pieceListV.add("P");
        pieceListV.add("F");
        pieceListV.add("Y");
        pieceListV.add("N");
        pieceListV.add("V");
        pieceListV.add("X");
        pieceListV.add("T");
        pieceListV.add("U");
        pieceListV.add("L");
        pieceListV.add("I");
        pieceListV.add("Z");
        pieceListV.add("W");
    }

    /** Accessor for both lists of pieces left for players.
     *  @param color The color of the player whose list of pieces
     *  you want to retrieve.
     *  @return The list of pieces available for the player color. */
    public ArrayList<String> getPieceList(Color color) {
        if (color == ORANGE) {
            return pieceListO;
        }
        if (color == VIOLET) {
            return pieceListV;
        }
        return null;
    }

    /** Remove a piece from color's piecelist.
     *  @param color The color of the player whose list of pieces
     *  you want to retrieve.
     *  @param piece The piece that you want to remove from the
     *  list of possible pieces. */
    public void removePiece(Color color, String piece) {
        getPieceList(color).remove(piece);
    }

    /** The previous move played by the orange player. */
    private String prevMoveO = "nothing";

    /** The previous move played by the violet player. */
    private String prevMoveV = "nothing";

    /** Change the previous move played by a player to a new move.
     *  @param color The color of the player whose previous move
     *  you want to set.
     *  @param move The move that you want to set as a previous move. */
    public void setPrevMove(Color color, String move) {
        if (color == ORANGE) {
            prevMoveO = move;
        }
        if (color == VIOLET) {
            prevMoveV = move;
        }
    }

    /** Retrieve the previous move played by a player.
     *  @param color The color of the player whose previous move
     *  you want to retrieve.
     *  @return The string that is the previous move of color player. */
    public String getPrevMove(Color color) {
        if (color == ORANGE) {
            return prevMoveO;
        }
        if (color == VIOLET) {
            return prevMoveV;
        }
        return null;
    }

    /** Return a random number. */
    public Random getRand() {
        return _rand;
    }

    /** The players in this game: orange is _player[0] and violet
     *  is _player[1]. */
    private final Player[] _player = new Player[2];

    /** The current board. */
    private MutableBoard _board;

    /** The UI I and my players use for input and output. */
    private final UI _ui;

    /** A random number generator for use by my players. */
    private Random _rand;

    /** A counter for how many moves have been made total. */
    private static int _moveCount;

}
