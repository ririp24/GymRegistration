package com.example.fitnesschaingui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents the database of all members in all locations.
 * Allows for operations to manage members and memberships.
 * @author Albert Zou, Rishabh Patel
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;

    private static final int NOT_FOUND = -1;
    private static final int EMPTY = 0;
    private static final int BIGGER = 1;
    private static final int SORT_BY_COUNTY = 1;
    private static final int SORT_BY_EXPIRATION = 2;
    private static final int SORT_BY_NAME = 3;
    private static final int START_SIZE = 4;
    private static final int GROW_SIZE = 4;
    private static final int START = 0;
    private static final int LAST = 1;
    private static final int ADD_FIRST_NAME = 0;
    private static final int ADD_LAST_NAME = 1;
    private static final int ADD_BIRTHDAY = 2;
    private static final int ADD_EXPIRATION_DAY = 3;
    private static final int ADD_LOCATION = 4;
    private static final String MEMBERS_FILE = "src/main/resources/data/memberList.txt";

    /**
     * Constructs MemberDatabase object.
     * Stores the list of members as well as the number of members
     * in the database.
     */
    public MemberDatabase() {
        mlist = new Member[START_SIZE];
        size = START;
    }

    /**
     * Checks if the database is empty.
     * Checks if size is 0.
     * @return true if size is zero, false otherwise.
     */
    public boolean isEmpty() {
        return size == EMPTY;
    }

    /**
     * Finds an inputted member in the database.
     * Uses the Member class's .equals() method to compare.
     * @param member object to find.
     * @return the index of the member if they exist, -1 otherwise.
     */
    private int find(Member member) {
        for ( int i = 0; i < size; i++ ) {
            if ( member.equals(mlist[i]) ) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the capacity of the database by four.
     * Called when the array is full.
     * Creates new array of larger size and copies over existing data.
     */
    private void grow() {
        Member[] newList = new Member[mlist.length + GROW_SIZE];
        for ( int i = 0; i < size; i++ ) {
            newList[i] = mlist[i];
        }
        mlist = newList;
    }

    /**
     * Adds a new member to the database.
     * Doesn't add member if it's a duplicate.
     * Adds member to the array and increases size.
     * Grows the database if full.
     * @param member to add to the database.
     * @return false if member exists in database,
     * true if successfully added.
     */
    public boolean add(Member member) {
        if ( find(member) != NOT_FOUND ) return false;
        if ( size == mlist.length ) {
            grow();
        }
        mlist[size] = member;
        size++;
        return true;
    }

    /**
     * Removes an existing member from the database.
     * Doesn't remove if the member doesn't exist.
     * Stably removes user by shifting all members over in the array.
     * Decreases the count of members without ever shrinking the array.
     * @param member to remove from the database.
     * @return false if the member doesn't exist,
     * true if successfully removed.
     */
    public boolean remove(Member member) {
        int location = find(member);
        if (location == NOT_FOUND) return false;
        for ( int i = location; i < size - LAST; i++ ) {
            mlist[i] = mlist[i + 1];
        }
        mlist[size - LAST] = null;
        size--;
        return true;
    }

    /**
     * Represents the database as a string.
     * Lists all the members in their string forms.
     * @return a string representation of the database.
     */
    @Override
    public String toString() {
        if ( isEmpty() ) return "Member database is empty!";
        String output = "";
        for ( int i = 0; i < size; i++ ) {
            output += mlist[i].toString() + "\n";
        }
        return output.substring(START, output.length() - LAST);
    }

    /**
     * Represents the database along with fees due as a string.
     * Every member is listed as a string along with the fees that are
     * due for the next billing period.
     * @param first boolean denoting whether to include starting fees.
     * @return a string representation of the database with fees.
     */
    public String toStringWithFees(boolean first) {
        if ( isEmpty() ) return "Member database is empty!";
        String output = "";
        for ( int i = 0; i < size; i++ ) {
            output += mlist[i].toString() + ", Membership fee: $"
                    + mlist[i].membershipFee(first) + "\n";
        }
        return output.substring(START, output.length() - LAST);
    }

    /**
     * Gets print string for the member database.
     * Uses the toString() method.
     * @return a print string for the database
     */
    public String getPrint() {
        if ( isEmpty() ) return "Member database is empty!";
        return "\n-list of members-\n" + toString() + "\n-end of list-";
    }

    /**
     * Sorts the array of members based on inputted member property.
     * Uses unstable version of Insertion Sort.
     * @param type an integer representing which property to sort by (county,
     *           expiration date, or name).
     */
    private void insertionSort(int type) {
        for ( int i = 0; i < size - 1; i++ ) {
            for ( int j = i + 1; j < size; j++ ) {
                if ( type == SORT_BY_COUNTY
                        && mlist[i].getLocation().ordinal() > mlist[j].getLocation().ordinal()
                        || type == SORT_BY_EXPIRATION
                        && mlist[i].getExpire().compareTo(mlist[j].getExpire()) == BIGGER
                        || type == SORT_BY_NAME
                        && mlist[i].compareTo(mlist[j]) >= BIGGER ) {
                    Member temp = mlist[i];
                    mlist[i] = mlist[j];
                    mlist[j] = temp;
                }
            }
        }
    }

    /**
     * Gets print string for the database sorted by county.
     * Leaves the database sorted.
     * @return a print string for the database by county
     */
    public String getPrintByCounty() {
        if ( isEmpty() ) return "Member database is empty!";
        insertionSort(SORT_BY_COUNTY);
        return "\n-list of members sorted by county and zipcode-\n"
                + toString()+ "\n-end of list-";
    }

    /**
     * Gets print string for the database sorted by expiration date.
     * Leaves the database sorted.
     * @return a print string for the database by expiration
     */
    public String getPrintByExpiration() {
        if ( isEmpty() ) return "Member database is empty!";
        insertionSort(SORT_BY_EXPIRATION);
        return "\n-list of members sorted by membership expiration date-\n"
                + toString() + "\n-end of list-";
    }

    /**
     * Gets print string fo the database sorted by
     * last name, and first name.
     * Leaves the database sorted.
     * @return a print string for the database by name
     */
    public String getPrintByName() {
        if ( isEmpty() ) return "Member database is empty!";
        insertionSort(SORT_BY_NAME);
        return "\n-list of members sorted by last name, and first name-\n"
                + toString() + "\n-end of list-";
    }

    /**
     * Gets full member data for a partially filled member.
     * Since members are identified by their fName, lName, and dob only,
     * this method retrieves the full data (expire and location) based
     * on an input member with just the identifying properties.
     * @param member a Member object with only the identifying properties.
     * @return the corresponding member in the database with all properties.
     */
    public Member getMemberData(Member member) {
        if ( find(member) == NOT_FOUND ) {
            return null;
        }
        return mlist[find(member)];
    }

    /**
     * Loads in members from a locally stored input file.
     * Scans in entries from the MEMBERS_FILE environment variable
     * and adds them to the member database.
     */
    public void loadMembers() {
        File f = new File(MEMBERS_FILE);
        Scanner scan;
        try {
            scan = new Scanner(f);
        } catch ( FileNotFoundException e ) {
            return;
        }

        while (scan.hasNextLine() ) {
            String[] input = scan.nextLine().split(" ");
            add(new Member(input[ADD_FIRST_NAME],
                    input[ADD_LAST_NAME],
                    new Date(input[ADD_BIRTHDAY]),
                    new Date(input[ADD_EXPIRATION_DAY]),
                    Location.valueOf(input[ADD_LOCATION].toUpperCase())
            ));
        }
    }
}