package unibo.citysimulation.model.business.utilities;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Optional;

import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.BusinessData;
import unibo.citysimulation.model.business.impl.Employee;

/**
 * Represents a big business in the city simulation.
 * Extends the Business class.
 */
public class BigBusiness implements Business {
    private final BusinessData businessData;

    /**
     * Creates a big business in the city simulation.
     * 
     * @param id   the id of the big business
     * @param zone the zone where the big business is located
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
