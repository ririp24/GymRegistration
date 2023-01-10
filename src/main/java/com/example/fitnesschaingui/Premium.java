package com.example.fitnesschaingui;

/**
 * Represents the premium membership at the fitness chain.
 * Allows for the validation and storage information.
 * Contains specifications for the premium membership.
 * @author Albert Zou, Rishabh Patel
 */
public class Premium extends Family {
    protected static final int PREMIUM_PASSES = 3;
    protected static final int MONTHS_YEAR = 12;
    private static final int MONTHS_WAIVED = 1;

    /**
     * Constructs Premium object.
     * Calls parent constructor and sets expiration date and
     * number of guest passes based on the premium specifications.
     * @param fName the first name of the member.
     * @param lName the last name of the member.
     * @param dob the date of birth of the member.
     * @param location the location where guests of the member can take classes at.
     */
    public Premium(
            String fName,
            String lName,
            Date dob,
            Location location
    ) {
        super(fName, lName, dob, location);
        expire = new Date();
        expire.addMonths(MONTHS_YEAR);
        guestPasses = PREMIUM_PASSES;
    }

    /**
     * Represents the premium member as a String.
     * Includes the number of guest passes remaining.
     * @return string representation of the premium member
     */
    @Override
    public String toString() {
        return super.toString().split("\\(Family\\)")[0]
                + "(Premium) Guest-pass remaining: " + guestPasses;
    }

    /**
     * Gets the membership fee for premium members.
     * @return the premium membership fee.
     */
    @Override
    public double membershipFee(boolean first) {
        return FAMILY_FEE * (MONTHS_YEAR - MONTHS_WAIVED);
    }
}