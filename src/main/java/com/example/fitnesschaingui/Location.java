package com.example.fitnesschaingui;

/**
 * Stores locations with related information.
 * Enum class that stores each gym location as a town along with its zip code
 * and county.
 * @author Albert Zou, Rishabh Patel
 */
public enum Location {
    /**
     * Edison data
     */
    EDISON ("08837", "MIDDLESEX"),
    /**
     * Piscataway data
     */
    PISCATAWAY ("08854", "MIDDLESEX"),
    /**
     * Bridgewater data
     */
    BRIDGEWATER ("08807", "SOMERSET"),
    /**
     * Franklin data
     */
    FRANKLIN ("08873", "SOMERSET"),
    /**
     * Somerville data
     */
    SOMERVILLE ("08876", "SOMERSET");

    private final String zip;
    private final String county;

    /**
     * Constructs a Location object.
     * Sets zip and county instance variables to corresponding arguments.
     * @param zip code of the location.
     * @param county of the location.
     */
    Location(String zip, String county) {
        this.zip = zip;
        this.county = county;
    }

    /**
     * Represents the location as a string.
     * Includes the town, zip code, and county name of the location.
     * @return a string representation of the location.
     */
    @Override
    public String toString() {
        return this.name() + ", " + this.zip + ", " + this.county;
    }
}