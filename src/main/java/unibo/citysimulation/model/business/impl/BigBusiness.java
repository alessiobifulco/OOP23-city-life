package unibo.citysimulation.model.business.impl;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Optional;

import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.utilities.BusinessConfig;
import unibo.citysimulation.model.business.utilities.BusinessData;
import unibo.citysimulation.model.business.utilities.BusinessType;

/**
 * Represents a big business in the city simulation.
 */
public class BigBusiness implements Business {
    private final BusinessData businessData;

    /**
     * Constructs a new BigBusiness object with the given ID and zone.
     *
     * @param id   the ID of the business
     * @param zone the zone where the business is located
     */
    public BigBusiness(final int id, final Zone zone) {
        this.businessData = new BusinessData(
                id,
                new LinkedList<>(),
                BusinessConfig.BIG_OPENING_TIME,
                BusinessConfig.BIG_CLOSING_TIME,
                BusinessConfig.BIG_REVENUE,
                BusinessConfig.MAX_EMPLOYEES_BIG_BUSINESS,
                zone.getRandomPosition(),
                BusinessConfig.BIG_MIN_AGE,
                BusinessConfig.BIG_MAX_AGE,
                BusinessConfig.BIG_MAX_TARDINESS,
                zone,
                BusinessType.BIG);
    }

    /**
     * Hires an employee for the business.
     *
     * @param employee the employee to hire
     */
    @Override
    public void hire(final Employee employee) {
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
    public void fire(final Employee employee) {
        if (employee != null && employee.count() > businessData.maxTardiness()) {
            businessData.employees().remove(employee);
        }
    }

    /**
     * Checks the delays of the employees at the current time.
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
     * @return the total pay
     */
    @Override
    public double calculatePay() {
        final double hoursworked = businessData.closingTime().getHour() - businessData.openingTime().getHour();
        return hoursworked * businessData.revenue();
    }

    /**
     * Gets the business data.
     *
     * @return the business data
     */
    @Override
    public BusinessData getBusinessData() {
        return businessData;
    }

    /**
     * Gets the business type.
     *
     * @return the business type
     */
    @Override
    public BusinessType getBusinessType() {
        return businessData.businessType();
    }
}
