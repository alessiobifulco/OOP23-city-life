package unibo.citysimulation.model.business.api;

import java.util.List;
import java.util.Optional;

import unibo.citysimulation.model.business.utilities.BusinessType;
import unibo.citysimulation.model.zone.Zone;

/**
 * The BusinessFactory interface provides methods for creating businesses in a city simulation.
 */
public interface BusinessFactory {
    /**
     * Creates a business of the specified type in the given zone.
     * 
     * @param type the type of business to create
     * @param zone the zone where the business will be located
     * @return an Optional containing the created Business, or an empty Optional if creation fails
     */
    Optional<Business> createBusiness(BusinessType type, Zone zone);
    /**
     * Creates a random business in one of the specified zones.
     * 
     * @param zones the list of zones where the business can be located
     * @return an Optional containing the created Business, or an empty Optional if creation fails
     */
    Optional<Business> createRandomBusiness(List<Zone> zones);
    /**
     * Creates multiple businesses in the specified zones, with a specified number of people for each business.
     * 
     * @param zones the list of zones where the businesses will be located
     * @param numberOfPeople the number of people for each business
     * @return the created Business
     */
    Business createMultipleBusiness(List<Zone> zones, int numberOfPeople);
}
