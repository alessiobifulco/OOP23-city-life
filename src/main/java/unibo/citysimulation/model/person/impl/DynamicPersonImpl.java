package unibo.citysimulation.model.person.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.person.api.TransportStrategy;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

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
    private String name;
    private static int counter = 0;
    private static Map<PersonState, Integer> stateCounts = new HashMap<>();
    private static List<DynamicPersonImpl> unchangedPersons = new ArrayList<>();
    private static int count_increment = 0;
    private static int count_decrement = 0;
    private static Map<String, LineCount> lineCounts = new HashMap<>();

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
        this.name = personData.name();
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
            stateCounts.put(oldState, stateCounts.getOrDefault(oldState, 0) - 1);
            stateCounts.put(newState, stateCounts.getOrDefault(newState, 0) + 1);
        }
        super.setState(newState);
    }

    public static Map<PersonState, Integer> getCountsOfStates() {
        return stateCounts;
    }

    public static List<DynamicPersonImpl> getUnchangedPersons() {
        return unchangedPersons;
    }

    private boolean shouldMove(final int currentTime, final int timeToMove, final int lineDuration) {
        // if (super.getState() == PersonState.WORKING) {
        // System.out.println("Current time: " + currentTime);
        // System.out.println("Time to move: " + timeToMove);
        // }
        if (getTransportLine().length == 0) {
            return false;
        } else if (currentTime == timeToMove || late || (timeToMove == 86400 && currentTime == 0)) {
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
        LocalTime o = LocalTime.of(0, 0);
        if (movingTime == o) {
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
            // System.out.println("Congestion: " + getTransportLine()[0].getPersonInLine());
            // System.out.println("Decrementing persons in line for " +
            // getTransportLine()[0].getName());
            for (TransportLine line : getTransportLine()) {
                String lineName = line.getName();
                lineCounts.putIfAbsent(lineName, new LineCount());
                lineCounts.get(lineName).decrement();
                // System.out.println("Person " + name + " is getting off the line " + lineName);
            }
            transportStrategy.decrementPersonsInLine(List.of(getTransportLine()));
            super.travel = false;
        }
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
        if (counter % 100 == 0) {
            // System.out.println("DynamicPersonImpl.checkState");
            // System.out.println("Name: " + name);
            // System.out.println("State: " + super.getState());
            // System.out.println("Current time: " + currentTime);
            // System.out.println("Business begin: " + LocalTime.ofSecondOfDay(businessBegin
            // % (24 * 60 * 60)));
            // System.out.println("Business end: " + LocalTime.ofSecondOfDay(businessEnd %
            // (24 * 60 * 60)));
            // System.out.println("Trip duration: " +
            // LocalTime.ofSecondOfDay(super.getTripDuration() % (24 * 60 * 60)));
            // System.out.println("Last arriving time: " +
            // LocalTime.ofSecondOfDay(lastArrivingTime % (24 * 60 * 60)));
            // System.out.println("Last destination: " + lastDestination);
            // System.out.println("Late: " + late);
            // System.out.println("Lines: " + getTransportLine());
            // if (getTransportLine().length > 0) {
            // System.out.println("Transport line: " +
            // getTransportLine()[0].getPersonInLine() + " "
            // + getTransportLine()[0].getName());
            // } else {
            // System.out.println("Transport line: None");
            // }
            // for (Map.Entry<PersonState, Integer> entry : stateCounts.entrySet()) {
            // System.out.println("State: " + entry.getKey() + " Count: " +
            // entry.getValue());
            // }
            // for (Map.Entry<String, LineCount> entry : lineCounts.entrySet()) {
            // System.out.println("Line: " + entry.getKey() + " Increment: " +
            // entry.getValue().getIncrementCount()
            // + " Decrement: " + entry.getValue().getDecrementCount());
            // }
        }
        counter++;
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
            for (TransportLine line : getTransportLine()) {
                String lineName = line.getName();
                lineCounts.putIfAbsent(lineName, new LineCount());
                lineCounts.get(lineName).increment();
                // System.out.println("Person " + name + " is getting on the line " + lineName);
            }
            super.travel = true;
            transportStrategy.incrementPersonsInLine(List.of(getTransportLine()));
        }
        this.lastDestination = newState;
        this.updatePosition();
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
