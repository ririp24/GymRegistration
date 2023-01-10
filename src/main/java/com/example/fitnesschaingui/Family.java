package com.example.fitnesschaingui;
/**
 * Represents Family memberships at the fitness chain.
 * Inherits from Member class and adds functionality for guest passes.
 * @author Albert Zou, Rishabh Patel
 */
public class Family extends Member {
    protected int guestPasses;
    protected static final double FAMILY_FEE = 59.99;
    private static final int FAMILY_PASSES = 1;
    private static final int EMPTY = 0;

    /**
     * Constructs Family object.
     * Calls super to construct Member object, then sets expiration date
     * to the correct value and initializes guestPasses.
     * @param fName first name of the membership holder.
     * @param lName last name of the membership holder.
     * @param dob date of birth of the membership holder.
     * @param location where the membership is held.
     */
    public Family(
            String fName,
            String lName,
            Date dob,
            Location location
    ) {
        super(fName, lName, dob, new Date(), location);
        expire.addMonths(MONTHS_QUARTER);
        guestPasses = FAMILY_PASSES;
    }

    /**
     * Represents the Family membership as a string.
     * Prints information for a standard membership along with a Family
     * identifier and the number of remaining guest passes.
     * @return string containing information about the membership.
     */
    @Override
    public String toString() {
        return super.toString() + " (Family) Guest-pass remaining: "
                + guestPasses;
    }

    /**
     * Calculates the fee owed for the membership.
     * Adds the starting fee to the monthly cost of a Family membership
     * multiplied by the number of months the membership lasts for.
     * @return the membership fee.
     */
    @Override
    public double membershipFee(boolean first) {
        double output = FAMILY_FEE * MONTHS_QUARTER;
        return ( first ) ? output + STARTING_FEE : output;
    }

    /**
     * Decrements the available number of guest passes.
     * Checks whether the member has a guest pass currently is available
     * and updates the number of available passes if so.
     */
    public void useGuestPass() {
        if ( hasPass() ) {
            guestPasses--;
        }
    }

    /**
     * Gives the member back a guest pass.
     * Increases the number of available guest passes by one.
     */
    public void returnPass() {
        guestPasses++;
    }

    /**
     * Checks if the member has a pass available.
     * Compares to see if the number of available guest passes is greater
     * than zero.
     * @return true if there is at least one available guest pass, false
     * otherwise.
     */
    public boolean hasPass() {
        return guestPasses > EMPTY;
    }
}
