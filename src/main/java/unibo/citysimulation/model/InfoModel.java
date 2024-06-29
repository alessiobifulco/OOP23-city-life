package unibo.citysimulation.model;

/**
 * This interface represents the model for the information panel.
 */
public interface InfoModel {
    /**
     * Updates the information panel based on the coordinates provided.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    void updateZoneInfo(int x, int y); 
}

