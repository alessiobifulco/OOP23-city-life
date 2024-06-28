/**
 * Represents the model of the city simulation, containing zones, transports,
 * businesses, and people.
 */
package unibo.citysimulation.model;

import unibo.citysimulation.model.business.utilities.EmploymentOfficeData;
import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.BusinessFactoryImpl;
import unibo.citysimulation.model.clock.api.ClockModel;
import unibo.citysimulation.model.clock.impl.ClockModelImpl;
import unibo.citysimulation.model.clock.impl.ClockObserverPerson;
import unibo.citysimulation.model.clock.impl.ClockObserverBusiness;
import unibo.citysimulation.model.graphics.impl.GraphicsModelImpl;
import unibo.citysimulation.model.map.impl.MapModelImpl;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.impl.PersonFactoryImpl;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.transport.impl.TransportFactoryImpl;
import unibo.citysimulation.model.zone.Boundary;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.zone.ZoneCreation;
import unibo.citysimulation.model.zone.ZoneTableCreation;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.utilities.Pair;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Optional;
import java.util.Collections;

/**
 * Represents the model of the city simulation, containing zones, transports,
 * businesses, and people.
 */
@SuppressFBWarnings(value = "EI", justification = """
    Considering the basic structure of the application, we choose to 
    pass the mutable models as parameters, because we need to keep them always updated. In every case, we pass
    interfaces of the models.""")
public final class CityModelImpl implements CityModel {
    private final List<Zone> zones;
    private List<TransportLine> transports;
    private final List<Business> businesses;
    private List<List<DynamicPerson>> people;
    private final MapModelImpl mapModel;
    private final ClockModel clockModel;
    private final InputModel inputModel;
    private final GraphicsModelImpl graphicsModel;
    private final EmploymentOfficeData employmentOfficeData;
    private int frameWidth;
    private int frameHeight;

    /**
     * Constructs a CityModel object with default settings.
     */
    public CityModelImpl() {
        takeFrameSize();

        this.mapModel = new MapModelImpl("/unibo/citysimulation/images/mapImage.png");
        this.clockModel = new ClockModelImpl(ConstantAndResourceLoader.SIMULATION_TOTAL_DAYS);
        this.inputModel = new InputModelImpl();
        this.graphicsModel = new GraphicsModelImpl();
        this.zones = ZoneCreation.createZonesFromFile();
        this.transports = new TransportFactoryImpl().createTransportsFromFile(zones);
        this.businesses = new ArrayList<>();
        this.employmentOfficeData = new EmploymentOfficeData(new LinkedList<>());
    }

    /**
     * @return the zone from a position, if present.
     * 
     * @param position the position to check.
     */
    @Override
    public Optional<Zone> getZoneByPosition(final Pair<Integer, Integer> position) {
        return zones.stream()
                .filter(zone -> isPositionInZone(position, zone))
                .findFirst();
    }

    /**
     * @return if a position is inside a certain zone or not.
     * 
     * @param position the position to check.
     * @param zone     the zone to check.
     */
    @Override
    public boolean isPositionInZone(final Pair<Integer, Integer> position, final Zone zone) {
        final int x = position.getFirst();
        final int y = position.getSecond();
        final Boundary boundary = zone.boundary();
        return x >= boundary.getX() && x <= (boundary.getX() + boundary.getWidth())
                && y >= boundary.getY() && y <= (boundary.getY() + boundary.getHeight());
    }

    /**
     * Creates the remaining entities missing in the simulation start process.
     */
    @Override
    public void createEntities() {
        graphicsModel.clearDatasets();

        transports = new TransportFactoryImpl().createTransportsFromFile(zones);
        transports.forEach(t -> t.setCapacity(t.getCapacity() * inputModel.getCapacity() / 100));

        // Create zone table
        ZoneTableCreation.createAndAddPairs(zones, transports);

        businesses.addAll(BusinessFactoryImpl.createMultipleBusiness(zones, inputModel.getNumberOfPeople()));

        // Create people
        this.people = new ArrayList<>();
        people =  new PersonFactoryImpl().createAllPeople(getInputModel().getNumberOfPeople(), zones, businesses);

        for (final List<DynamicPerson> group : people) {
            for (final DynamicPerson person : group) {
                if (person.getBusiness().isEmpty()) {
                    employmentOfficeData.disoccupied().add(person); 
                }   
            }
        }
        clockModel.addObserver(new ClockObserverPerson(people));

        clockModel.addObserver(new ClockObserverBusiness(businesses, employmentOfficeData));
    }

    /**
     * Calculates the average pay for employees in the specified zone.
     *
     * <p>
     * This method iterates over all businesses in the city model,
     * checks if each business is in the specified zone, and sums the total pay
     * for all employees in those businesses.
     * </p>
     *
     * @param zone the zone for which to calculate the average pay
     * @return the total pay for all employees in the specified zone
     */
    @Override
    public double avaragePayZone(final Zone zone) {
        double avarage = 0;
        int businessCount = 0;
        for (final Business business : businesses) {
            if (business.getBusinessData().zone().equals(zone)) {
                businessCount++;
                double sum = 0;
                sum += business.getBusinessData().employees().size() * business.calculatePay();
                avarage = sum / businessCount;
            }
        }
        return avarage;
    }

    /**
     * Returns the number of direct lines from a zone.
     *
     * @param zone the zone to check
     * @return the number of direct lines from the zone
     */
    @Override
    public int getNumberOfDirectLinesFromZone(final Zone zone) {
        int numberOfDirectLines = 0;
        for (final TransportLine transportLine : transports) {
            if (transportLine.getLink().getFirst().equals(zone) || transportLine.getLink().getSecond().equals(zone)) {
                numberOfDirectLines++;
            }
        }
        return numberOfDirectLines;
    }

