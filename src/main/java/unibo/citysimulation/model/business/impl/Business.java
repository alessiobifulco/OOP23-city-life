package unibo.citysimulation.model.business.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import unibo.citysimulation.utilities.Pair;
import unibo.citysimulation.model.business.api.BusinessBehavior;
import unibo.citysimulation.model.zone.Zone;

/**
 * The abstract class representing a business in the city simulation.
 */
public abstract class Business implements BusinessBehavior {

    private final BusinessData businessData;
    /**
     * Record representing the data of a business in the city simulation.
     *
     * @param id           the id of the business
     * @param employees    the list of employees working in the business
     * @param opLocalTime  the opening time of the business
     * @param clLocalTime  the closing time of the business
     * @param revenue      the revenue generated by the business
     * @param maxEmployees the maximum number of employees the business can have
     * @param position     the position of the business in the city
     * @param minAge       the minimum age for employees
     * @param maxAge       the maximum age for employees
     * @param maxTardiness the maximum number of times an employee can be late before being fired
     * @param zone         the zone in which the business is located
     */
    public static record BusinessData( 
    int id,
    List<Employee> employees,
    LocalTime opLocalTime,
    LocalTime clLocalTime,
    double revenue,
    int maxEmployees,
    Pair<Integer, Integer> position,
    int minAge,
    int maxAge,
    int maxTardiness,
    Zone zone) {
    }
    /**
     * Constructs a Business object with the given BusinessData.
     *
     * @param businessData the data of the business
     */
    public Business(BusinessData businessData) {
        this.businessData = Objects.requireNonNull(businessData);
    }

    /**
     * Gets the BusinessData of this business.
     *
     * @return the BusinessData of this business
     */
    public BusinessData getBusinessData() {
        return businessData;
    }
    
    /**
     * Hires an employee for the business.
     * 
     * @param employee the employee to hire
     * @return true if the employee was hired successfully, false otherwise
     */
    @Override
    public final boolean hire(final Employee employee) {
        if (employee.person().getPersonData().age() >= businessData.minAge()
        && employee.person().getPersonData().age() <= businessData.maxAge() 
        && businessData.employees().size() < businessData.maxEmployees()) {
        businessData.employees().add(employee);
        return true;
    }
    return false;
    }

    /**
     * Fires an employee from the business.
     * 
     * @param employee the employee to fire
     */
    @Override
    public final void fire(final Employee employee) {
        if (employee != null && employee.count() > businessData.maxTardiness()) {
            businessData.employees.remove(employee);
        }
    }

    /**
     * Checks the delays of all employees at the current time.
     * 
     * @param currentTime the current time
     */
    public void checkEmployeeDelays(final LocalTime currentTime) {
        if (currentTime.equals(businessData.opLocalTime())) {
            for (Employee employee : businessData.employees()) {
                if (employee.isLate(Optional.of(businessData.position()))) {
                    employee.incrementDelayCount();
                }
            }
        }
    }

    /**
     * Calculates the total pay for the business.
     * 
     * @return the total pay for the business
     */
    @Override
    public final double calculatePay() {
        final double hoursworked = businessData.clLocalTime().getHour() - businessData.opLocalTime.getHour();
        return hoursworked * businessData.revenue;
    }
}
