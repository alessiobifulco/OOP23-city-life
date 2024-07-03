package unibo.citysimulation.model.clock.impl;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.EmploymentOfficeManager;
import unibo.citysimulation.model.business.utilities.EmploymentOfficeData;
import unibo.citysimulation.model.clock.api.ClockObserver;

import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A ClockObserver implementation specifically designed for managing businesses
 * in a city simulation.
 */
public class ClockObserverBusiness implements ClockObserver {
    private final List<Business> businesses;
    private final EmploymentOfficeManager employmentManager;
    private static final LocalTime HR_TIME = LocalTime.of(0, 0);
    private static final LocalTime FR_TIME = LocalTime.of(23, 0);

    /**
     * Constructs a ClockObserverBusiness with the specified list of businesses and
     * employment office data.
     *
     * @param businesses       the list of businesses to observe
     * @param employmentOffice the employment office data to use for managing
     *                         employees
     */
    public ClockObserverBusiness(final List<Business> businesses, final EmploymentOfficeData employmentOffice) {
        this.businesses = new ArrayList<>(businesses);
        this.employmentManager = new EmploymentOfficeManager(employmentOffice);
    }

    /**
     * Called when the time is updated in the city simulation.
     * Checks for employee delays, handles employee hiring and payment at the end of
     * the day,
     * and handles employee firing at the start of the day.
     *
     * @param currentTime the current time in the simulation
     * @param currentDay  the current day in the simulation
     */
    @Override
    public void onTimeUpdate(final LocalTime currentTime, final int currentDay) {
        for (final Business business : businesses) {
            business.checkEmployeeDelays(currentTime);
            if (currentTime.equals(FR_TIME)) {
                employmentManager.handleEmployeeHiring(business);
                employmentManager.handleEmployeePay(business);
            }
            if (currentTime.equals(HR_TIME)) {
                employmentManager.handleEmployeeFiring(business);
            }
        }
    }
}
