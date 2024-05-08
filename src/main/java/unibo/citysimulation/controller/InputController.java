package unibo.citysimulation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.view.sidePanels.InputPanel;

public class InputController {
    private CityModel cityModel;
    private InputPanel inputPanel;
    private int numberOfPeople;
    
    public InputController(CityModel cityModel, InputPanel inputPanel) {
        this.cityModel = cityModel;
        this.inputPanel = inputPanel;


        inputPanel.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cityModel.getClockModel().restartSimulation();
                cityModel.createEntities(numberOfPeople);
            }
        });

        inputPanel.getPeopleSlider().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                numberOfPeople = inputPanel.getPeopleSlider().getValue();
                System.out.println("Selected number of people: " + numberOfPeople);
            }
        });


    }


}
