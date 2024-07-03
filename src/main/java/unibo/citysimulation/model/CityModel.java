package unibo.citysimulation.model;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.clock.api.ClockModel;
import unibo.citysimulation.model.graphics.api.GraphicsModel;
import unibo.citysimulation.model.map.api.MapModel;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the CityModel.
 */
public interface CityModel {

    /**
     * Creates the entities in the city model, such as businesses and people.
     *
     * @param extraBusinesses The number of extra businesses to create.
     */
    void createEntities(int extraBusinesses);

    /**
     * Get the zone in which the position is located.
     *
     * @param position The position to check.
     * @return An Optional of the zone in which the position is located.
     */
    Optional<Zone> getZoneByPosition(Pair<Integer, Integer> position);

    /**
     * Check if the position is in the specified zone.
     *
     * @param position The position to check.
     * @param zone     The zone to check against.
     * @return true if the position is in the zone, false otherwise.
     */
    boolean isPositionInZone(Pair<Integer, Integer> position, Zone zone);

    /**
     * Calculates the average pay in the specified zone.
     *
     * @param zone The zone to calculate the average pay for.
     * @return The average pay in the zone.
     */
    double avaragePayZone(Zone zone);

    /**
     * Get the number of direct lines from the specified zone.
     *
     * @param zone The zone to check.
     * @return The number of direct lines from the zone.
     */
    int getNumberOfDirectLinesFromZone(Zone zone);

    /**
     * Takes the frame size of the city model.
     */
    void takeFrameSize();

    /**
     * Sets the screen size of the city model.
     *
     * @param newWidth  The new width of the screen.
     * @param newHeight The new height of the screen.
     */
    void setScreenSize(int newWidth, int newHeight);

    /**
     * Get the map model of the city.
     *
     * @return The map model.
     */
    MapModel getMapModel();

    /**
     * Get the clock model of the city.
     *
     * @return The clock model.
     */
    ClockModel getClockModel();

    /**
     * Get the input model of the city.
     *
     * @return The input model.
     */
    InputModel getInputModel();

    /**
     * Get the graphics model of the city.
     *
     * @return The graphics model.
     */
    GraphicsModel getGraphicsModel();

    /**
     * Get the list of zones in the city.
     *
     * @return The list of zones.
     */
    List<Zone> getZones();

    /**
     * Get the list of transport lines in the city.
     *
     * @return The list of transport lines.
     */
    List<TransportLine> getTransportLines();

    /**
     * Get the list of businesses in the city.
     *
     * @return The list of businesses.
     */
    List<Business> getBusinesses();

    /**
     * Get the list of all people in the simulation.
     *
     * @return The list of all people.
     */
    List<DynamicPerson> getAllPeople();

    /**
     * Checks if there are any people present in the city.
     *
     * @return true if there are people present, false otherwise.
     */
    boolean isPeoplePresent();

    /**
     * Checks if there are any businesses present in the city.
     *
     * @return true if there are businesses present, false otherwise.
     */
    boolean isBusinessesPresent();

    /**
     * Returns the width of the frame.
     *
     * @return The width of the frame.
     */
    int getFrameWidth();

    /**
     * Returns the height of the frame.
     *
     * @return The height of the frame.
     */
    int getFrameHeight();

    /**
     * Returns the number of people in the specified zone.
     *
     * @param zoneName The name of the zone.
     * @return The number of people in the zone, or an empty Optional if the zone
     *         does not exist.
     */
    Optional<Integer> getPeopleInZone(String zoneName);

    /**
     * Returns the number of businesses in the specified zone.
     *
     * @param zoneName The name of the zone.
     * @return The number of businesses in the zone.
     */
    int getBusinessesInZone(String zoneName);

    /**
     * Removes all businesses from the city.
     */
    void removeBusinesses();

    /**
     * Returns a random person from the city.
     *
     * @return An Optional containing a random person, or an empty Optional if there
     *         are no people in the city.
     */
    Optional<DynamicPerson> getRandomPerson();
}
