package com.example.fitnesschaingui;
/**
 * Represents a member at the fitness chain.
 * Allows for validation and storage of member information.
 * @author Albert Zou, Rishabh Patel
 */
public class Member implements Comparable<Member>{
    protected String fName;
    protected String lName;
    protected Date dob;
    protected Date expire;
    protected Location location;
    protected double fee;

    protected static final int SAME = 0;
    protected static final int ADULT_AGE = 18;
    private static final double STANDARD_FEE = 39.99;
    protected static final int MONTHS_QUARTER = 3;
    protected static final double STARTING_FEE = 29.99;

    /**
     * Constructs Member object given expiration date.
     * Stores identifying information about the member based
     * on the parameters.
     * @param fName the first name of the member.
     * @param lName the last name of the member.
     * @param dob the date of birth of the member.
     * @param expire the expiration date of the member's membership.
     * @param location the location of the member's gym.
     */
    public Member(
            String fName,
            String lName,
            Date dob,
            Date expire,
            Location location
    ) {
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
        this.fee = STANDARD_FEE;
    }

    /**
     * Constructs Member object for a new standard membership.
     * Stores identifying information about the member based
     * on the parameters, sets the expiration date for 3 months from
     * the current date.
     * @param fName the first name of the member.
     * @param lName the last name of the member.
     * @param dob the date of birth of the member.
     * @param location the location of the member's gym.
     */
    public Member(
            String fName,
            String lName,
            Date dob,
            Location location
    ) {
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.expire = new Date();
        this.expire.addMonths(MONTHS_QUARTER);
        this.location = location;
        this.fee = STANDARD_FEE;
    }

    /**
     * Checks if the member's date of birth is valid.
     * The date of birth needs to be before the current date.
     * @return true if the person was born before today, false otherwise.
     */
    public boolean isValidDOB() {
        int compare = dob.compareTo(new Date());
        return compare < 0;
    }

    /**
     * Checks if the member is 18 years old or older.
     * First checking if the birth year is more than 18 years before today.
     * If it's a close call, checks specific birth month and day.
     * @return true if the member is 18 or older, false otherwise.
     */
    public boolean isAdult() {
        Date today = new Date();
        int years = today.getYear() - dob.getYear();
        if ( years > ADULT_AGE ) {
            return true;
        }
        if ( years == ADULT_AGE ) {
            int months = today.getMonth() - dob.getMonth();
            if ( months > SAME) {
                return true;
            }
            if ( months == SAME ) {
                int days = today.getDay() - dob.getDay();
                if ( days >= 0 ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the member's membership is expired.
     * Compares to see if the member's expiration date is before today.
     * @return true if the member's membership is expired, false otherwise.
     */
    public boolean isExpired() {
        Date today = new Date();
        if ( expire.compareTo( today ) > 0){
            return false;
        }
        return true;
    }

    /**
     * Returns the membership fee for standard members.
     * @return the standard membership fee.
     */
    public double membershipFee(boolean first) {
        double output = STANDARD_FEE * MONTHS_QUARTER;
        return ( first ) ? output + STARTING_FEE : output;
    }

    /**
     * Represents the member as a String.
     * Lists the member's properties with labels.
     * @return the string representation of the member.
     */
    @Override
    public String toString() {
        String expireTense = isExpired() ? "expired" : "expires";
        return fName + " " + lName + ", DOB: " + dob
                + ", Membership " + expireTense + ": "
                + expire + ", Location: " + location;
    }

    /**
     * Checks if another member is identical to itself
     * Checks to see if all the properties in this member matches
     * those of the other object.
     * Is not case-specific for names.
     * @param obj an object to be compared with this object.
     * @return true if members are identical, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if ( ! ( obj instanceof Member ) ) {
            return false;
        }
        Member o = (Member) obj;
        return ( fName.toUpperCase().equals(o.getFName().toUpperCase())
                && lName.toUpperCase().equals(o.getLName().toUpperCase())
                && dob.compareTo(o.getDOB()) == SAME );
    }

    /**
     * Compares the names of current member and another member lexicographically.
     * Compares by last name first, then first names.
     * Is not case-specific for names.
     * @param member a member to be compared to with the current one.
     * @return 1 if the current member comes after the other lexicographically,
     * 0 if the two members have the same first and last name,
     * -1 if the current member comes before the other lexicographically.
     */
    @Override
    public int compareTo(Member member) {
        int ans = lName.toUpperCase().compareTo(member.getLName().toUpperCase());
        if ( ans == SAME ) {
            ans = fName.toUpperCase().compareTo(member.getFName().toUpperCase());
        }
        return ans;
    }

    /**
     * Gets first name of the member.
     * @return the fName property.
     */
    public String getFName() {
        return fName;
    }

    /**
     * Gets last name of the member.
     * @return the lName property.
     */
    public String getLName() {
        return lName;
    }

    /**
     * Gets the date of birth of the member.
     * @return the dob property.
     */
    public Date getDOB() {
        return dob;
    }

    /**
     * Gets the expiration date of the member.
     * @return the expire property.
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * Gets the gym location of the member.
     * @return the location property.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets expiration date.
     * @param date to set as expiration date.
     */
    public void setExpire(Date date) {
        expire = date;
    }
}
