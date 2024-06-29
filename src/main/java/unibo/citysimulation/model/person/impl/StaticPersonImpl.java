package unibo.citysimulation.model.person.impl;

import java.util.Optional;
import java.util.Arrays;
import java.util.Random;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.person.api.StaticPerson;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.zone.ZoneTable;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.utilities.Pair;

/**
 * Represents a static person in the city simulation.
 */
public class StaticPersonImpl implements StaticPerson {
    private double money;
    private Optional<Pair<Integer, Integer>> position;
    private final PersonData personData;
    private PersonState state;
    private final Pair<Integer, Integer> homePosition;
    private TransportLine[] transportLine;
    private int tripDuration;
    private static final Random RANDOM = new Random();
    private Optional<Business> business;
    public Boolean travel = false;

    /**
     * Constructs a new static person with the given person data and money.
     * 
     * @param personData the data of the person.
     * @param money      the money of the person.
     * @param business   the business where the person works.
     */
    public StaticPersonImpl(final PersonData personData, final double money, final Optional<Business> business) {
        this.personData = personData;
        this.money = money;
        this.state = PersonState.AT_HOME;
        this.homePosition = personData.residenceZone().getRandomPosition();
        this.position = Optional.of(homePosition);
        this.business = business;
        calculateTrip();
    }

    /**
     * @return the data of the person.
     */
    @Override
    public PersonData getPersonData() {
        return personData;
    }

    /**
     * @return the actual position of the person.
     */
    @Override
    public Optional<Pair<Integer, Integer>> getPosition() {
        return position;
    }

    /**
     * @return the money of the person.
     */
    @Override
    public double getMoney() {
        return money;
    }

    /**
     * Adds the given amount of money to the person.
     * 
     * @param amount the amount of money to add.
     */
    @Override
    public void addMoney(final double amount) {
        this.money += amount;
    }

    /**
     * @return the state of the person.
     */
    @Override
    public PersonState getState() {
        return state;
    }

    /**
     * Sets the state of the person.
     * 
     * @param state the new state of the person.
     */
    @Override
    public void setState(final PersonState state) {
        this.state = state;
    }

    /**
     * @return the path of transport lines that the person have to take every day..
     */
    @Override
    public TransportLine[] getTransportLine() {
        return Arrays.copyOf(transportLine, transportLine.length);
    }

    /**
     * @return the duration of the trip from home to work.
     */
    @Override
    public int getTripDuration() {
        return tripDuration;
    }

    /**
     * Updates the position of the person.
     */
    protected void updatePosition() {
        switch (this.state) {
            case MOVING:
                this.position = Optional.empty();
                break;
            case WORKING:
                business.ifPresent(b -> {
                    final Pair<Integer, Integer> businessPosition = b.getBusinessData().position();
                    final int newX = businessPosition.getFirst() + getRandomDeviation();
                    final int newY = businessPosition.getSecond() + getRandomDeviation();
                    this.position = Optional.of(new Pair<>(newX, newY));
                });
                break;
            case AT_HOME:
                this.position = Optional.of(homePosition);
                break;
            default:
                throw new IllegalStateException("Invalid state.");
        }
    }

    /**
     * @return a random deviation to add to the position of the person between -20
     *         and 20.
     */
    private int getRandomDeviation() {
        return RANDOM.nextInt(ConstantAndResourceLoader.MAX_DEVIATION_RANGE)
                - ConstantAndResourceLoader.MAX_DEVIATION_OFFSET;
    }

    /**
     * Calculates the trip details for the person.
     * If the person has a business, it retrieves the transport line and trip
     * duration
     * based on the residence zone and business zone using the ZoneTable.
     * If the person does not have a business, it sets the transport line to an
     * empty array
     * and trip duration to 0.
     */
    private void calculateTrip() {
        if (this.business.isPresent()) {
            this.transportLine = ZoneTable.getInstance().getTransportLine(personData.residenceZone(),
                    business.get().getBusinessData().zone());
            tripDuration = ZoneTable.getInstance().getTripDuration(transportLine);
        } else {
            this.transportLine = new TransportLine[0];
            tripDuration = 0;
        }
    }

    /**
     * Returns the business associated with this person.
     *
     * @return an Optional containing the business associated with this person,
     *         or an empty Optional if no business is associated.
     */
    @Override
    public final Optional<Business> getBusiness() {
        return this.business;
    }

    /**
     * Sets the business for this person and calculates the trip details.
     * 
     * @param business the optional business to set for this person
     */
    @Override
    public final void setBusiness(final Optional<Business> business) {
        if(!travel){
            this.business = business;
            calculateTrip();
        }
    }
}