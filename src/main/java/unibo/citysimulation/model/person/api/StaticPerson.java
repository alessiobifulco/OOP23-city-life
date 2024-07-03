package unibo.citysimulation.model.person.api;

import java.util.Optional;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.utilities.Pair;

/**
 * An interface for modelling a person.
 */
public interface StaticPerson {
    /**
     * Represents the state of a person.
     */
    enum PersonState {
        /**
         * The person is moving from home to work or vice versa.
         */
        MOVING,
        /**
         * The person is at work.
         */
        WORKING,
        /**
         * The person is at home.
         */
        AT_HOME
    }

    /**
     * @return the state of the person.
     */
    PersonState getState();

    /**
     * @return the data of the person.
     */
    PersonData getPersonData();

    /**
     * @return the money of the person.
     */
    double getMoney();

    /**
     * @param amount the amount of money to add.
     */
    void addMoney(double amount);

    /**
     * 
     * @return the duration of the trip from home to work.
     */
    int getTripDuration();

    /**
     * Sets the state of the person.
     * 
     * @param state the new state of the person.
     */
    void setState(PersonState state);

    /**
     * 
     * @return the transport lines used by the person in his daily trip.
     */
    TransportLine[] getTransportLine();

    /**
     * 
     * @return the actual position of the person.
     */
    Optional<Pair<Integer, Integer>> getPosition();

    /**
     * Returns an Optional object representing the business associated with this
     * person.
     *
     * @return an Optional object containing the business associated with this
     *         person,
     *         or an empty Optional if no business is associated.
     */
    Optional<Business> getBusiness();

    /**
     * Sets the business for the person.
     *
     * @param business an optional representing the business the person is
     *                 associated with
     */
    void setBusiness(Optional<Business> business);
}
