package unibo.citysimulation.model;

import unibo.citysimulation.model.business.utilities.EmploymentOfficeData;
import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.api.BusinessFactory;
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
import java.util.Random;
import java.util.Collections;
import java.util.Iterator;

/**
 * Implementation of the CityModel interface.
 * This class represents the core model of the city simulation, managing all
 * zones, transport lines, businesses, and people.
 */
@SuppressFBWarnings(value = "EI", justification = """
        Considering the basic structure of the application, we choose to
        pass the mutable models as parameters, because we need to keep them always updated. In every case, we pass
        interfaces of the models.""")
public final class CityModelImpl implements CityModel {
    private final List<Zone> zones;
    private List<TransportLine> transports;
    private List<Business> businesses;
    private List<List<DynamicPerson>> people;
    private final MapModelImpl mapModel;
    private final ClockModel clockModel;
    private final InputModel inputModel;
    private final GraphicsModelImpl graphicsModel;
    private final EmploymentOfficeData employmentOfficeData;
    private int frameWidth;
    private int frameHeight;
    private final Random random = new Random();

    /**
     * Constructs a new CityModelImpl object.
     * Initializes the map model, clock model, input model, graphics model, zones,
     * transports, and employment office data.
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
     * Creates the entities (businesses and people) for the simulation.
     * 
     * @param extraBusinesses The number of extra businesses to add.
     */
    @Override
    public void createEntities(final int extraBusinesses) {
        graphicsModel.clearDatasets();

        transports = new TransportFactoryImpl().createTransportsFromFile(zones);
        transports.forEach(t -> t.setCapacity(t.getCapacity() * inputModel.getCapacity() / 100));

        ZoneTableCreation.createAndAddPairs(zones, transports);

        int numberOfBusinesses = inputModel.getNumberOfPeople() / ConstantAndResourceLoader.PERC_BUSINESS;

        if (extraBusinesses > 0) {
            numberOfBusinesses += extraBusinesses;
        }
        final BusinessFactory businessFactory = new BusinessFactoryImpl();
        businesses = businessFactory.createMultipleBusiness(zones, numberOfBusinesses);


        this.people = new ArrayList<>();
        people = new PersonFactoryImpl().createAllPeople(getInputModel().getNumberOfPeople(), zones, businesses);

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
     * Calculates the average pay in a given zone.
     * 
     * @param zone The zone to calculate the average pay for.
     * @return The average pay in the zone.
     */
    @Override
    public double avaragePayZone(final Zone zone) {
        double avarage = 0;
        int businessCount = 0;
        double sum = 0;
        for (final Business business : businesses) {
            if (business.getBusinessData().zone().equals(zone)) {
                businessCount++;
                sum += business.getBusinessData().employees().size() * business.calculatePay();
                avarage = sum / businessCount;
            }
        }
        return avarage;
    }

    /**
     * Gets the number of direct transport lines from a given zone.
     * 
     * @param zone The zone to check for direct transport lines.
     * @return The number of direct transport lines from the zone.
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
     * Determines the frame size based on the screen size.
     */
    @Override
    public void takeFrameSize() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int maxWidth = (int) (screenSize.getWidth() * ConstantAndResourceLoader.SCREEN_SIZE_PERCENTAGE);
        final int maxHeight = (int) (screenSize.getHeight() * ConstantAndResourceLoader.SCREEN_SIZE_PERCENTAGE);

        final int frameHeight = maxHeight > (maxWidth / 2) ? maxWidth / 2 : maxHeight;
        final int frameWidth = frameHeight * 2;

        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
    }

    /**
     * Sets the screen size to the specified width and height.
     * 
     * @param newWidth  The new width of the screen.
     * @param newHeight The new height of the screen.
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
     * Gets the zone by a given position.
     * 
     * @param position The position to check for a zone.
     * @return An Optional containing the zone if found, otherwise empty.
     */
    @Override
    public Optional<Zone> getZoneByPosition(final Pair<Integer, Integer> position) {
        return zones.stream()
                .filter(zone -> isPositionInZone(position, zone))
                .findFirst();
    }

