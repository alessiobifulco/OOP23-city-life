package unibo.citysimulation.model.business.utilities;

import java.time.LocalTime;

/**
 * The {@code BusinessConfig} class contains constants and configurations related to businesses in the city simulation.
 */
public final class BusinessConfig {
    /**
     * The start time for big businesses.
     */
    public static final int START_TIME_BIG_BUSINESS = 12;
    /**
     * The end time for big businesses.
     */
    public static final int END_TIME_BIG_BUSINESS = 21;
    /**
     * The start time for medium businesses.
     */
    public static final int START_TIME_MEDIUM_BUSINESS = 17;
    /**
     * The end time for medium businesses.
     */
    public static final int END_TIME_MEDIUM_BUSINESS = 23;
    /**
     * The start time for small businesses.
     */
    public static final int START_TIME_SMALL_BUSINESS = 8;
    /**
     * The end time for small businesses.
     */
    public static final int END_TIME_SMALL_BUSINESS = 13;
    /**
     * The number of minutes.
     */
    public static final int MINUTES = 0;
    /**
     * The revenue for big businesses.
     */
    public static final double BIG_REVENUE = 25.0;
    /**
     * The revenue for medium businesses.
     */
    public static final double MEDIUM_REVENUE = 12.0;
    /**
     * The revenue for small businesses.
     */
    public static final double SMALL_REVENUE = 7.5;
    /**
     * The opening time for big businesses.
     */
    public static final LocalTime BIG_OPENING_TIME = LocalTime.of(START_TIME_BIG_BUSINESS, MINUTES);
    /**
     * The closing time for big businesses.
     */
    public static final LocalTime BIG_CLOSING_TIME = LocalTime.of(END_TIME_BIG_BUSINESS, MINUTES);
    /**
     * The opening time for medium businesses.
     */
    public static final LocalTime MEDIUM_OPENING_TIME = LocalTime.of(START_TIME_MEDIUM_BUSINESS, MINUTES);
    /**
     * The closing time for medium businesses.
     */
    public static final LocalTime MEDIUM_CLOSING_TIME = LocalTime.of(END_TIME_MEDIUM_BUSINESS, MINUTES);
    /**
     * The opening time for small businesses.
     */
    public static final LocalTime SMALL_OPENING_TIME = LocalTime.of(START_TIME_SMALL_BUSINESS, MINUTES);
    /**
     * The closing time for small businesses.
     */
    public static final LocalTime SMALL_CLOSING_TIME = LocalTime.of(END_TIME_SMALL_BUSINESS, MINUTES);
    /**
     * The maximum number of employees for big businesses.
     */
    public static final int MAX_EMPLOYEES_BIG_BUSINESS = 25;
    /**
     * The maximum number of employees for medium businesses.
     */
    public static final int MAX_EMPLOYEES_MEDIUM_BUSINESS = 10;
    /**
     * The maximum number of employees for small businesses.
     */
    public static final int MAX_EMPLOYEES_SMALL_BUSINESS = 5;
    /**
     * The maximum tardiness for big businesses.
     */
    public static final int BIG_MAX_TARDINESS = 2;
    /**
     * The maximum tardiness for medium businesses.
     */
    public static final int MEDIUM_MAX_TARDINESS = 5;
    /**
     * The maximum tardiness for small businesses.
     */
    public static final int SMALL_MAX_TARDINESS = 9;
    /**
     * The minimum age for employees in big businesses.
     */
    public static final int BIG_MIN_AGE = 20;
    /**
     * The minimum age for employees in medium businesses.
     */
    public static final int MEDIUM_MIN_AGE = 27;
    /**
     * The minimum age for employees in small businesses.
     */
    public static final int SMALL_MIN_AGE = 18;
    /**
     * The maximum age for employees in big businesses.
     */
    public static final int BIG_MAX_AGE = 60;
    /**
     * The maximum age for employees in medium businesses.
     */
    public static final int MEDIUM_MAX_AGE = 50;
    /**
     * The maximum age for employees in small businesses.
     */
    public static final int SMALL_MAX_AGE = 29;
    /**
     * The percentage of businesses in the city.
     */
    public static final int BUSINESS_PERCENTAGE = 10;

    private BusinessConfig() { }
}
