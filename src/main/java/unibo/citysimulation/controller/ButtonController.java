package unibo.citysimulation.controller;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.view.WindowView;

import java.util.Objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ButtonController class is responsible for handling button actions in the
 * city simulation.
 * It implements the ActionListener interface to listen for button events.
 */
public final class ButtonController implements ActionListener {
    private final CityModel cityModel;
    private final WindowView windowView;

    /**
     * Constructs a ButtonController object with the specified CityModel and
     * WindowView.
     *
     * @param cityModel  the CityModel object representing the city simulation model
     * @param windowView the WindowView object representing the graphical user
     *                   interface
     */
    public ButtonController(final CityModel cityModel, final WindowView windowView) {
        this.cityModel = Objects.requireNonNull(cityModel, "cityModel must not be null");
        this.windowView = Objects.requireNonNull(windowView, "windowView must not be null");
    }

    /**
     * Handles the button action event.
     *
     * @param e the ActionEvent object representing the button action event
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        cityModel.getRandomPerson().ifPresent(person -> {
            if (person.getBusiness().isPresent() && person.getPersonData().residenceZone().name() != null) {
                final String info = "Name: " + person.getPersonData().name()
                        + "\nAge: " + person.getPersonData().age()
                        + "\nResidence: " + person.getPersonData().residenceZone().name()
                        + "\nWork at: " + person.getBusiness().get().getBusinessData().id()
                        + "\nState: " + person.getState()
                        + "\nBalance " + person.getMoney();
                windowView.showPersonInfo(info);
            } else if (person.getPersonData().residenceZone().name() != null) {
                final String info = "Name: " + person.getPersonData().name()
                        + "\nAge: " + person.getPersonData().age()
                        + "\nResidence: " + person.getPersonData().residenceZone().name()
                        + "\nWork at: No Work"
                        + "\nState: " + person.getState()
                        + "\nBalance " + person.getMoney();
                windowView.showPersonInfo(info);
            }
        });
    }
}
