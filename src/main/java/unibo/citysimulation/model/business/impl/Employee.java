package unibo.citysimulation.model.business.impl;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.citysimulation.model.business.api.EmployeeBehavior;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.utilities.Pair;

/**
 * Represents an employee in the city simulation.
 */
@SuppressFBWarnings(value = "EI", justification = """
""")
public class Employee implements EmployeeBehavior {
    private final DynamicPerson person;
    private final BusinessData businessData;
    private int count;

    /**
     * Constructs a new Employee object with the given person and business data.
     * 
     * @param person       The dynamic person associated with the employee.
     * @param businessData The business data associated with the employee.
     */
    public Employee(final DynamicPerson person, final BusinessData businessData) {
        this.person = person;
        this.businessData = businessData;
        this.count = 0;
    }

    /**
     * Constructs a new Employee object with the given person, business data, and delay count.
     * 
     * @param person       The dynamic person associated with the employee.
     * @param businessData The business data associated with the employee.
     * @param count        The initial delay count of the employee.
     */
    public Employee(final DynamicPerson person, final BusinessData businessData, final int count) {
        this.person = person;
        this.businessData = businessData;
        this.count = count;
    }

    /**
     * Gets the dynamic person associated with the employee.
     * 
     * @return The dynamic person.
     */
    public DynamicPerson person() {
        return this.person;
    }

    /**
     * Gets the business data associated with the employee.
     * 
     * @return The business data.
     */
    public BusinessData businessData() {
        return this.businessData;
    }

    /**
     * Gets the delay count of the employee.
     * 
     * @return The delay count.
     */
    public int count() {
        return this.count;
    }

    /**
     * Increments the delay count of the employee.
     */
    @Override
    public void incrementDelayCount() {
        this.count++;
    }

    /**
     * Checks if the employee is late based on the business position.
     * 
     * @param businessPosition The position of the business.
     * @return true if the employee is late, false otherwise.
     */
    @Override
    public boolean isLate(final Optional<Pair<Integer, Integer>> businessPosition) {
        return !person.getPosition().equals(businessPosition);
    }
}
