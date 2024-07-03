package unibo.citysimulation.model.business.impl;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Optional;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.utilities.BusinessConfig;
import unibo.citysimulation.model.business.utilities.BusinessData;
import unibo.citysimulation.model.business.utilities.BusinessType;
import unibo.citysimulation.model.zone.Zone;

/**
 * A small business implementation of the {@link Business} interface.
 */
public final class SmallBusiness implements Business {
    private final BusinessData businessData;

    /**
     * Constructs a new SmallBusiness object with the given ID and zone.
     *
     * @param id   the ID of the small business
     * @param zone the zone where the small business is located
     */
    public SmallBusiness(final int id, final Zone zone) {
        this.businessData = new BusinessData(
                id,
                new LinkedList<>(),
                BusinessConfig.SMALL_OPENING_TIME,
                BusinessConfig.SMALL_CLOSING_TIME,
                BusinessConfig.SMALL_REVENUE,
                BusinessConfig.MAX_EMPLOYEES_SMALL_BUSINESS,
                zone.getRandomPosition(),
                BusinessConfig.SMALL_MIN_AGE,
                BusinessConfig.SMALL_MAX_AGE,
                BusinessConfig.SMALL_MAX_TARDINESS,
                zone,
                BusinessType.SMALL);
    }

    /**
     * Hires an employee for the small business.
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
     * Fires an employee from the small business.
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
     * Checks the delays of the employees at the opening time of the small business.
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
     * Calculates the total pay for the small business.
     *
     * @return the total pay
     */
    @Override
    public double calculatePay() {
        final double hoursworked = businessData.closingTime().getHour() - businessData.openingTime().getHour();
        return hoursworked * businessData.revenue();
    }

    /**
     * Gets the business data of the small business.
     *
     * @return the business data
     */
    @Override
    public BusinessData getBusinessData() {
        return businessData;
    }

    /**
     * Gets the business type of the small business.
     *
     * @return the business type
     */
    @Override
    public BusinessType getBusinessType() {
        return businessData.businessType();
    }
}
