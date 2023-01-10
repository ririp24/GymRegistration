package com.example.fitnesschaingui;
/**
 * Stores locations with related information.
 * Enum class that stores the two times at which classes are available.
 * @author Albert Zou, Rishabh Patel
 */
public enum Time {
    /**
     * Morning time slot
     */
    MORNING ("09", "30"),
    /**
     * Afternoon time slot
     */
    AFTERNOON ("14", "00"),
    /**
     * Evening time slot
     */
    EVENING ("18", "30");

    private final String hour;
    private final String minute;

    /**
     * Constructs a Time object.
     * Sets the hour and minute instance variable to the corresponding
     * arguments
     * @param hour of the time.
     * @param minute of the time.
     */
    Time(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Represents the time as a string.
     * Formatted as "hh:mm"
     * @return a string representation of the time.
     */
    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}