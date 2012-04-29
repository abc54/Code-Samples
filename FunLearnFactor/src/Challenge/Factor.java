package Challenge;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Zack Mayeda
 * 3/15/12
 * Fun-Learning Factor, Factor class
 * This class does the bulk of the work for this challenge. It implements the Hungarian Algorithm,
 * and contains all data structures for this program.
 */

public class Factor {

    /** ArrayList containing all tutors as Person objects. */
    private ArrayList<Person> tutors = new ArrayList<Person>();

    /** ArrayList containing all players as Person objects. */
    private ArrayList<Person> players = new ArrayList<Person>();

    /** The maximum sum of FLF possible with given players and tutors. */
    private double totalFLF = 0;

    /** The largest single FLF that exists for any one player and one tutor. */
    private double maxSingleFLF = 0;

    /** The number of pairings needed to be made given all players and tutors. */
    private int numberOfPairs;

    /** ArrayList containing the minimum FLF for each row of the flfMatrix.
     * The minimum FLF for a row is stored at that row's index in rowMin.
     * For example: if 7.0 is the smallest FLF in row 2, 7.0 is store at index 2 in rowMin. */
    private ArrayList<Double> rowMin = new ArrayList<Double>();

    /** ArrayList containing the minimum FLF for each column of the flfMatrix.
     * the minimum FLF for a column is stored at that column's index in colMin. */
    private ArrayList<Double> colMin = new ArrayList<Double>();

    /** ArrayList containing booleans corresponding to whether or not a row (tutor) has
     * been given an assignment. The row number is the position of the boolean in rowCover. */
    private ArrayList<Boolean> rowCover = new ArrayList<Boolean>();

    /** ArrayList containing booleans corresponding to whether or not a column (player) has
     * been given an assignment. The column number is the position of the boolean in colCover. */
    private ArrayList<Boolean> colCover = new ArrayList<Boolean>();

    /** The matrix formed with ArrayLists, which contains doubles. Tutors correspond to rows,
     * and players to columns. This matrix is often altered as the program runs. This is the
     * matrix that I use to run the Hungarian Algorithm. */
    private ArrayList<ArrayList<Double>> flfMatrix = new ArrayList<ArrayList<Double>>();

    /** The matrix formed with ArrayLists, which contains every possible FLF, as doubles.
     * Tutors correspond to rows, and players to columns. After being filled with every FLF,
     * this matrix is never changed. */
    private ArrayList<ArrayList<Double>> originalMatrix = new ArrayList<ArrayList<Double>>();

    /** HashMap containing all final assignments. A tutor's position in the tutor ArrayList
     * corresponds to the key of the assignment in this HashMap. The player assigned to a given
     * tutor is the value. */
    private HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();

    /** Method that is given a string and an int that tells what type of person is being created.
     *  @param name One long string that is the name of the person.
     *  @param job 0 if the people being created are tutors, 1 if the people being created are players.
     */
    void addPerson(String name, int job) {
        Person newPerson = new Person(name);
        if (job == 0) {
            tutors.add(newPerson);
            numberOfPairs += 1;
        } else {
            players.add(newPerson);
        }
    }

    /** Method that gets the FLF for each tutor and player assignment, and puts that value into the flfMatrix.
     *  After filling the flfMatrix, this copies all values into the originalMatrix.
     */
    void computeEachFLF() {
        for (int i = 0; i < numberOfPairs; i++) {
            ArrayList<Double> row = new ArrayList<Double>();
            for (int j = 0; j < numberOfPairs; j++) {
                double FLF = MathHelpers.computeFLF(tutors.get(i), players.get(j));
                row.add(j, FLF);
                if (FLF > maxSingleFLF) {
                    maxSingleFLF = FLF;
                }
            }
            flfMatrix.add(i, row);
            rowMin.add(Double.POSITIVE_INFINITY);
            colMin.add(Double.POSITIVE_INFINITY);
        }
        duplicateMat(flfMatrix, originalMatrix);
    }

