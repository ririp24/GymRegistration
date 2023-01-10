package com.example.fitnesschaingui;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Represents fitness classes between all the facilities.
 * Stores fitnessClass objects in an array and provides functionality
 * to manipulate them.
 * @author Albert Zou, Rishabh Patel
 */
public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;

    private static final int EMPTY = 0;
    private static final int START = 0;
    private static final int LAST = 1;
    private static final int START_SIZE = 4;
    private static final int NAME = 0;
    private static final int INSTRUCTOR = 1;
    private static final int TIME = 2;
    private static final int LOCATION = 3;
    private static final String SCHEDULE_FILE = "src/main/resources/data/classSchedule.txt";
    private static final int NOT_FOUND = -1;

    /**
     * Constructor for the ClassSchedule class.
     * initializes the empty FitnessClass array and
     * numclasses variable.
     */
    public ClassSchedule() {
        classes = new FitnessClass[START_SIZE];
        numClasses = EMPTY;
    }

    /**
     * Resizes classes array when full.
     * Creates a new array with 4 more elements and
     * copies over elements from old array.
     */
    private void grow() {
        FitnessClass[] newList = new FitnessClass[classes.length
                + START_SIZE];
        for ( int i = START; i < numClasses; i++ ) {
            newList[i] = classes[i];
        }
        classes = newList;
    }

    /**
     * Represents the ClassSchedule object as a string.
     * Prints out each FitnessClass object in the array,
     * calling the FitnessClass.toString method.
     * @return string representation of all the FitnessClasses
     */
    @Override
    public String toString() {
        if ( isEmpty() ) return "Fitness class schedule is empty.";
        String output = "";
        for ( int i = START; i < numClasses; i++ ) {
            output += classes[i].toString() + "\n";
        }
        return output.substring(START, output.length() - LAST);
    }

    /**
     * Checks to see if the given name is an instructor.
     * Iterates through the elements of classes array to check
     * if any of them have the instructor.
     * @param instructor to search for
     * @return true if the instructor is found, false otherwise.
     */
    public boolean instructorExists(String instructor) {
        for ( int i = 0; i < numClasses; i++ ) {
            FitnessClass c = classes[i];
            if ( c.getInstructor().toUpperCase().equals(
                    instructor.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if given class exists anywhere.
     * Iterates through the elements of classes array to check
     * if any of them have the same name.
     * @param name of the class to search for.
     * @return true if the class exists, false otherwise.
     */
    public boolean classExists(String name) {
        for ( int i = 0; i < numClasses; i++ ) {
            FitnessClass c = classes[i];
            if ( c.getName().toUpperCase().equals(name.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a FitnessClass object matching the given parameters.
     * Iterates through the classes array, comparing the name of each
     * FitnessClass, instructor, and location to the parameters.
     * @param name of the class
     * @param instructor of the class
     * @param loc location of the class
     * @return the FitnessClass if it was found, null if there was none.
     */
    public FitnessClass getClass(String name, String instructor, Location loc) {
        for ( int i = 0; i < numClasses; i++ ) {
            FitnessClass c = classes[i];
            if ( name.toUpperCase().equals(c.getName().toUpperCase())
                    && instructor.toUpperCase().equals(c.getInstructor().toUpperCase())
                    && loc.equals(c.getLocation())) {
                return c;
            }
        }
        return null;
    }

    /**
     * Adds a new FitnessClass to the classes array.
     * First resizes the array if necessary, then inserts the new
     * FitnessClass at the end of the list of FitnessClasses.
     * @param c class to be added
     * @return false if class exists in schedule,
     * true if successfully added.
     */
    private boolean add(FitnessClass c) {
        if ( getClass(c.getName(), c.getInstructor(), c.getLocation()) != null ) return false;
        if ( numClasses >= classes.length ) {
            grow();
        }
        classes[numClasses] = c;
        numClasses++;
        return true;
    }

    /**
     * Reads in a schedule of FitnessClasses from a text file.
     * Scans through classSchedule.txt, making a FitnessClass object from
     * each row and adding it to the classes array.
     * Handles FileNotFoundException.
     */
    public void loadSchedule() {
        File f = new File(SCHEDULE_FILE);
        Scanner scan;
        try {
            scan = new Scanner(f);
        } catch ( FileNotFoundException e ) {
            return;
        }
        while ( scan.hasNextLine() ) {
            String[] input = scan.nextLine().split(" ");
            add(new FitnessClass(
                    input[NAME],
                    input[INSTRUCTOR],
                    Time.valueOf(input[TIME].toUpperCase()),
                    Location.valueOf(input[LOCATION].toUpperCase())
            ));
        }
    }

    /**
     * Checks if there are any classes currently stored.
     * Compares numClasses to zero.
     * @return true if there are no classes, false otherwise.
     */
    public boolean isEmpty() {
        return numClasses == EMPTY;
    }

    /**
     * Searches for other classes the member is signed up for at the same time
     * as the given class.
     * Iterates through all the other FitnessClass objects to see if they are
     * at the same time as the given class and the given member is in the
     * participants list for that class.
     * @param member to look for in conflicting classes.
     * @param course to find conflicts for.
     * @return FitnessClass that is conflicting, null if there are none.
     */
    public FitnessClass isTimeConflict(Member member, FitnessClass course) {
        for (int i = START; i < numClasses; i++) {
            if ( classes[i] != course ) {
                if ( classes[i].getTime().equals(course.getTime()) ) {
                    if ( classes[i].findMember(member) != NOT_FOUND ) {
                        return classes[i];
                    }
                }
            }
        }
        return null;
    }

}