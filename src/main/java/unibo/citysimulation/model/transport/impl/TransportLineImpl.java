package unibo.citysimulation.model.transport.impl;

import unibo.citysimulation.utilities.Pair;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.zone.Zone;

import java.util.List;

/**
 * Represents a transport line within the city simulation.
 */
public class TransportLineImpl implements TransportLine {
    private int capacity;
    private final String name;
    private int personInLine;
    private final int duration;
    private final Pair<Zone, Zone> link;

    /**
     * Constructor for TransportLineImpl.
     *
     * @param name     the name of the transport line
     * @param capacity the capacity of the transport line
     * @param duration the duration of the transport line
     * @param link     the pair of zones that the transport line links
     */
    public TransportLineImpl(final String name, final int capacity, final int duration, final Pair<Zone, Zone> link) {
        this.name = name;
        this.capacity = capacity;
        this.duration = duration;
        this.link = link;
    }

    /**
     * Returns the link between zones for this transport line.
     *
     * @return the link between zones for this transport line
     */
    @Override
    public Pair<Zone, Zone> getLink() {
        return link;
    }

    /**
     * Returns the capacity of this transport line.
     *
     * @return the capacity of this transport line
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of this transport line.
     *
     * @param capacity the new capacity of this transport line
     */
    @Override
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the name of this transport line.
     *
     * @return the name of this transport line
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the number of people in line.
     *
     * @return the number of people in line
     */
    @Override
    public int getPersonInLine() {
        return personInLine;
    }

    /**
     * Resets the personInLine field.
     */
    @Override
    public void resetPersonInLine() {
        personInLine = 0;
    }

    /**
     * Returns the congestion of the transport line as a percentage of the capacity.
     *
     * @return the congestion of the transport line
     */
    @Override
    public double getCongestion() {
        return (double) personInLine * 100 / capacity;
    }

    /**
     * Returns the duration of the transport line.
     *
     * @return the duration of the transport line
     */
    @Override
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the pair of zones that the transport line links.
     *
     * @return the pair of zones that the transport line links
     */
    @Override
    public Pair<Zone, Zone> getLinkedZones() {
        return link;
    }

    /**
     * Increments the number of people in line by one.
     */
    @Override
    public void incrementPersonInLine() {
        if (personInLine < capacity) {
            personInLine++;
        }
    }

    /**
     * Decrements the number of people in line by one.
     */
    @Override
    public void decrementPersonInLine() {
        if (personInLine > 0) {
            personInLine--;
        }
    }

    /**
     * Calculates the average congestion of a list of transport lines.
     *
     * @param lines the list of transport lines
     * @return the average congestion
     */
    public static double calculateAverageCongestion(final List<TransportLine> lines) {
        return lines.stream()
                .mapToDouble(TransportLine::getCongestion)
                .average()
                .orElse(0);
    }
}