    /** Method that copies all elements of a matrix, represented by an ArrayList of ArrayLists, into a different
     * matrix.
     * @param start The matrix to copy elements from.
     * @param dest The location where elements will be copied to. */
    void duplicateMat(ArrayList<ArrayList<Double>> start, ArrayList<ArrayList<Double>> dest) {
        for (int i = 0; i < numberOfPairs; i++) {
            ArrayList<Double> row = new ArrayList<Double>();
            for (int j = 0; j < numberOfPairs; j++) {
                double val = start.get(i).get(j);
                row.add(j, val);
                dest.add(i, row);
            }
            dest.add(i, row);
        }
    }

    /** Method that finds the difference between the largest element in the flfMatrix and each element in the flfMatrix.
     *  Remember that singleMaxFLF is the largest value in the flfMatrix, so I just subtract each element in the
     *  flfMatrix from the singleMaxFLF and put the result into the flfMatrix. Thus each number is still positive,
     *  and the largest number in the previous flfMatrix is 0 in the new flfMatrix and the smallest number in the
     *  previous flfMatrix is now the largest number in the new flfMatrix. This effectively turns the FLF
     *  maximization problem into a minimization problem. Thus I can solve this challenge using the Hungarian Algorithm. */
    void convertToMin() {
        for (int i = 0; i < numberOfPairs; i++) {
            ArrayList<Double> row = flfMatrix.get(i);
            double newFLF;
            for (int j = 0; j < numberOfPairs; j++) {
                newFLF = maxSingleFLF - row.get(j);
                row.set(j, newFLF);
            }
            flfMatrix.set(i, row);
        }
    }

    /** Method that determines the minimum FLF for each row and column, and places that minimum FLF
     *  in the corresponding location in rMin and cMin, respectively. */
    void setMins() {
        for (int i = 0; i < numberOfPairs; i++) {
            double rMin = Double.POSITIVE_INFINITY, cMin = Double.POSITIVE_INFINITY;
            ArrayList<Double> row = flfMatrix.get(i);
            for (int j = 0; j < numberOfPairs; j++) {
                double currentRFLF = row.get(j);
                double currentCFLF = flfMatrix.get(j).get(i);
                if (currentRFLF < rMin) {
                    rMin = currentRFLF;
                }
                if (currentCFLF < cMin) {
                    cMin = currentCFLF;
                }
            }
            rowMin.set(i, rMin);
            colMin.set(i, cMin);
        }
    }

    /** Method that subtracts the minimum FLF of a row from each FLF in that row. It accesses the rowMin
     *  ArrayList that we filled earlier to obtain the minimum FLF for a row. */
    void subtractMin() {
        for (int i = 0; i < numberOfPairs; i++) {
            ArrayList<Double> row = flfMatrix.get(i);
            for (int j = 0; j < numberOfPairs; j++) {
                double oldVal = row.get(j);
                row.set(j, oldVal - rowMin.get(i));
            }
            flfMatrix.set(i, row);
        }
        setMins();
    }

    /** Method that empties an ArrayList of booleans, and adds one false for each assignment that will
     *  be formed at the end of the problem.
     *  @param list ArrayList of booleans that will be emptied and filled with false booleans. */
    void resetList(ArrayList<Boolean> list) {
        list.clear();
        for (int i = 0; i < numberOfPairs; i++) {
            list.add(i, false);
        }
    }

    /** Method that uses several helper methods to find the optimal horizontal and vertical lines to cover all
     *  zeros in the flfMatrix. It updates the rowCover and colCover ArrayList to true if a
     *  column or row is covered, and false if it isn't. It continues combinations of horizontal and
     *  vertical lines until there are the same number of lines as there are assignments to be made, or stops if that
     *  isn't possible. */
    void coverZeros() {
        resetList(colCover);
        resetList(rowCover);
        int min = minLines();
        while (min < numberOfPairs) {
            double num = findMinUncovered();
            reduceUncovered(num);
            addToCovered(num);
            resetList(colCover);
            resetList(rowCover);
            min = minLines();
        }
    }

