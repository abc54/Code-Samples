package Challenge;

/**
 * @author Zack Mayeda
 * 3/15/12
 * Fun-Learning Factor, Person class
 * This class represents any person, player or tutor. I included this class to make the program as a whole
 * more understandable. By creating person objects, certain data structures can be filled with Person objects
 * instead of strings. I can easily remove this class, but I kept it to make the program more readable for myself.
 */
public class Person {
    /** The name of the person, as a string. */
    private String name;

    /** Person constructor method that sets name to be the given string.
     * @param s The name of the person, as a string. */
    public Person(String s) {
        name = s;
    }

    /** Getter method to retrieve name.
     * @return The name of the person object as a string. */
    String getName() {
        return name;
    }

}
