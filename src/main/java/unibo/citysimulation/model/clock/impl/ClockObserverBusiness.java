package unibo.citysimulation.model.clock.impl;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.EmploymentOfficeManager;
import unibo.citysimulation.model.business.utilities.EmploymentOfficeData;
import unibo.citysimulation.model.clock.api.ClockObserver;

import java.util.List;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A ClockObserver implementation that handles business-related operations based on time updates.
 */
public class ClockObserverBusiness implements ClockObserver {
    private final List<Business> businesses;
    private final EmploymentOfficeManager employmentManager;
    private final Map<Business, Integer> businessHiredCountMap;
    private static final LocalTime HR_TIME = LocalTime.of(0, 0);
    private static final LocalTime FR_TIME = LocalTime.of(23, 0);

    /**
     * Constructs a ClockObserverBusiness object with the given list of businesses and employment office.
     * 
     * @param businesses the list of businesses
     * @param employmentOffice the employment office
     */
    public ClockObserverBusiness(final List<Business> businesses, final EmploymentOfficeData employmentOffice) {
        this.businesses = businesses;
        this.employmentManager = new EmploymentOfficeManager(employmentOffice);
        this.businessHiredCountMap = new HashMap<>();
    }

    /**
     * Handles business operations based on the current time and day.
     * 
     * @param currentTime the current time
     * @param currentDay the current day
     */
    @Override
    public void onTimeUpdate(final LocalTime currentTime, final int currentDay) {
        for (final Business business : businesses) {
            business.checkEmployeeDelays(currentTime);
            if (currentTime.equals(FR_TIME)) {
                employmentManager.handleEmployeeHiring(business);
                businessHiredCountMap.put(business, business.getBusinessData().employees().size());
                employmentManager.handleEmployeePay(business);
            }
            if (currentTime.equals(HR_TIME)) {
                employmentManager.handleEmployeeFiring(business);
                businessHiredCountMap.put(business, business.getBusinessData().employees().size());
            }
        }
    }

    protected Map<Business, Integer> getBusinessHiredCountMap() {
        return this.businessHiredCountMap;
    }
}
