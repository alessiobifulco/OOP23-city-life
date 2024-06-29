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
import java.util.Random;
import java.util.Collections;

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

    @Override
    public void createEntities() {
        graphicsModel.clearDatasets();

        transports = new TransportFactoryImpl().createTransportsFromFile(zones);
        transports.forEach(t -> t.setCapacity(t.getCapacity() * inputModel.getCapacity() / 100));

        ZoneTableCreation.createAndAddPairs(zones, transports);

        businesses = BusinessFactoryImpl.createMultipleBusiness(zones, inputModel.getNumberOfPeople());

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

    @Override
    public Optional<Zone> getZoneByPosition(final Pair<Integer, Integer> position) {
        return zones.stream()
                .filter(zone -> isPositionInZone(position, zone))
                .findFirst();
    }

    @Override
    public boolean isPositionInZone(final Pair<Integer, Integer> position, final Zone zone) {
        final int x = position.getFirst();
        final int y = position.getSecond();
        final Boundary boundary = zone.boundary();
        return x >= boundary.getX() && x <= (boundary.getX() + boundary.getWidth())
                && y >= boundary.getY() && y <= (boundary.getY() + boundary.getHeight());
    }

    @Override
    public MapModelImpl getMapModel() {
        return this.mapModel;
    }

    @Override
    public ClockModel getClockModel() {
        return this.clockModel;
    }

    @Override
    public InputModel getInputModel() {
        return this.inputModel;
    }

    @Override
    public GraphicsModelImpl getGraphicsModel() {
        return this.graphicsModel;
    }

    @Override
    public List<Zone> getZones() {
        return Collections.unmodifiableList(this.zones);
    }

    @Override
    public List<TransportLine> getTransportLines() {
        return Collections.unmodifiableList(this.transports);
    }

    @Override
    public List<Business> getBusinesses() {
        return Collections.unmodifiableList(this.businesses);
    }

    @Override
    public List<DynamicPerson> getAllPeople() {
        return people.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPeoplePresent() {
        return this.people != null;
    }

    @Override
    public boolean isBusinessesPresent() {
        return this.businesses != null;
    }

    @Override
    public int getFrameWidth() {
        return this.frameWidth;
    }

    @Override
    public int getFrameHeight() {
        return this.frameHeight;
    }

    @Override
    public Optional<Integer> getPeopleInZone(final String zoneName) {
        return Optional.ofNullable(people)
                .map(pList -> pList.stream()
                        .flatMap(List::stream)
                        .filter(p -> p.getPersonData().residenceZone().name().equals(zoneName))
                        .count())
                .map(Long::intValue);
    }

    @Override
    public int getBusinessesInZone(final String zoneName) {
        return (int) businesses.stream()
                .filter(b -> b.getBusinessData().zone().name().equals(zoneName))
                .count();
    }

    @Override
    public void removeBusinesses(final int numberOfBusinesses) {
        for (int i = 0; i < numberOfBusinesses; i++) {
            businesses.remove(businesses.size());
        }
    }

    @Override
    public Optional<DynamicPerson> getRandomPerson() {
        if (people == null || people.isEmpty()) {
            return Optional.empty();
        }
        List<DynamicPerson> allPeople = getAllPeople();
        Random random = new Random();
        return Optional.of(allPeople.get(random.nextInt(allPeople.size())));
    }
}
