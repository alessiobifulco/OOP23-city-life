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
 * Represents an implementation of the {@link DynamicPerson} interface.
 * This class extends the {@link StaticPersonImpl} class and provides additional
 * functionality for dynamic persons.
 * Dynamic persons are able to change their state and move between different
 * locations in the city simulation.
 */
public final class DynamicPersonImpl extends StaticPersonImpl implements DynamicPerson {
    private int lastArrivingTime;
    private PersonState lastDestination;
    private boolean late;
    private final Random random = new Random();
    private int businessBegin;
    private int businessEnd;
    private final TransportStrategy transportStrategy;
    private static final Map<PersonState, Integer> STATE_COUNT = new HashMap<>();
    private static final List<DynamicPersonImpl> UNCHANGED_PERSON = new ArrayList<>();
    private static final Map<String, LineCount> LINE_COUNT = new HashMap<>();
    private static final int END = 86400;

    /**
     * Constructs a new DynamicPersonImpl object with the given person data, money,
     * and business.
     *
     * @param personData The data of the person.
     * @param money      The amount of money the person has.
     * @param business   An optional business the person is associated with.
     */
    public DynamicPersonImpl(final PersonData personData, final int money, final Optional<Business> business) {
        super(personData, money, business);
        this.lastDestination = PersonState.WORKING;
        this.late = false;
        this.businessBegin = 0;
        this.businessEnd = 0;
        this.transportStrategy = new TransportStrategyImpl();
    }

    /**
     * Sets the state of the person.
     *
     * @param newState the new state to set
     */
    @Override
    public void setState(final PersonState newState) {
        final PersonState oldState = super.getState();
        if (oldState == newState) {
            UNCHANGED_PERSON.add(this);
        } else {
            UNCHANGED_PERSON.remove(this);
            updateStateCounts(oldState, newState);
        }
        super.setState(newState);
    }

    /**
     * Updates the counts of different states for a person.
     *
     * @param oldState The previous state of the person.
     * @param newState The new state of the person.
     */
    private void updateStateCounts(final PersonState oldState, final PersonState newState) {
        STATE_COUNT.put(oldState, STATE_COUNT.getOrDefault(oldState, 0) - 1);
        STATE_COUNT.put(newState, STATE_COUNT.getOrDefault(newState, 0) + 1);
    }

    /**
     * Returns a map containing the counts of different states of a person.
     *
     * @return a map containing the counts of different states of a person
     */
    public static Map<PersonState, Integer> getCountsOfStates() {
        return new HashMap<>(STATE_COUNT);
    }

    /**
     * Returns a list of unchanged persons.
     *
     * @return a list of unchanged persons
     */
    public static List<DynamicPersonImpl> getUnchangedPersons() {
        return new ArrayList<>(UNCHANGED_PERSON);
    }

