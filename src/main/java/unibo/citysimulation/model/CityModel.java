package unibo.citysimulation.model;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.clock.api.ClockModel;
import unibo.citysimulation.model.graphics.impl.GraphicsModelImpl;
import unibo.citysimulation.model.map.impl.MapModelImpl;
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
     * Create the entities of the city.
     */
    void createEntities();

    /**
     * Get the zone in which the position is located.
     * @param position
     * @return an optional of the zone in which the position is located.
     */
    Optional<Zone> getZoneByPosition(Pair<Integer, Integer> position);
    /**
     * Check if the position is in the zone.
     * @param position
     * @param zone
     * @return a boolean that is true if the position is in the zone, false otherwise.
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
     * get the number of direct lines from the zone.
     * @param zone
     * @return the number of direct lines from the zone.
     */
    int getNumberOfDirectLinesFromZone(Zone zone);
    /**
     * take the frame size.
     */
    void takeFrameSize();
    /**
     * set the screen size.
     * @param newWidth
     * @param newHeight
     */
    void setScreenSize(int newWidth, int newHeight);
    /**
     * get the map model.
     * @return the map model.
     */
    MapModelImpl getMapModel();
    /**
     * get the clock model.
     * @return the clock model.
     */
    ClockModel getClockModel();
    /**
     * get the input model.
     * @return the input model.
     */
    InputModel getInputModel();
    /**
     * get the graphics model.
     * @return the graphics model.
     */
    GraphicsModelImpl getGraphicsModel();
    /**
     * get the zones.
     * @return the list of zones.
     */
    List<Zone> getZones();
    /**
     * get the transport lines.
     * @return the list of transport lines.
     */
    List<TransportLine> getTransportLines();
    /**
     * get the businesses.
     * @return the list of businesses.
     */
    List<Business> getBusinesses();
    /**
     * get the people.
     * @return the list of all the people in the simulation.
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
     * @return the width of the frame.
     */
    int getFrameWidth();

    /**
     * Returns the height of the frame.
     *
     * @return the height of the frame.
     */
    int getFrameHeight();

    /**
     * Returns the number of people in the specified zone.
     *
     * @param zoneName The name of the zone.
     * @return The number of people in the zone, or an empty Optional if the zone does not exist.
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
     * Removes the specified number of businesses from the city.
     *
     * @param numberOfBusinesses The number of businesses to remove.
     */
    void removeBusinesses(int numberOfBusinesses);

    /**
     * Returns a random person from the city.
     *
     * @return An Optional containing a random person, or an empty Optional if there are no people in the city.
     */
    Optional<DynamicPerson> getRandomPerson();
}
