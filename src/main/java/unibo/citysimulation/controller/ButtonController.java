package unibo.citysimulation.controller;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.view.WindowView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener {
    private final CityModel cityModel;
    private final WindowView windowView;

    public ButtonController(CityModel cityModel, WindowView windowView) {
        this.cityModel = cityModel;
        this.windowView = windowView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cityModel.getRandomPerson().ifPresent(person -> {
            String info = "Name: " + person.getPersonData().name() +
                          "\nAge: " + person.getPersonData().age() +
                          "\nResidence: " + person.getPersonData().residenceZone().name() +
                          "\nWork at: " + person.getBusiness().get().getBusinessData().id() +
                          "\nState: " + person.getState()+
                          "\nBalance " + person.getMoney()
                          ;
            windowView.showPersonInfo(info);
        });
    }
}