    /**
     * Determines whether the person should move based on the current time, time to
     * move, and line duration.
     *
     * @param currentTime  the current time
     * @param timeToMove   the time to move
     * @param lineDuration the duration of the line
     * @return true if the person should move, false otherwise
     */
    private boolean shouldMove(final int currentTime, final int timeToMove, final int lineDuration) {
        if (getTransportLine().length == 0) {
            return false;
        }
        if (currentTime == timeToMove || late || (timeToMove == END && currentTime == 0)) {
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

    /**
     * Handles the work transition for the person.
     * If the current time is within the specified time range, the person moves to
     * the working state.
     *
     * @param currentTime the current time
     */
    private void handleWorkTransition(final LocalTime currentTime) {
        if (shouldMove(currentTime.toSecondOfDay(), businessBegin - super.getTripDuration(), super.getTripDuration())) {
            moveTo(PersonState.WORKING);
        }
    }

    /**
     * Handles the transition of the person to their home state based on the current
     * time.
     *
     * @param currentTime the current time of the simulation
     */
    private void handleHomeTransition(final LocalTime currentTime) {
        if (shouldMove(currentTime.toSecondOfDay(), businessEnd, super.getTripDuration())) {
            moveTo(PersonState.AT_HOME);
        }
    }

    /**
     * Calculates the updated time based on the given moving time.
     *
     * @param movingTime the moving time to calculate the updated time for
     * @return the updated time in seconds
     */
    private int calculateUpdatedTime(final LocalTime movingTime) {
        if (movingTime.equals(LocalTime.MIDNIGHT)) {
            return 0;
        }
        return movingTime.toSecondOfDay() + random.nextInt(ConstantAndResourceLoader.MAX_MOVING_TIME_VARIATION)
                * ConstantAndResourceLoader.MINUTES_IN_A_SECOND * ConstantAndResourceLoader.SECONDS_IN_A_MINUTE;
    }

    /**
     * Handles the arrival of the person at their destination.
     * If the current time is equal to the last arriving time or if the last
     * arriving time is END and the current time is 0,
     * the person's state is set to the last destination, the position is updated,
     * the number of persons in line is decremented,
     * and the travel flag is set to false.
     *
     * @param currentTime the current time
     */
    private void handleArrival(final LocalTime currentTime) {
        if (currentTime.toSecondOfDay() == this.lastArrivingTime
                || (this.lastArrivingTime == END && currentTime.toSecondOfDay() == 0)) {
            this.setState(this.lastDestination);
            updatePosition();
            decrementPersonsInLine();
            super.setTravel(false);
        }
    }

    /**
     * Decrements the number of persons in line for each transport line associated
     * with this person.
     * This method updates the LINE_COUNT map by decrementing the count for each
     * transport line.
     * It also delegates the decrement operation to the transport strategy.
     */
    private void decrementPersonsInLine() {
        for (final TransportLine line : getTransportLine()) {
            final String lineName = line.getName();
            LINE_COUNT.putIfAbsent(lineName, new LineCount());
            LINE_COUNT.get(lineName).decrement();
        }
        transportStrategy.decrementPersonsInLine(List.of(getTransportLine()));
    }

    /**
     * Checks the current state of the person and performs the corresponding actions
     * based on the state.
     * 
     * @param currentTime the current time
     * @throws IllegalStateException if the person's state is invalid
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

    /**
     * Moves the person to the specified state.
     * If the trip duration is 0, the person's state is set to the new state
     * immediately.
     * Otherwise, the person's state is set to MOVING, the persons in line count is
     * incremented,
     * and the travel flag is set to true.
     * The last destination is updated and the position is updated.
     *
     * @param newState the new state to move the person to
     */
    private void moveTo(final PersonState newState) {
        if (super.getTripDuration() == 0) {
            this.setState(newState);
        } else {
            this.setState(PersonState.MOVING);
            incrementPersonsInLine();
            super.setTravel(true);
        }
        this.lastDestination = newState;
        this.updatePosition();
    }

    /**
     * Increments the count of persons in each transport line that the person is
     * currently on.
     * Also, increments the count of persons in line for the transport strategy.
     */
    private void incrementPersonsInLine() {
        for (final TransportLine line : getTransportLine()) {
            final String lineName = line.getName();
            LINE_COUNT.putIfAbsent(lineName, new LineCount());
            LINE_COUNT.get(lineName).increment();
        }
        transportStrategy.incrementPersonsInLine(List.of(getTransportLine()));
    }

    /**
     * Sets the beginning time of the business for this person.
     * The provided time is used to calculate an updated time based on the person's
     * current location.
     *
     * @param businessBegin the beginning time of the business
     */
    @Override
    public void setBusinessBegin(final LocalTime businessBegin) {
        this.businessBegin = calculateUpdatedTime(businessBegin);
    }

    /**
     * Sets the end time of the business for this person.
     * 
     * @param businessEnd the end time of the business
     */
    @Override
    public void setBusinessEnd(final LocalTime businessEnd) {
        this.businessEnd = calculateUpdatedTime(businessEnd);
    }
}
