package duo;

import static duo.Color.*;

/** Abstract class of all players.
 * @author Zack Mayeda
 */
abstract class Player {

    /** A Player named NAME who uses UI (if needed) for input and messages. */
    Player(String name, UI ui) {
        _name = name;
        _ui = ui;
    }

    /** Returns my name. */
    String getName() {
        return _name;
    }

    /** Returns the color I am playing. */
    Color getColor() {
        return _color;
    }

    /** Returns the game I am playing. */
    Game getGame() {
        return _game;
    }

    /** Join GAME as the COLOR player. */
    void startGame(Game game, Color color) {
        _game = game;
        _color = color;
    }

    /** Make the next move in the game I am currently playing. */
    abstract void move();

    /** My name. */
    private final String _name;
    /** The piece color I am playing. */
    private Color _color;
    /** My current game. */
    protected Game _game;
    /** The UI I use for input and messages. */
    protected UI _ui;

}
