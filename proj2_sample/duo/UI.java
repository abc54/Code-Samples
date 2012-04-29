package duo;

import static duo.Color.*;

/** A user interface, providing for the reportage of board state and
 *  moves, and for input of moves.
 *  @author Zack Mayeda
 */
interface UI {

    /** Solicit and return a move from the COLOR player, where there
     *  have been NUMMOVES so far in this game and PREVMOVE is the last move
     *  (ignored if NUMMOVES is 0).  The arguments are intended for
     *  use by the UI (if needed) to fashion a prompt. */
    String getMove(Color color, int numMoves, String prevMove);

    /** Report the winner of an ORANGESCORE-VIOLETSCORE game. */
    void reportWinner(int orangeScore, int violetScore);

    /** Show the contents of BOARD. */
    void reportBoard(Board board);

    /** Show the contents of BOARD in the official format (surrounded
     *  by ===). */
    void reportBoardStandard(Board board);

    /** Report that the COLOR player has made MOVE, for a total of
     *  NUMMOVES in the current game. */
    void reportMove(Color color, int numMoves, String move);

    /** Display MESSAGE as an error message. */
    void reportError(String message);

    /** Display informational message MESSAGE (not an error). */
    void report(String message);

}
