package unibo.citysimulation.controller;

import java.util.Objects;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.model.InputModel;
import unibo.citysimulation.view.sidepanels.InputPanel;
import unibo.citysimulation.view.sidepanels.clock.ClockPanel;

/**
* Controller class responsible for handling user input from the input panel.
*/
public class InputController {
    private final CityModel cityModel;
    private final InputModel inputModel;
    private final InputPanel inputPanel;

    /**
     * Constructs an InputController object.
     *
     * @param cityModel the city model
     * @param inputModel the input model
     * @param inputPanel the input panel
     * @param clockPanel the clock panel
     */
    public InputController(final CityModel cityModel, final InputModel inputModel, 
    final InputPanel inputPanel, final ClockPanel clockPanel) {
        this.cityModel = Objects.requireNonNull(cityModel, "cityModel must not be null");
        this.inputModel = Objects.requireNonNull(inputModel, "inputModel must not be null");
        this.inputPanel = Objects.requireNonNull(inputPanel, "inputPanel must not be null");

        inputPanel.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startSimulation(clockPanel);
                cityModel.getMapModel().startSimulation();
                inputPanel.setSlidersEnabled(false);
                inputPanel.setStartButtonEnabled(false);
                inputPanel.setStopButtonEnabled(true);
            }
        });

        inputPanel.addStopButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                stopSimulation(clockPanel);
                inputPanel.setSlidersEnabled(true);
                inputPanel.setStartButtonEnabled(true);
                inputPanel.setStopButtonEnabled(false);
            }
        });
    }

    /**
     * Starts the simulation when the start button is clicked.
     *
     * @param clockPanel The ClockPanel object representing the clock panel.
     */
    private void startSimulation(final ClockPanel clockPanel) {
        inputModel.setNumberOfPeople(inputPanel.getPeopleSliderValue());
        inputModel.setCapacity(inputPanel.getCapacitySliderValue());
        // Create entities
        int extraBusinesses = inputPanel.getBusinessSliderValue();
        cityModel.createEntities(extraBusinesses);
        // Restart the clock simulation
        cityModel.getClockModel().restartSimulation();
        // Update the pause button state on the clock panel
        clockPanel.updatePauseButton(cityModel.getClockModel().isPaused());
        clockPanel.setPauseButtonEnabled(true);
    }

    private void stopSimulation(final ClockPanel clockPanel) {
        // Restart the clock simulation
        cityModel.getClockModel().stopSimulation();
        cityModel.removeBusinesses();
        // Update the pause button state on the clock panel
        clockPanel.updatePauseButton(cityModel.getClockModel().isPaused());
        clockPanel.setPauseButtonEnabled(false);
    }
}
