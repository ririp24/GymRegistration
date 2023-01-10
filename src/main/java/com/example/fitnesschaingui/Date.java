package com.example.fitnesschaingui;
import java.util.Calendar;
/**
 * Represents dates.
 * Stores month, day, and year as separate values and provides basic
 * functionalities.
 * @author Albert Zou, Rishabh Patel
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int INDEX = 1;
    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int APR = 4;
    private static final int MAY = 5;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int SEP = 9;
    private static final int OCT = 10;
    private static final int NOV = 11;
    private static final int DEC = 12;
    private static final int START_DAY = 1;
    private static final int LONG_END_DAY = 31;
    private static final int SHORT_END_DAY = 30;
    private static final int LEAP_FEB_END_DAY = 29;
    private static final int FEB_END_DAY = 28;
    private static final int MONTH = 0;
    private static final int DAY = 1;
    private static final int YEAR = 2;
    private static final int DIVISIBLE = 0;
    private static final int START = 0;
    private static final int MONTHS_IN_YEAR = 12;
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;

    /**
     * Constructs Date object representing the current day.
     * Uses Calendar.getInstance() to set day, month, and year according to
     * the date at the time of construction.
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH)+1;
        day = today.get(Calendar.DATE);
    }

    /**
     * Constructs Date object from the given string
     * Splits the string into month, day, and year values in that order using
     * '/' as the delimiter.
     * @param date string to parse into a Date object.
     */
    public Date(String date) {
        String[] parts = date.split("/");
        month = Integer.parseInt(parts[MONTH]);
        day = Integer.parseInt(parts[DAY]);
        year = Integer.parseInt(parts[YEAR]);
    }

    /**
     * Compares two dates chronologically.
     * Compares years first, months if they are equal, and days if months are
     * equal.
     * @param date to compare to current Date object.
     * @return 1 if current Date object comes after specified date,
     * 0 if equal, -1 if current Date object comes before.
     */
    @Override
    public int compareTo(Date date) {
        if ( year > date.year) {
            return 1;
        }
        if (year < date.year) {
            return -1;
        }
        if ( month > date.month) {
            return 1;
        }
        if (month < date.month) {
            return -1;
        }
        if ( day > date.day) {
            return 1;
        }
        if (day < date.day) {
            return -1;
        }
        return 0;
    }

    /**
     * Checks if the current Date object exists.
     * Checks the Date object against the values possible in the Gregorian
     * Calendar after the death of our Lord and Saviour, Jesus Christ.
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        if ( year < 0 ) return false;
        if ( month < JAN
                || month > DEC
                || day < START_DAY
                || day > LONG_END_DAY ) {
            return false;
        }
        if ( ( month == APR
                || month == JUN
                || month == SEP
                || month == NOV )
                && day > SHORT_END_DAY) {
            return false;
        }
        if ( month == FEB ) {
            if ( isLeapYear() && day > LEAP_FEB_END_DAY
                    || ! isLeapYear() && day > FEB_END_DAY ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Represents the date as a string.
     * date is represented as ints representing month, day, and year separated
     * by '/'
     * @return string representation of the date
     */
    @Override
    public String toString() {
        String result = month + "/" + day + "/" + year;
        return result;
    }

    public static String convertFromLocal (String local) {
        String[] date = local.split("-");
        String tmp = date[START];
        for (int i = START; i < date.length-1; i++) {
            date[i] = date[i+1];
        }
        date[date.length-1] = tmp;
        return String.join("/", date);
    }

    /**
     * Checks if the current Date object is in a leap year.
     * Checks if the year is divisible by 4 and not divisible by 100, unless
     * it is also divisible by 400.
     * @return true if it is a leap year, false otherwise.
     */
    private boolean isLeapYear() {
        if ( year % QUADRENNIAL != DIVISIBLE
                || ( year % CENTENNIAL == DIVISIBLE
                && year % QUATERCENTENNIAL != DIVISIBLE ) ) {
            return false;
        }
        return true;
    }

    /**
     * Adds the given number of months to the Date object.
     * Rounds down to the end of the last month if adding yields
     * an invalid date.
     * @param months to add to the date
     */
    public void addMonths(int months) {
         month += months;
         year += month / MONTHS_IN_YEAR;
         month--;
         month %= MONTHS_IN_YEAR;
         month++;
         while ( ! isValid() && day >= FEB_END_DAY) {
             day--;
         }
    }

    /**
     * Returns day of the month of the current Date object.
     * @return day of the month as an int.
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns month of the year of the current Date object.
     * @return month of the year as an int.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns year of the current Date object.
     * @return year as an int.
     */
    public int getYear() {
        return year;
    }
}