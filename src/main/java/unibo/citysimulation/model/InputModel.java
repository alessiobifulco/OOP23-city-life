package unibo.citysimulation.model;
/**
 * This class represents the input model for the city simulation.
 */
public interface InputModel {
    /**
     * Gets the number of people.
     *
     * @return the number of people
     */
    int getNumberOfPeople();
    /**
     * Sets the number of people.
     *
     * @param numberOfPeople the number of people
     */
    void setNumberOfPeople(int numberOfPeople);
    /**
     * Gets the number of businesses.
     *
     * @return the number of businesses
     */
    int getNumberOfBusiness();
    /**
     * Sets the number of businesses.
     *
     * @param numberOfBusiness the number of businesses
     */
    void addNumberOfBusiness(int numberOfBusiness);
    /**
     * Increments the number of businesses.
     *
     * @param numberOfBusiness the number of businesses to add
     */
    void incrementNumberOfBusiness(int numberOfBusiness);
    /**
     * Gets the capacity.
     *
     * @return the capacity
     */
    int getCapacity();
    /**
     * Sets the capacity.
     *
     * @param capacity the capacity
     */
    void setCapacity(int capacity);
    /**
     * Gets the richness.
     *
     * @return the richness
     */
    int getRichness();
    /**
     * Sets the richness.
     *
     * @param richness the richness
     */
    void setRichness(int richness);
}


