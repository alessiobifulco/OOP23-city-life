package unibo.citysimulation.model.business.impl;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.utilities.Pair;

import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for handling business expansion and closure logic.
 */
public final class BusinessExpancionLogic {

    private BusinessExpancionLogic() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Divides the businesses into two lists: those to be closed (0% occupation)
     * and those to be expanded (100% occupation).
     *
     * @param allBusinesses the list of all businesses
     * @return a Pair containing two lists: first with businesses to be closed, second with businesses to be expanded
     */
    public static Pair<List<Business>, List<Business>> evaluateBusinesses(final List<Business> allBusinesses) {
        final List<Business> businessesToClose = new ArrayList<>();
        final List<Business> businessesToExpand = new ArrayList<>();

        allBusinesses.forEach(business -> {
            final double occupationRate = calculateOccupationRate(business);
            if (occupationRate == 0) {
                businessesToClose.add(business);
            } else if (occupationRate == 100) {
                businessesToExpand.add(business);
            }
        });

        return new Pair<>(businessesToClose, businessesToExpand);
    }

    /**
     * Calculates the occupation rate of a business.
     *
     * @param business the business to calculate the occupation rate for
     * @return the occupation rate as a percentage
     */
    private static double calculateOccupationRate(final Business business) {
        final int maxEmployees = business.getBusinessData().maxEmployees();
        final int currentEmployees = business.getBusinessData().employees().size();
        return (double) currentEmployees / maxEmployees * 100;
    }
}
