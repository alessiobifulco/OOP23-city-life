package unibo.citysimulation.utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A utility class that contains constants and methods for loading resources.
 */
public final class ConstantAndResourceLoader extends ClassLoader {

    /**
     * The application name.
     */
    public static final String APPLICATION_NAME = "City Simulation";

    /**
     * The application version.
     */
    public static final String APPLICATION_VERSION = "0.1";

    /**
     * Screen size percentage.
     */
    public static final double SCREEN_SIZE_PERCENTAGE = 0.90;

    /**
     * Number of minutes in a second of simulation time.
     */
    public static final int MINUTES_IN_A_SECOND = 5;

    /**
     * Rate at which the simulation time updates, in milliseconds.
     */
    public static final int TIME_UPDATE_RATE = 500;

    /**
     * Total number of days in the simulation.
     */
    public static final int SIMULATION_TOTAL_DAYS = 365;

    /**
     * Array of available simulation speeds.
     */
    public static final List<Integer> SPEEDS = Collections.unmodifiableList(Arrays.asList(1, 2, 10, 20));

    /**
     * Minimum screen width in pixels.
     */
    public static final int SCREEN_MINIMUM_WIDTH_PIXEL = 1000;

    /**
     * Minimum screen height in pixels.
     */
    public static final int SCREEN_MINIMUM_HEIGHT_PIXEL = 500;

    /**
     * Minimum number of people in the simulation.
     */
    public static final int MIN_PEOPLE = 100;

    /**
     * Maximum number of people in the simulation.
     */
    public static final int MAX_PEOPLE = 1000;

    /**
     * Maximum number of columns in the grapihcs abscissa.
     */
    public static final int MAX_COLUMNS = 50;

    /**
     * Maximum deviation range for random values.
     */
    public static final int MAX_DEVIATION_RANGE = 41;

    /**
     * Maximum deviation offset for random values.
     */
    public static final int MAX_DEVIATION_OFFSET = 20;

    /**
     * Congestion value threshold.
     */
    public static final int CONGESTION_VALUE = 98;

    /**
     * Maximum variation in moving time.
     */
    public static final int MAX_MOVING_TIME_VARIATION = 12;

    /**
     * Number of seconds in a minute.
     */
    public static final int SECONDS_IN_A_MINUTE = 60;

    /**
     * Number of seconds in a day.
     *
     */
    public static final int SECONDS_IN_A_DAY = 86_400;

    /**
     * Maximum random age for people in the simulation.
     */
    public static final int MAX_RANDOM_AGE = 62;

    /**
     * Minimum age for people in the simulation.
     */
    public static final int MIN_AGE = 18;

    /**
     * Width of the clock panel in pixels.
     */
    public static final int CLOCK_PANEL_PANEL_WIDTH = 100;

    /**
     * Height of the clock panel in pixels.
     */
    public static final int CLOCK_PANEL_PANEL_HEIGHT = 50;

    /**
     * Font size for the clock panel.
     */
    public static final int CLOCK_PANEL_FONT_SIZE = 15;

    /**
     * Width of the map panel in pixels.
     */
    public static final int WELCOME_SCREEN_MIN_WIDTH = 400;

    /**
     * Height of the map panel in pixels.
     */
    public static final int WELCOME_SCREEN_MIN_HEIGHT = 200;

    /**
     * Width of the info panel in pixels.
     */
    public static final int INFO_PANEL_FONT_SIZE = 20;

    /**
     * Maximum number of businesses in the simulation.
     */
    public static final int MAX_BUSINESS = 100;

    /**
     * Minimum number of businesses in the simulation.
     */
    public static final int MIN_BUSINESS = 0;
    /**
     * @return Returns a copy of the SPEEDS array.
     */

    public static final int PERC_BUSINESS = 10;

    public static List<Integer> getSpeeds() {
        return SPEEDS;
    }
}
