package com.example.fitnesschaingui;
import java.util.ArrayList;
/**
 * Represents the fitness classes available at the gym.
 * Allows for managing rosters of each class.
 * @author Albert Zou, Rishabh Patel
 */
public class FitnessClass {
    private ArrayList<Member> participants;
    private ArrayList<Member> guests;
    private String name;
    private String instructor;
    private Time time;
    private Location location;
    private static final int NOT_FOUND = -1;
    private static final int EMPTY = 0;
    private static final int START = 0;

    /**
     * Constructs FitnessClass object.
     * Initializes time, instructor, location, and name of class.
     * Initializes empty lists of participants and guests.
     * @param name of the class.
     * @param instructor of the class.
     * @param time of the class.
     * @param location of the location.
     */
    public FitnessClass(String name, String instructor, Time time,
                        Location location) {
        participants = new ArrayList<Member>();
        guests = new ArrayList<Member>();
        this.name = name;
        this.instructor = instructor;
        this.time = time;
        this.location = location;
    }

    /**
     * toString method for the class.
     * Generates a string containing participants and guests in the class.
     * @return string containing participants and guests of the class.
     */
    @Override
    public String toString() {
        String output = "";
        output += name + " - " + instructor + ", " + time + ", " + location.name();
        if ( participants.size() != EMPTY ) {
            output += "\n- Participants -";
            for (int i = START; i < participants.size(); i++) {
                output += "\n\t" + participants.get(i);
            }
        }
        if ( guests.size() != EMPTY ) {
            output += "\n- Guests -";
            for (int i = START; i < guests.size(); i++) {
                output += "\n\t" + guests.get(i);
            }
        }

        return output;
    }

    /**
     * Gets the full name of the class.
     * Includes name, instructor, time, and location.
     * @return String of the full name of the class.
     */
    public String fullName() {
        return name + " - " + instructor + ", " + time + ", " + location;
    }

    /**
     * Locates a member in the given fitness class.
     * Iterates through the participants list.
     * @param member to search for.
     * @return index in the participants array if found, -1 if not found.
     */
    public int findMember(Member member) {
        for ( int i = START; i < participants.size(); i++ ) {
            if ( member.equals(participants.get(i)) ) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Locates a guest in the given fitness class.
     * Iterates through the guests list.
     * @param member to search for as a host of the guest.
     * @return index in the guests array if found, -1 if not found.
     */
    private int findGuest(Member member) {
        for ( int i = START; i < guests.size(); i++ ) {
            if ( member.equals(guests.get(i)) ) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Checks if the member is taking a class at a valid location.
     * Allows all family and premium memberships to go to any location.
     * Standard members can only go to their location.
     * @param member to check location of.
     * @return true if the member is at a valid location, false otherwise.
     */
    public boolean validLoc(Member member) {
        if ( member instanceof Family) {
            return true;
        }
        return member.getLocation() == getLocation();
    }

    /**
     * Checks if the member's guest is taking a class at a valid location.
     * Guests can only go to their host's location.
     * @param member whose guest's location is being checked.
     * @return true if guest is at the right location, false otherwise.
     */
    public boolean validGuestLoc(Member member) {
        return member.getLocation() == getLocation();
    }

    /**
     * Checks member into this class.
     * If member is not already signed up for the class, adds member to the
     * corresponding participants list. Updates size and grows if necessary.
     * @param member to check in
     * @return true if member was not checked in before, false otherwise
     */
    public boolean checkIn(Member member) {
        if (member.isExpired()) {
            return false;
        } else if (!validLoc(member)) {
            return false;
        } else {
            if (findMember(member) == NOT_FOUND) {
                participants.add(member);
                return true;
            }
            return false;
        }
    }

    /**
     * Checks in a guest into this class.
     * If the member is standard, they can't have a guest.
     * The guest can only check in to a class that's location
     * is the same as the guest's host.
     * @param member who is bringing a guest
     * @return true if guest has been checked in successfully, false otherwise.
     */
    public boolean checkInGuest(Member member) {
        if (! (member instanceof Family) ) {
            return false;
        } else if ( ! validGuestLoc(member) ) {
            return false;
        } else if ( ! ((Family) member).hasPass() ) {
            return false;
        }else {
            guests.add(member);
            ((Family) member).useGuestPass();
            return true;
        }
    }


    /**
     * Removes member from the participants list of this class.
     * Finds member's index in class list, then shifts the rest of the members
     * left and sets the previously last member in the list to null.
     * Decrements size. Does nothing if member was not checked in to
     * this class.
     * @param member to be done with the class
     * @return true if member was originally checked in, false otherwise.
     */
    public boolean done(Member member) {
        int memberLoc = findMember(member);
        if (memberLoc != NOT_FOUND) {
            participants.remove(memberLoc);
            return true;
        }
        return false;
    }

    /**
     * Removes member from the guests list of this class.
     * Only removes member if the guest was checked in already.
     * @param member whose guest is done with the class.
     * @return true if guest was successfully removed, false otherwise.
     */
    public boolean doneGuest(Member member) {
        int memberLoc = findGuest(member);
        if ( memberLoc != NOT_FOUND ) {
            guests.remove(memberLoc);
            ((Family) member).returnPass();
            return true;
        }
        return false;
    }

    /**
     * Gets name of the class.
     * @return the name of the class.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the instructor of the class.
     * @return the instructor of the class.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Gets the time of the class.
     * @return the time of the class.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Gets the location of the class.
     * @return the location of the class.
     */
    public Location getLocation() {
        return location;
    }
}