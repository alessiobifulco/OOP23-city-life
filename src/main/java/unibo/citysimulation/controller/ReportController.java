package unibo.citysimulation.controller;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.model.ReportLogic;

import java.util.Objects;

import javax.swing.JOptionPane;

/**
 * The ReportController class is responsible for displaying the simulation
 * report.
 */
public final class ReportController {

    private final CityModel cityModel;

    /**
     * Constructs a ReportController object with the specified CityModel.
     *
     * @param cityModel the CityModel object representing the city simulation model
     */
    public ReportController(final CityModel cityModel) {
        this.cityModel = Objects.requireNonNull(cityModel, "cityModel must not be null");
    }

    /**
     * Displays the simulation report using a JOptionPane dialog.
     */
    public void showReport() {
        final String report = ReportLogic.generateReport(cityModel);
        JOptionPane.showMessageDialog(null, report, "Simulation Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
