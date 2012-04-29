package duo;

import java.util.Scanner;
import java.io.FileReader;

import java.io.FileNotFoundException;

/** Initial class for the duo program.
 *  @author Zack Mayeda
 */
public final class Main {

    /** Entry point for the CS61B duo program.  ARGS may contain 2 to 4
     *  arguments: SRC1 SRC2 [ SEED [ INITIAL-MOVES-FILE ] ].  SRC1
     *  and SRC2 are names of players; automated players start with
     *  "@".  OPTIONAL_SEED, if present is a long integer numeral
     *  giving an initial seed for a pseudo-random number generator.
     *  With identical seeds, the program will behave identically on
     *  identical inputs. */
    public static void main(String... args) {

        if (args.length < 2 || args.length > 4) {
            usage();
        }

        Game game;
        Player orange, violet;
        long seed;
        UI ui = new TextUI();

        if (args[0].startsWith("@")) {
            orange = new AI(args[0], ui);
        } else {
            orange = new Human(args[0], ui);
        }
        if (args[1].startsWith("@")) {
            violet = new AI(args[1], ui);
        } else {
            violet = new Human(args[1], ui);
        }

        seed = 0;
        if (args.length > 2) {
            try {
                seed = Long.parseLong(args[2]);
            } catch (NumberFormatException e) {
                fatalError(ui, "Seed has bad format");
            }
        }

        game = new Game(orange, violet, ui, seed);

        if (args.length > 3) {
            initGame(game, args[3], ui);
        }

        game.play();
    }

    /** Read moves from the file named INITFILENAME into GAME.  Report
     *  any errors to UI. */
    private static void initGame(Game game, String initFileName, UI ui) {
        try {
            Scanner s = new Scanner(new FileReader(initFileName));
            while (s.hasNext()) {
                game.move(s.next());
            }
        } catch (FileNotFoundException e) {
            fatalError(ui, "cannot open initial-move file.");
        } catch (IllegalArgumentException e) {
            fatalError(ui, "initial-move file contains bad move.");
        }
    }

    /** Print MSG on UI as an error and terminate program with
     *  non-zero exit code. */
    static void fatalError(UI ui, String msg) {
        ui.reportError(msg);
        System.exit(1);
    }

    /** Print a brief usage message and exit program abnormally. */
    private static void usage() {
        System.out.println("error, usage error. too few or"
                           + " too many arguments supplied");
        System.exit(1);
    }

}
