package unibo.citysimulation.view;

import java.awt.event.ComponentAdapter;

import unibo.citysimulation.view.map.MapPanel;
import unibo.citysimulation.view.sidepanels.InfoPanel;
import unibo.citysimulation.view.sidepanels.InputPanel;
import unibo.citysimulation.view.sidepanels.clock.ClockPanel;
import unibo.citysimulation.view.sidepanels.graphics.GraphicsPanel;

/**
 * Represents the main window of the application.
 */
public interface WindowView {

    /**
     * Adds a component resize listener.
     *
     * @param adapter The component adapter to add.
     */
    void addResizeListener(ComponentAdapter adapter);

    /**
     * Updates the size of the panels based on the window size.
     *
     * @param width the new width of the window.
     * @param height the new height of the window.
     */
    void updateFrame(int width, int height);

    /**
     * Retrieves the info panel.
     *
     * @return The info panel.
     */
    InfoPanel getInfoPanel();

    /**
     * Retrieves the clock panel.
     *
     * @return The clock panel.
     */
    ClockPanel getClockPanel();

    /**
     * Retrieves the input panel.
     *
     * @return The input panel.
     */
    InputPanel getInputPanel();

    /**
     * Retrieves the graphics panel.
     *
     * @return The graphics panel.
     */
    GraphicsPanel getGraphicsPanel();

    /**
     * Retrieves the map panel.
     *
     * @return The map panel.
     */
    MapPanel getMapPanel();

    /**
     * Shows the information of a person.
     *
     * @param info The information of the person.
     */
    void showPersonInfo(String info);
}
