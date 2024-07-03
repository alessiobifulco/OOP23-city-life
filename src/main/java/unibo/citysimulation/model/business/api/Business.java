package unibo.citysimulation.model.business.api;

import java.time.LocalTime;

import unibo.citysimulation.model.business.impl.Employee;
import unibo.citysimulation.model.business.utilities.BusinessData;
import unibo.citysimulation.model.business.utilities.BusinessType;

/**
 * The Business interface represents a business entity in the city simulation.
 * It provides methods for hiring and firing employees, checking employee
 * delays,
 * calculating pay, and retrieving business data and type.
 */
public interface Business {
    /**
     * Hires an employee for the business.
     * 
     * @param employee the employee to be hired
     */
    void hire(Employee employee);

    /**
     * Fires an employee from the business.
     * 
     * @param employee the employee to be fired
     */
    void fire(Employee employee);

    /**
     * Checks the delays of all employees based on the current time.
     * 
     * @param currentTime the current time
     */
    void checkEmployeeDelays(LocalTime currentTime);

    /**
     * Calculates the total pay for all employees in the business.
     * 
     * @return the total pay
     */
    double calculatePay();

    /**
     * Retrieves the business data.
     * 
     * @return the business data
     */
    BusinessData getBusinessData();

    /**
     * Retrieves the business type.
     * 
     * @return the business type
     */
    BusinessType getBusinessType();
}
