package Challenge;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Zack Mayeda
 * 3/15/12
 * Fun-Learning Factor, MathHelpers class
 * This class contains all the methods which are used to compute the FLF of a Tutor, Player assignment.
 */
public class MathHelpers {

    /** A variable that contains all uppercase and lowercase vowels in an array of strings. */
    static String[] vowelArray = {"A", "a", "E", "e", "I", "i","O", "o", "U", "u"};
    /** A variable that puts all vowels into an ArrayList. */
    static ArrayList<String> vowels = new ArrayList<String>(Arrays.asList(vowelArray));

    /** Method that returns the number of vowels in a string.
    *  @param name A string that can be a player name or tutor name.
    *  @return The number of vowels in a string as a double.
    */
    static double vowels(String name) {
        double count = 0;
         for (int i = 0; i < name.length(); i++) {
             if (vowels.contains(name.substring(i, i+1))) {
                 count++;
             }
         }
         return count;
    }

    /** Method that returns the number of consonants in a string.
     * @param name A string that can be a player name or tutor name.
     * @return The number of consonants in the string as a double.
     */
    static double consonants(String name) {
        double count = 0;
        for (int i = 0; i < name.length(); i++) {
            if (!vowels.contains(name.substring(i, i+1))) {
                count += 1;
            }
        }
        return count;
    }

    /** A number that returns the greatest common factor of two numbers.
     * @param a The first integer to compare.
     * @param b The second integer to compare.
     * @return The greatest common factor of two numbers as a double. */
    static double gcf(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcf(b, a % b);
        }
    }

    /** Method that calls helper functions in this class to compute the FLF of a given tutor and player.
     * @param tutor The tutor, which is a person object.
     * @param player The player, which is a person object.
     * @return The FLF of the given tutor and player as a double. */
    static double computeFLF(Person tutor, Person player) {
        double baseFLF, totalFLF;
        if (tutor.getName().length() % 2 != 0) {
             baseFLF = 1.5 * vowels(player.getName());
        } else {
            baseFLF = consonants(player.getName());
        }
        totalFLF = baseFLF;
        if (gcf(player.getName().length(), tutor.getName().length()) != 1) {
            totalFLF += .5 * baseFLF;
        }
        return totalFLF;
    }

}
