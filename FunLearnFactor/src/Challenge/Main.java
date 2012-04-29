package Challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Zack Mayeda
 * 3/15/12
 *
 * Challenge 3: Fun-Learning Factor
 * To solve this challenge, I used the Hungarian Algorithm, implemented in matrix form. A clear
 * explanation of this algorithm is available on Wikipedia.
 * http://en.wikipedia.org/wiki/Hungarian_algorithm
 * The goal for this challenge is to maximize the FLF and the Hungarian Algorithm finds a minimum value
 * associated with assignments, so I made some alterations to the information I had to fit the algorithm. There is more
 * detail about this in the Factor Class, convertToMin method.
 *
 * Fun-Learning Factor, Main class
 * This class reads input from the command line, calls many helper methods, and outputs results.
 * Assumptions:
 * - "y" is not a vowel
 * - there are equal tutors and players
 * - tutor and player names will only contain letters (no dashes, numbers, etc.)
 * - individual names (tutors or players) are separated by a whitespace
 * - final assignments and total FLF will be printed to command line (this can easily be changed to output to a file)
 * - assignments are printed in the following format (Tutor_1, Player_1), (Tutor_2, Player_2), ...
 */

public class Main {

    public static void main(String[] args) throws IOException {
        Factor group = new Factor();
        BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
        String tutorFileName = inLine.readLine();
        String playerFileName = inLine.readLine();
        inLine.close();
        BufferedReader tutorReader = new BufferedReader(new FileReader(tutorFileName));
        String tName;
        while ((tName = tutorReader.readLine()) != null) {
            group.addPerson(tName, 0);
        }
        BufferedReader playerReader = new BufferedReader(new FileReader(playerFileName));
        String pName;
        while ((pName = playerReader.readLine()) != null) {
            group.addPerson(pName, 1);
        }

        group.computeEachFLF();

        group.convertToMin();

        group.setMins();

        group.subtractMin();

        group.coverZeros();

        group.assign();

        ArrayList<Person> players = group.getPlayerList();
        ArrayList<Person> tutors = group.getTutorList();
        ArrayList<ArrayList<Double>> originalMatrix = group.getOriginalMatrix();
        HashMap<Integer, Integer> pairs = group.getPairs();
        for (int i = 0; i < group.getNumberOfPairs(); i++) {
            int j = pairs.get(i);
            System.out.println("(" + tutors.get(i).getName() + ", " + players.get(j).getName() + ")");
            group.incTotalFLF(originalMatrix.get(i).get(j));
        }
        System.out.println();
        System.out.println("Total FLF: " + group.getTotalFLF());
    }

}
