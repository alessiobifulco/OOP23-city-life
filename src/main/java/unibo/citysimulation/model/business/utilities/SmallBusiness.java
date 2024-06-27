package unibo.citysimulation.model.business.utilities;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Optional;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.BusinessData;
import unibo.citysimulation.model.business.impl.Employee;
import unibo.citysimulation.model.zone.Zone;

/**
 * Represents a small business in the city simulation.
 * Inherits from the Business class.
 */
public class SmallBusiness implements Business {
    private final BusinessData businessData;

    /**
     * Creates a small business in the city simulation.
     * 
     * @param id   the id of the small business
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
                zone);
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
}
