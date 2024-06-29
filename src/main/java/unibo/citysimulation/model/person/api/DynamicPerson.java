package unibo.citysimulation.model.person.api;

import java.time.LocalTime;

/**
 * Represents a dynamic person that can change state based on the current time and move in order to work.
 */
/**
 * The DynamicPerson interface represents a person with dynamic behavior in a city simulation.
 * It extends the StaticPerson interface, which defines the static characteristics of a person.
 */
public interface DynamicPerson extends StaticPerson {
    /**
     * Checks if the state of the person has to change based on the current time.
     * 
     * @param currentTime the current time.
     */
    void checkState(LocalTime currentTime);
    /**
     * Sets the beginning time of the person's business.
     * 
     * @param businessBegin the beginning time of the person's business.
     */
    void setBusinessBegin(LocalTime businessBegin);
    /**
     * Sets the end time of the person's business.
     * 
     * @param businessEnd the end time of the person's business.
     */
    void setBusinessEnd(LocalTime businessEnd);
}