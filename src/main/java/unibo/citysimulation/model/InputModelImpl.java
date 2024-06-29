package unibo.citysimulation.model;

import unibo.citysimulation.utilities.ConstantAndResourceLoader;
/**
 * This class represents the input model for the city simulation.
 */
public class InputModelImpl implements InputModel {
    private int numberOfPeople;
    private int numberOfBusiness;
    private int capacity;
    private int richness;

    /**
     * Gets the number of people.
     *
     * @return the number of people
     */
    @Override
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Sets the number of people.
     *
     * @param numberOfPeople the number of people
     */
    @Override
    public void setNumberOfPeople(final int numberOfPeople) {
        final int range = ConstantAndResourceLoader.MAX_PEOPLE - ConstantAndResourceLoader.MIN_PEOPLE;
        int scaledPeople = numberOfPeople * range;
        scaledPeople = scaledPeople / 100;
        this.numberOfPeople = scaledPeople + ConstantAndResourceLoader.MIN_PEOPLE;
        //System.out.println(numberOfPeople);
    }

    /**
     * Gets the number of businesses.
     *
     * @return the number of businesses
     */
    @Override
    public int getNumberOfBusiness() {
        return numberOfBusiness;
    }

    /**
     * Sets the number of businesses.
     *
     * @param numberOfBusiness the number of businesses
     */
    @Override
    public void addNumberOfBusiness(final int numberOfBusiness) {
        this.numberOfBusiness = numberOfBusiness;
    }

    /**
     * Increments the number of businesses.
     *
     * @param numberOfBusiness the number of businesses to add
     */
    @Override
    public void incrementNumberOfBusiness(final int numberOfBusiness) {
        this.numberOfBusiness += numberOfBusiness;
    }

    /**
     * Gets the capacity.
     *
     * @return the capacity
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity.
     *
     * @param capacity the capacity
     */
    @Override
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the richness.
     *
     * @return the richness
     */
    @Override
    public int getRichness() {
        return richness;
    }

    /**
     * Sets the richness.
     *
     * @param richness the richness
     */
    @Override
    public void setRichness(final int richness) {
        this.richness = richness;
    }
}
