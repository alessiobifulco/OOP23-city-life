package unibo.citysimulation.model.business.api;

import java.time.LocalTime;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.citysimulation.model.business.impl.BusinessData;
import unibo.citysimulation.model.business.impl.Employee;

/**
 * The abstract class representing a business in the city simulation.
 */
@SuppressFBWarnings(value = "EI", justification = """
    """)
public abstract class Business implements BusinessBehavior {
    protected BusinessData businessData;

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
    public final void hire(final Employee employee) {
        if (employee != null && businessData.employees().size() < businessData.maxEmployees()) {
            businessData.employees().add(employee);
        }
    }

    /**
     * Fires an employee from the business.
     * 
     * @param employee the employee to fire
     */
    @Override
    public final void fire(final Employee employee) {
        if (employee != null && employee.count() > businessData.maxTardiness()) {
            businessData.employees().remove(employee);
        }
    }

    /**
     * Checks the delays of all employees at the current time.
     * 
     * @param currentTime the current time
     */
    @Override
    public void checkEmployeeDelays(final LocalTime currentTime) {
        if (currentTime.equals(businessData.openingTime())) {
            for (final Employee employee : businessData.employees()) {
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
        final double hoursworked = businessData.closingTime().getHour() - businessData.openingTime().getHour();
        return hoursworked * businessData.revenue();
    }
}