    /**
     * Adjusts the frame size based on the screen dimensions.
     */
    @Override
    public void takeFrameSize() {
        // Get the screen size
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int maxWidth = (int) (screenSize.getWidth() * ConstantAndResourceLoader.SCREEN_SIZE_PERCENTAGE);
        final int maxHeight = (int) (screenSize.getHeight() * ConstantAndResourceLoader.SCREEN_SIZE_PERCENTAGE);

        // Calculate the frame dimensions based on the maximum dimensions
        final int frameHeight = maxHeight > (maxWidth / 2) ? maxWidth / 2 : maxHeight;
        final int frameWidth = frameHeight * 2;

        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
    }

    /**
     * Sets the screen size to the specified width and height, adjusting dimensions
     * to maintain aspect ratio if needed. This method also updates the map model's
     * maximum coordinates and transport information.
     *
     * <p>
     * If both the width and height have changed, the method adjusts one of the
     * dimensions to maintain the aspect ratio based on the larger proportional
     * change.
     * If only the height changes, the width is adjusted to twice the height.
     * If only the width changes, the height is adjusted to half the width.
     * </p>
     *
     * @param newWidth  the new width of the screen
     * @param newHeight the new height of the screen
     */
    @Override
    public void setScreenSize(final int newWidth, final int newHeight) {
        final int oldWidth = frameWidth;
        final int oldHeight = frameHeight;
        int width = newWidth;
        int height = newHeight;
        final boolean widthChanged = width != oldWidth;
        final boolean heightChanged = height != oldHeight;

        if (widthChanged && heightChanged) {
            if ((double) width / oldWidth > (double) height / oldHeight) {
                height = width / 2;
            } else {
                width = newHeight * 2;
            }
        } else if (heightChanged) {
            width = height * 2;
        } else if (widthChanged) {
            height = width / 2;
        }

        frameWidth = width;
        frameHeight = height;

        mapModel.setMaxCoordinates(frameWidth / 2, frameHeight);
        mapModel.setTransportInfo(transports);
    }

    /**
     * Gets the map model associated with this city model.
     * 
     * @return The map model.
     */
    @Override
    public MapModelImpl getMapModel() {
        return this.mapModel;
    }

    /**
     * Gets the clock model associated with this city model.
     * 
     * @return The clock model.
     */
    @Override
    public ClockModel getClockModel() {
        return this.clockModel;
    }

    /**
     * @return the input model.
     */
    @Override
    public InputModel getInputModel() {
        return this.inputModel;
    }

    /**
     * @return the graphics model.
     */
    @Override
    public GraphicsModelImpl getGraphicsModel() {
        return this.graphicsModel;
    }

    /**
     * Gets the list of zones in the city model.
     * 
     * @return The list of zones.
     */
    @Override
    public List<Zone> getZones() {
        return Collections.unmodifiableList(this.zones);
    }

    /**
     * Gets the list of transport lines in the city model.
     * 
     * @return The list of transport lines.
     */
    @Override
    public List<TransportLine> getTransportLines() {
        return Collections.unmodifiableList(this.transports);
    }

    /**
     * Gets the list of businesses in the city model.
     * 
     * @return The list of businesses.
     */
    @Override
    public List<Business> getBusinesses() {
        return Collections.unmodifiableList(this.businesses);
    }

    /**
     * Gets the list of all the people in the city model.
     * 
     * @return a list with all the people from every zone of the map.
     */
    @Override
    public List<DynamicPerson> getAllPeople() {
        return people.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Checks if people are present in the city model.
     *
     * @return True if people are present, false otherwise.
     */
    @Override
    public boolean isPeoplePresent() {
        return this.people != null;
    }

    /**
     * Checks if there are businesses present in the city model.
     * 
     * @return true if there are businesses present, false otherwise.
     */
    @Override
    public boolean isBusinessesPresent() {
        return this.businesses != null;
    }

    /**
     * Gets the frame width of the city model.
     *
     * @return The frame width.
     */
    @Override
    public int getFrameWidth() {
        return this.frameWidth;
    }

    /**
     * Gets the frame height of the city model.
     *
     * @return The frame height.
     */
    @Override
    public int getFrameHeight() {
        return this.frameHeight;
    }

    /**
     * Gets the number of people residing in a specific zone.
     *
     * @param zoneName The name of the zone.
     * @return An Optional containing the number of people residing in the specified
     *         zone,
     *         or an empty Optional if people is null.
     */
    @Override
    public Optional<Integer> getPeopleInZone(final String zoneName) {
        return Optional.ofNullable(people)
                .map(pList -> pList.stream()
                        .flatMap(List::stream)
                        .filter(p -> p.getPersonData().residenceZone().name().equals(zoneName))
                        .count())
                .map(Long::intValue);
    }

    /**
     * Gets the number of businesses in a specific zone.
     *
     * @param zoneName The name of the zone.
     * @return The number of businesses in the specified zone.
     */
    @Override
    public int getBusinessesInZone(final String zoneName) {
        return (int) businesses.stream()
                .filter(b -> b.getBusinessData().zone().name().equals(zoneName))
                .count();
    }

    /**
     * Removes a specified number of businesses from the city model.
     *
     * @param numberOfBusinesses the number of businesses to remove
     */
    @Override
    public void removeBusinesses(final int numberOfBusinesses) {
        for (int i = 0; i < numberOfBusinesses; i++) {
            businesses.remove(businesses.size());
        }
    }
}