    /**
     * Checks if a position is within a given zone.
     * 
     * @param position The position to check.
     * @param zone     The zone to check within.
     * @return True if the position is within the zone, false otherwise.
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
     * Gets the map model.
     * 
     * @return The map model.
     */
    @Override
    public MapModelImpl getMapModel() {
        return this.mapModel;
    }

    /**
     * Gets the clock model.
     * 
     * @return The clock model.
     */
    @Override
    public ClockModel getClockModel() {
        return this.clockModel;
    }

    /**
     * Gets the input model.
     * 
     * @return The input model.
     */
    @Override
    public InputModel getInputModel() {
        return this.inputModel;
    }

    /**
     * Gets the graphics model.
     * 
     * @return The graphics model.
     */
    @Override
    public GraphicsModelImpl getGraphicsModel() {
        return this.graphicsModel;
    }

    /**
     * Gets the list of zones.
     * 
     * @return An unmodifiable list of zones.
     */
    @Override
    public List<Zone> getZones() {
        return Collections.unmodifiableList(this.zones);
    }

    /**
     * Gets the list of transport lines.
     * 
     * @return An unmodifiable list of transport lines.
     */
    @Override
    public List<TransportLine> getTransportLines() {
        return Collections.unmodifiableList(this.transports);
    }

    /**
     * Gets the list of businesses.
     * 
     * @return An unmodifiable list of businesses.
     */
    @Override
    public List<Business> getBusinesses() {
        return Collections.unmodifiableList(this.businesses);
    }

    /**
     * Gets a list of all people in the simulation.
     * 
     * @return A list of all people.
     */
    @Override
    public List<DynamicPerson> getAllPeople() {
        return people.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Checks if there are people present in the simulation.
     * 
     * @return True if people are present, false otherwise.
     */
    @Override
    public boolean isPeoplePresent() {
        return this.people != null;
    }

    /**
     * Checks if there are businesses present in the simulation.
     * 
     * @return True if businesses are present, false otherwise.
     */
    @Override
    public boolean isBusinessesPresent() {
        return this.businesses != null;
    }

    /**
     * Gets the frame width.
     * 
     * @return The frame width.
     */
    @Override
    public int getFrameWidth() {
        return this.frameWidth;
    }

    /**
     * Gets the frame height.
     * 
     * @return The frame height.
     */
    @Override
    public int getFrameHeight() {
        return this.frameHeight;
    }

    /**
     * Gets the number of people residing in a given zone.
     * 
     * @param zoneName The name of the zone.
     * @return An Optional containing the number of people in the zone if found,
     *         otherwise empty.
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
     * Gets the number of businesses in a given zone.
     * 
     * @param zoneName The name of the zone.
     * @return The number of businesses in the zone.
     */
    @Override
    public int getBusinessesInZone(final String zoneName) {
        return (int) businesses.stream()
                .filter(b -> b.getBusinessData().zone().name().equals(zoneName))
                .count();
    }

    /**
     * Removes all businesses from the simulation.
     */
    @Override
    public void removeBusinesses() {
        final Iterator<Business> iterator = businesses.iterator();
        while (iterator.hasNext()) {
            final Business business = iterator.next();
            if (business != null) {
                iterator.remove();
            }
        }
    }

    /**
     * Gets a random person from the simulation.
     * 
     * @return An Optional containing a random person if found, otherwise empty.
     */
    @Override
    public Optional<DynamicPerson> getRandomPerson() {
        if (people == null || people.isEmpty()) {
            return Optional.empty();
        }
        final List<DynamicPerson> allPeople = getAllPeople();
        return Optional.of(allPeople.get(random.nextInt(allPeople.size())));
    }
}
