package unibo.citysimulation.controller;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.view.WindowView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

/**
 * Controller class responsible for managing the main window.
 */
public class WindowController {
    private final WindowView windowView;
    private final CityModel cityModel;

    /**
     * Constructor for initial window and initialize all the feature in the window.
     * 
     * @param windowView
     * @param cityModel
     */
    public WindowController(final WindowView windowView, final CityModel cityModel) {
        this.windowView = Objects.requireNonNull(windowView, "windowView must not be null");
        this.cityModel = Objects.requireNonNull(cityModel, "cityModel must not be null");

        windowView.addResizeListener(new ResizeListener());
        initializeControllers();
        windowView.updateFrame(cityModel.getFrameWidth(), cityModel.getFrameHeight());
        
        ButtonController buttonController = new ButtonController(cityModel, windowView);
        windowView.getInputPanel().addShowPersonButtonListener(buttonController);  // Aggiungi il listener
    }

    private void initializeControllers() {
        new MapController(cityModel, windowView);
        cityModel.getClockModel().addObserver(new ClockController(cityModel.getClockModel(), windowView.getClockPanel()));
        new InputController(cityModel, cityModel.getInputModel(), windowView.getInputPanel(), windowView.getClockPanel());
        new GraphicsController(cityModel, windowView.getGraphicsPanel());
    }

    /**
     * Inner class responsible for handling component resize events.
     */
    private final class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(final ComponentEvent e) {
            final int newWidth = e.getComponent().getWidth();
            final int newHeight = e.getComponent().getHeight();
            cityModel.setScreenSize(newWidth, newHeight);

            final var mapModel = cityModel.getMapModel();
            final var mapPanel = windowView.getMapPanel();

            mapModel.setMaxCoordinates(newWidth / 2, newHeight);
            mapPanel.setLinesInfo(mapModel.getLinesPointsCoordinates(), mapModel.getTransportNames());
            if (cityModel.isPeoplePresent() && cityModel.isBusinessesPresent()) {
                mapPanel.setEntities(mapModel.getPersonInfos(cityModel.getAllPeople()),
                        mapModel.getBusinessInfos(cityModel.getBusinesses()));
            }
            windowView.updateFrame(cityModel.getFrameWidth(), cityModel.getFrameHeight());
        }
    }
}
