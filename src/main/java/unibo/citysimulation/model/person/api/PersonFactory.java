package unibo.citysimulation.model.person.api;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;

import java.util.List;
import java.util.Optional;

/**
 * A factory for a single person, a group of person for a certain zone,
 * or for all the people needed in the simulation.
 */
public interface PersonFactory {

    /**
     * Creates all the people for the simulation.
     *
     * @param numberOfPeople The total number of people to create, given in input.
     * @param zones          The list of available zones.
     * @param businesses     The list of available businesses.
     * @return A list of lists of DynamicPerson objects for every zone.
     */
    List<List<DynamicPerson>> createAllPeople(int numberOfPeople, List<Zone> zones, List<Business> businesses);

    /**
     * Creates a group of people for a certain zone.
     * 
     * @param groupCounter   a counter for the group of people.
     * @param numberOfPeople The number of people to create for the given zone.
     * @param moneyMinMax    The minimum and maximum amount of money that the people
     *                       can have in that zone.
     * @param businesses     The list of available businesses.
     * @param residenceZone  The zone where this group of people will live.
     * @return A list of DynamicPerson objects for the given zone.
     */
    List<DynamicPerson> createGroupOfPeople(int groupCounter, int numberOfPeople, Pair<Integer, Integer> moneyMinMax,
            List<Business> businesses, Zone residenceZone);

    /**
     * Creates a single person.
     * 
     * @param name          The name of the person.
     * @param age           The age of the person.
     * @param business      The business where the person works, if any.
     * @param residenceZone The zone where the person lives.
     * @param money         The amount of money that the person has at the creation
     *                      moment.
     * @return A DynamicPerson object.
     */
    DynamicPerson createPerson(String name, int age, Optional<Business> business, Zone residenceZone, int money);
}