    /** Method that returns the minimum number of lines required to cover all zeros in the flfMatrix.
     * It does this by covering lines in two different ways, and choosing whichever can cover all zeros in
     * the least number of lines. First it traverses columns and rows in ascending order, and compares that
     * to the results obtained from traversing rows in ascending order and columns in descending order.
     * It updates colCover and rowCover accordingly.
     * @return The number minimum number of lines needed to cover all zeros in flfMatrix. */
    int minLines() {
        ArrayList<Boolean> col1 = new ArrayList<Boolean>();
        ArrayList<Boolean> row1 = new ArrayList<Boolean>();
        ArrayList<Boolean> col2 = new ArrayList<Boolean>();
        ArrayList<Boolean> row2 = new ArrayList<Boolean>();
        int min1 = 0;
        int min2 = 0;
        resetList(col1);
        resetList(row1);
        // check columns and rows starting from index zero
        for (int i = 0; i < numberOfPairs; i++) {
            for (int j = 0; j < numberOfPairs; j++) {
                if (flfMatrix.get(i).get(j) == 0 && !row1.get(i) && !col1.get(j)) {
                    row1.set(i, true);
                    min1++;
                }
            }
        }
        for (int i = 0; i < numberOfPairs; i++) {
            for (int j = 0; j < numberOfPairs; j++) {
                if (flfMatrix.get(i).get(j) == 0 && !row1.get(i) && !col1.get(j)) {
                    col1.set(j, true);
                    min1++;
                }
            }
        }

        resetList(col2);
        resetList(row2);
        // check rows starting from index zero, columns starting from the largest index
        for (int i = numberOfPairs - 1; i >= 0; i--) {
            for (int j = 0; j < numberOfPairs; j++) {
                if (flfMatrix.get(i).get(j) == 0 && !row2.get(i) && !col2.get(j)) {
                    col2.set(j, true);
                    min2++;
                }
            }
        }
        for (int i = numberOfPairs - 1; i >= 0; i--) {
            for (int j = 0; j < numberOfPairs; j++) {
                if (flfMatrix.get(i).get(j) == 0 && !row2.get(i) && !col2.get(j)) {
                    row2.set(i, true);
                    min2++;
                }
            }
        }
        if (min1 < min2) {
            colCover = col1;
            rowCover = row1;
            return min1;
        } else {
            colCover = col2;
            rowCover = row2;
            return min2;
        }
    }

