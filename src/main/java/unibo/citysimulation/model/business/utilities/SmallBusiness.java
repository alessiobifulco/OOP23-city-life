package unibo.citysimulation.model.business.utilities;

import java.util.LinkedList;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.BusinessData;
import unibo.citysimulation.model.zone.Zone;

/**
 * Represents a small business in the city simulation.
 * Inherits from the Business class.
 */
public class SmallBusiness extends Business {

    /**
     * Creates a small business in the city simulation.
     * @param id the id of the small business
     * @param zone the zone where the small business is located
     */
    public SmallBusiness(final int id, final Zone zone) {
        this.businessData =
        new BusinessData(
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
}
