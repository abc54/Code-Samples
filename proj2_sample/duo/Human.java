package duo;

import static duo.Color.*;

/** Abstract class of all players.
 * @author Zack Mayeda
 */
class Human extends Player {

    /** A Human player named NAME, playing the COLOR pieces,
     *  and using UI for input and messages.  This kind of
     *  player prompts for moves from the user. */
    Human(String name, UI ui) {
        super(name, ui);
    }

    /** The overriden move method for humans. It gets the next move
     *  from TextUI and if the move is well-formed, it sends it to
     *  game's move method.
     */
    @Override
    void move() {
        Board b = _game.getBoard();
        String theMove = _ui.getMove(getColor(), _game.getNumMoves(),
                                     _game.getPrevMove(getColor()));
        Color currentColor = b.playerOnMove();
        if (Board.isWellFormed(theMove)) {
            _game.move(theMove);
        } else {
            _ui.reportError("error, move is not well formed");
        }
    }

}
