package unibo.citysimulation.model.business.utilities;

import java.util.LinkedList;

import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.BusinessData;

/**
 * Represents a medium-sized business in the city simulation.
 * Inherits from the Business class.
 */
public class MediumBusiness extends Business {

    /**
     * Creates a medium business in the city simulation.
     * @param id the id of the medium business
     * @param zone the zone where the medium business is located
     */
    public MediumBusiness(final int id, final Zone zone) {
        this.businessData =
        new BusinessData(
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
            zone);
    }
}
