package unibo.citysimulation.model.person.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.person.api.TransportStrategy;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;

/**
 * Represents a dynamic person that can change state based on the current time
 * and move in order to work.
 */
public final class DynamicPersonImpl extends StaticPersonImpl implements DynamicPerson {
    private int lastArrivingTime;
    private PersonState lastDestination;
    private boolean late;
    private final Random random = new Random();
    private int businessBegin;
    private int businessEnd;
    private final TransportStrategy transportStrategy;
    private static final Map<PersonState, Integer> stateCounts = new HashMap<>();
    private static final List<DynamicPersonImpl> unchangedPersons = new ArrayList<>();
    private static final Map<String, LineCount> lineCounts = new HashMap<>();

    /**
     * Constructs a new dynamic person with the given person data and money.
     * At the beginning, the person is at home.
     * 
     * @param personData the data of the person.
     * @param money      the money of the person.
     * @param business   the business where the person works.
     */
    public DynamicPersonImpl(final PersonData personData, final int money, final Optional<Business> business) {
        super(personData, money, business);
        this.lastDestination = PersonState.WORKING;
        this.late = false;
        this.businessBegin = 0;
        this.businessEnd = 0;
        this.transportStrategy = new TransportStrategyImpl();
    }

    @Override
    public void setState(final PersonState newState) {
        PersonState oldState = super.getState();
        if (oldState == newState) {
            unchangedPersons.add(this);
        } else {
            unchangedPersons.remove(this);
            updateStateCounts(oldState, newState);
        }
        super.setState(newState);
    }

    private void updateStateCounts(PersonState oldState, PersonState newState) {
        stateCounts.put(oldState, stateCounts.getOrDefault(oldState, 0) - 1);
        stateCounts.put(newState, stateCounts.getOrDefault(newState, 0) + 1);
    }

    public static Map<PersonState, Integer> getCountsOfStates() {
        return stateCounts;
    }

    public static List<DynamicPersonImpl> getUnchangedPersons() {
        return unchangedPersons;
    }

    private boolean shouldMove(final int currentTime, final int timeToMove, final int lineDuration) {
        if (getTransportLine().length == 0) {
            return false;
        }
        if (currentTime == timeToMove || late || (timeToMove == 86400 && currentTime == 0)) {
            if (transportStrategy.isCongested(List.of(getTransportLine()))) {
                late = true;
                return false;
            }
            this.lastArrivingTime = transportStrategy.calculateArrivalTime(currentTime, lineDuration);
            this.late = false;
            return true;
        }
        return false;
    }

    private void handleWorkTransition(final LocalTime currentTime) {
        if (shouldMove(currentTime.toSecondOfDay(), businessBegin - super.getTripDuration(), super.getTripDuration())) {
            moveTo(PersonState.WORKING);
        }
    }

    private void handleHomeTransition(final LocalTime currentTime) {
        if (shouldMove(currentTime.toSecondOfDay(), businessEnd, super.getTripDuration())) {
            moveTo(PersonState.AT_HOME);
        }
    }

    private int calculateUpdatedTime(final LocalTime movingTime) {
        if (movingTime.equals(LocalTime.MIDNIGHT)) {
            return 0;
        }
        return movingTime.toSecondOfDay() + random.nextInt(ConstantAndResourceLoader.MAX_MOVING_TIME_VARIATION)
                * ConstantAndResourceLoader.MINUTES_IN_A_SECOND * ConstantAndResourceLoader.SECONDS_IN_A_MINUTE;
    }

    private void handleArrival(final LocalTime currentTime) {
        if (currentTime.toSecondOfDay() == this.lastArrivingTime
                || (this.lastArrivingTime == 86400 && currentTime.toSecondOfDay() == 0)) {
            this.setState(this.lastDestination);
            updatePosition();
            decrementPersonsInLine();
            super.travel = false;
        }
    }

    private void decrementPersonsInLine() {
        for (TransportLine line : getTransportLine()) {
            String lineName = line.getName();
            lineCounts.putIfAbsent(lineName, new LineCount());
            lineCounts.get(lineName).decrement();
        }
        transportStrategy.decrementPersonsInLine(List.of(getTransportLine()));
    }

    /**
     * Checks the state of the person based on the current time.
     * If the person is moving, it checks if the person has arrived at the
     * destination.
     * If the person is working, it checks if it is time to go home.
     * If the person is at home, it checks if it is time to go to work.
     * 
     * @param currentTime the current time.
     */
    @Override
    public void checkState(final LocalTime currentTime) {
        switch (super.getState()) {
            case MOVING -> handleArrival(currentTime);
            case WORKING -> handleHomeTransition(currentTime);
            case AT_HOME -> handleWorkTransition(currentTime);
            default -> throw new IllegalStateException("Invalid state: " + super.getState());
        }
    }

    private void moveTo(final PersonState newState) {
        if (super.getTripDuration() == 0) {
            this.setState(newState);
        } else {
            this.setState(PersonState.MOVING);
            incrementPersonsInLine();
            super.travel = true;
        }
        this.lastDestination = newState;
        this.updatePosition();
    }

    private void incrementPersonsInLine() {
        for (TransportLine line : getTransportLine()) {
            String lineName = line.getName();
            lineCounts.putIfAbsent(lineName, new LineCount());
            lineCounts.get(lineName).increment();
        }
        transportStrategy.incrementPersonsInLine(List.of(getTransportLine()));
    }

    @Override
    public void setBusinessBegin(final LocalTime businessBegin) {
        this.businessBegin = calculateUpdatedTime(businessBegin);
    }

    @Override
    public void setBusinessEnd(final LocalTime businessEnd) {
        this.businessEnd = calculateUpdatedTime(businessEnd);
    }
}