    /** Method to find the smallest number that is not covered. It accesses rowCover and colCover to
     *  determine if a row or column is covered.
     *  @return The smallest double that is not covered. */
    double findMinUncovered() {
        Double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < numberOfPairs; i++) {
            if (!rowCover.get(i)) {
                for (int j = 0; j < numberOfPairs; j++) {
                    double num = flfMatrix.get(i).get(j);
                    if (!colCover.get(j) && num < min) {
                        min = num;
                    }
                }
            }
        }
        return min;
    }

    /** Method to subtract a number from every uncovered number. Update minimum lists.
     *  @param min The number to subtract from each uncovered number. */
    void reduceUncovered(double min) {
        for (int i = 0; i < numberOfPairs; i++) {
            for (int j = 0; j < numberOfPairs; j++) {
                if (!rowCover.get(i) && !colCover.get(j)) {
                    double oldVal = flfMatrix.get(i).get(j);
                    double newVal = oldVal - min;
                    flfMatrix.get(i).set(j, newVal);
                    if (newVal < rowMin.get(i)) {
                        rowMin.set(i, newVal);
                    }
                    if (newVal< colMin.get(j)) {
                        colMin.set(j, newVal);
                    }
                }
            }
        }
    }

    /** Method to add a number to every number which is in a covered column and covered row.
     *  @param min The number to add to each number located in a covered column and covered row. */
    void addToCovered(double min) {
        for (int i = 0; i < numberOfPairs; i++) {
            for (int j = 0; j < numberOfPairs; j++) {
                if (rowCover.get(i) && colCover.get(j)) {
                    double oldVal = flfMatrix.get(i).get(j);
                    flfMatrix.get(i).set(j, oldVal + min);
                }
            }
        }
    }

    /** Method that makes the final assignments, and puts them into the pairs HashMap. The first while loop
     *  finds all pairings that are definitely increase total FLF (if there is only one zero in a row/column).
     *  The second while loop handles the situation where two or more assignments have the same FLF, and
     *  the total FLF will not be different if either assignment is made (it there is more than one zero per
     *  row/column).
     *  Essentially, this methods tries to create assignments so that sum of all chosen places in matrix equals 0. */
    void assign() {
        resetList(colCover);
        resetList(rowCover);
        int clock = 1;
        int oldClock = 0;
        int assignments = 0;
        while (clock != oldClock && assignments <= numberOfPairs) {
            clock = oldClock;
            int detector = oldClock;
            for (int i = 0; i < numberOfPairs; i++) {
                for (int j = 0; j < numberOfPairs; j++) {
                    if (uniqueZeroRow(i, j)) {
                        rowCover.set(i, true);
                        colCover.set(j, true);
                        pairs.put(i,j);
                        detector++;
                        assignments++;
                    }
                }
            }
            for (int i = 0; i < numberOfPairs; i++) {
                for (int j = 0; j < numberOfPairs; j++) {
                    if (uniqueZeroCol(i, j)) {
                        rowCover.set(i, true);
                        colCover.set(j, true);
                        pairs.put(i,j);
                        detector++;
                        assignments++;
                    }
                }
            }
            clock = detector;
        }
        while (assignments != numberOfPairs) {
            for (int i = 0; i < numberOfPairs; i++) {
                for (int j = 0; j < numberOfPairs; j++) {
                    if (!rowCover.get(i) && !colCover.get(j)) {
                        rowCover.set(i, true);
                        colCover.set(j, true);
                        pairs.put(i,j);
                        assignments++;
                    }
                }
            }
        }
    }

    /** Method that returns true if there is a zero at position i,j in flfMatrix and there
     *  are no other zeros in the row i.
     *  @param i The row index of the place we are checking
     *  @param j The column index of the place we are checking
     *  @return True iff position i,j contains a zero, and it is the only zero in row i. */
    boolean uniqueZeroRow(int i, int j) {
        if (flfMatrix.get(i).get(j) == 0) {
            boolean onlyZero = true;
            for (int k = 0; k < numberOfPairs; k++) {
                if (flfMatrix.get(i).get(k) == 0 && k != j) {
                    onlyZero = false;
                }
            }
            if (rowCover.get(i) || colCover.get(j)) {
                onlyZero = false;
            }
            return onlyZero;
        } else {
            return false;
        }
    }

    /** Method that return true if there is a zero at position i,j in flfMatrix and there
     *  are no other zeros in the column j.
     *  @param i The row index of the place we are checking.
     *  @param j The column index of the place we are checking.
     *  @return True iff position i,j contains a zero, and it is the only zero in column j. */
    boolean uniqueZeroCol(int i, int j) {
        if (flfMatrix.get(i).get(j) == 0) {
            boolean onlyZero = true;
            for (int k = 0; k < numberOfPairs; k++) {
                if (flfMatrix.get(k).get(j) == 0 && k != i) {
                    onlyZero = false;
                }
            }
            if (rowCover.get(i) || colCover.get(j)) {
                onlyZero = false;
            }
            return onlyZero;
        } else {
            return false;
        }
    }


    /* All access methods. */
    /** @return The total FLF for the final assignments. */
    double getTotalFLF() {
        return totalFLF;
    }
    /** Method to increment the total FLF.
     *  @param num Amount to increment total FLF by. */
    void incTotalFLF(double num) {
        totalFLF += num;
    }
    /** @return The number of assignments that will be made (same as number of player). */
    int getNumberOfPairs() {
        return numberOfPairs;
    }
    /** @return The ArrayList of player Person objects. */
    ArrayList<Person> getPlayerList() {
        return players;
    }
    /** @return The ArrayList of tutor Person objects. */
    ArrayList<Person> getTutorList() {
        return tutors;
    }
    /** @return The working FLF matrix (the one altered throughout the running of the program). */
    ArrayList<ArrayList<Double>> getFLFMatrix() {
        return flfMatrix;
    }
    /** @return The original FLF matrix containing original calculated FLFs. */
    ArrayList<ArrayList<Double>> getOriginalMatrix() {
        return originalMatrix;
    }
    /** @return The HashMap of all final assignments made. */
    HashMap<Integer, Integer> getPairs() {
        return pairs;
    }
}
