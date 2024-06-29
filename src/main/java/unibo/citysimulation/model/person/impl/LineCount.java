package unibo.citysimulation.model.person.impl;

public class LineCount {
    private int incrementCount;
    private int decrementCount;

    public void increment() {
        incrementCount++;
    }

    public void decrement() {
        decrementCount++;
    }

    public int getIncrementCount() {
        return incrementCount;
    }

    public int getDecrementCount() {
        return decrementCount;
    }
}