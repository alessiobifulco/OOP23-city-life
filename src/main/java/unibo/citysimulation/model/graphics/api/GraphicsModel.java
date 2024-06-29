package unibo.citysimulation.model.graphics.api;


import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.transport.api.TransportLine;

import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Color;
import java.util.List;

/**
 * Interface for managing datasets used in the graphical representation of the city simulation.
 */
public interface GraphicsModel {

    /**
     * Clears all datasets in the graphics model.
     */
    void clearDatasets();

    /**
     * Updates the datasets with new values derived from the provided lists of people, transport lines, and businesses.
     *
     * @param people     the list of dynamic person objects representing the population
     * @param lines      the list of transport line objects representing the transportation network
     * @param businesses the list of business objects representing the businesses
     * @param updateRate the rate at which the data is updated for debug concurrency access
     */
    void updateDataset(List<DynamicPerson> people, List<TransportLine> lines, List<Business> businesses, int updateRate);

    /**
     * Retrieves the datasets used in the graphics model.
     *
     * @return the list of {@link XYSeriesCollection} datasets
     */
    List<XYSeriesCollection> getDatasets();

    /**
     * Returns the names of the datasets in the graphics model.
     *
     * @return the list of names for each dataset
     */
    List<String> getNames();

    /**
     * Returns the list of colors used for rendering the datasets in the graphics model.
     *
     * @return the list of colors
     */
    List<Color> getColors();
}
