package unibo.citysimulation.model.person.impl;

/**
 * The LineCount class represents a counter for increment and decrement
 * operations.
 * It keeps track of the number of times the increment and decrement methods are
 * called.
 */
public class LineCount {
    private int incrementCount;
    private int decrementCount;

    /**
     * Increments the incrementCount by 1.
     */
    public void increment() {
        incrementCount++;
    }

    /**
     * Increments the decrementCount by 1.
     */
    public void decrement() {
        decrementCount++;
    }

    /**
     * Returns the current value of incrementCount.
     *
     * @return the current value of incrementCount
     */
    public int getIncrementCount() {
        return incrementCount;
    }

    /**
     * Returns the current value of decrementCount.
     *
     * @return the current value of decrementCount
     */
    public int getDecrementCount() {
        return decrementCount;
    }
}
