package unibo.citysimulation.controller;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.model.ReportLogic;

import javax.swing.JOptionPane;

public class ReportController {

    private final CityModel cityModel;

    public ReportController(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public void showReport() {
        String report = ReportLogic.generateReport(cityModel);
        JOptionPane.showMessageDialog(null, report, "Simulation Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
