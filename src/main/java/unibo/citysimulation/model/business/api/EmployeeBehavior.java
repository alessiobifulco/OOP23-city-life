package unibo.citysimulation.model.business.api;

import java.util.Optional;

import unibo.citysimulation.model.business.utilities.BusinessData;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.utilities.Pair;

/**
 * This interface represents employye behavior.
 */
public interface EmployeeBehavior {
    /**
     * Increments the count of delays for the employee by 1.
     *
     */
    void incrementDelayCount();

    /**
     * Checks if the employee is late based on the given business position.
     *
     * @param businessPosition The business position to check against.
     * @return True if the employee is late, false otherwise.
     */
    boolean isLate(Optional<Pair<Integer, Integer>> businessPosition);
     /**
     * Gets the dynamic person associated with the employee.
     * 
     * @return The dynamic person.
     */
    DynamicPerson person();

    /**
     * Gets the business data associated with the employee.
     * 
     * @return The business data.
     */
    BusinessData businessData();

    /**
     * Gets the delay count of the employee.
     * 
     * @return The delay count.
     */
    int count();
}
