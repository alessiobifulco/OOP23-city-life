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
 * Represents a medium-sized business in the city simulation.
 * Inherits from the Business class.
 */
public class MediumBusiness implements Business {
    private final BusinessData businessData;

    /**
     * Creates a medium business in the city simulation.
     * 
     * @param id   the id of the medium business
     * @param zone the zone where the medium business is located
     */
    public MediumBusiness(final int id, final Zone zone) {
        this.businessData = new BusinessData(
                id,
                new LinkedList<>(),
                BusinessConfig.MEDIUM_OPENING_TIME,
                BusinessConfig.MEDIUM_CLOSING_TIME,
                BusinessConfig.MEDIUM_REVENUE,
                BusinessConfig.MAX_EMPLOYEES_MEDIUM_BUSINESS,
                zone.getRandomPosition(),
                BusinessConfig.MEDIUM_MIN_AGE,
                BusinessConfig.MEDIUM_MAX_AGE,
                BusinessConfig.MEDIUM_MAX_TARDINESS,
                zone,
                BusinessType.MEDIUM);
    }

    @Override
    public void hire(final Employee employee) {
        if (employee != null && businessData.employees().size() < businessData.maxEmployees()) {
            businessData.employees().add(employee);
        }
    }

    @Override
    public void fire(final Employee employee) {
        if (employee != null && employee.count() > businessData.maxTardiness()) {
            businessData.employees().remove(employee);
        }
    }

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

    @Override
    public double calculatePay() {
        final double hoursworked = businessData.closingTime().getHour() - businessData.openingTime().getHour();
        return hoursworked * businessData.revenue();
    }

    @Override
    public BusinessData getBusinessData() {
        return businessData;
    }

    @Override
    public BusinessType getBusinessType() {
        return businessData.businessType();
    }
}
