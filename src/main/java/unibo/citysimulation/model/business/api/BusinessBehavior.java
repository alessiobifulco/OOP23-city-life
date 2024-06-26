package unibo.citysimulation.model.business.api;

import java.time.LocalTime;

import unibo.citysimulation.model.business.impl.Employee;

/**
 * The interface representing a business behavior.
 */
public interface BusinessBehavior {
    
    void hire(Employee employee);
    /**
     * Fires an employee from the business.
     * 
     * @param employee the employee to be fired
     */
    void fire(Employee employee);

    /**
     * Checks the delays of the employee based on the current time.
     * 
     * @param currentTime the current time
     */
    void checkEmployeeDelays(LocalTime currentTime);

    /**
     * Calculates the pay for the employee.
     * 
     * @return the pay amount
     */
    double calculatePay();
}
