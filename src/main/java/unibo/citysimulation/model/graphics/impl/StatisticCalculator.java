package unibo.citysimulation.model.graphics.impl;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.utilities.BusinessType;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.api.StaticPerson.PersonState;
import unibo.citysimulation.model.transport.api.TransportLine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for calculating various statistics related to the city
 * simulation.
 * This includes calculations for people states, transport line congestion
 * levels, and business occupation percentages.
 */
public final class StatisticCalculator {

    private StatisticCalculator() {
    }

    /**
     * Calculates the percentage of people in each state (AT_HOME, MOVING, WORKING)
     * from the given list of people.
     *
     * @param people the list of dynamic person objects representing the population
     * @return a list of integers representing the percentage of people in each
     *         state: AT_HOME, MOVING, and WORKING
     */
    static List<Integer> getPeopleStateCounts(final List<DynamicPerson> people) {
        return Arrays.asList(
                calculatePercentage(people, PersonState.AT_HOME),
                calculatePercentage(people, PersonState.MOVING),
                calculatePercentage(people, PersonState.WORKING));
    }

    static int calculatePercentage(final List<DynamicPerson> people, final PersonState state) {
        return (int) (people.stream().filter(person -> person.getState() == state).count() * 100.0 / people.size());
    }

    /**
     * Calculates the congestion levels for each transport line in the given list.
     *
     * @param lines the list of transport line objects
     * @return a list of doubles representing the congestion level for each
     *         transport line
     */
    static List<Double> getTransportLinesCongestion(final List<TransportLine> lines) {
        return lines.stream()
                .map(TransportLine::getCongestion)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the occupation percentage of each business in the given list.
     *
     * @param businesses the list of businesses
     * @return a list of integers representing the occupation percentage of each
     *         business
     */
    static List<Integer> getBusinessesOccupation(final List<Business> businesses) {
        return Arrays.asList(
                calculateBusinessOccupation(businesses, BusinessType.SMALL),
                calculateBusinessOccupation(businesses, BusinessType.MEDIUM),
                calculateBusinessOccupation(businesses, BusinessType.BIG));
    }

    /**
     * Calculates the occupation percentage of businesses of a specific type.
     *
     * @param businesses the list of businesses to calculate the occupation for
     * @param type       the type of business to calculate the occupation for
     * @return the occupation percentage of businesses of the specified type, or 0
     *         if no businesses of that type exist
     */
    private static int calculateBusinessOccupation(final List<Business> businesses, final BusinessType type) {
        final List<Business> filteredBusinesses = businesses.stream()
                .filter(business -> business.getBusinessType() == type)
                .collect(Collectors.toList());

        if (filteredBusinesses.isEmpty()) {
            return 0;
        }

        return (int) filteredBusinesses.stream()
                .mapToDouble(business -> (double) business.getBusinessData().employees().size()
                        / business.getBusinessData().maxEmployees() * 100)
                .average()
                .orElse(0);
    }
}
