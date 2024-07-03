package unibo.citysimulation.controller;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.view.WindowView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

/**
 * The WindowController class is responsible for controlling the window view and
 * interacting with the city model.
 */
public class WindowController {
    private final WindowView windowView;
    private final CityModel cityModel;

    /**
     * Constructs a new WindowController with the specified window view and city
     * model.
     *
     * @param windowView The window view to be controlled.
     * @param cityModel  The city model to interact with.
     */
    public WindowController(final WindowView windowView, final CityModel cityModel) {
        this.windowView = Objects.requireNonNull(windowView, "windowView must not be null");
        this.cityModel = Objects.requireNonNull(cityModel, "cityModel must not be null");

        windowView.addResizeListener(new ResizeListener());
        initializeControllers();
        windowView.updateFrame(cityModel.getFrameWidth(), cityModel.getFrameHeight());

        final ButtonController buttonController = new ButtonController(cityModel, windowView);
        windowView.getInputPanel().addShowPersonButtonListener(buttonController);
    }

    /**
     * Initializes the controllers for the city simulation.
     */
    private void initializeControllers() {
        new MapController(cityModel, windowView);
        cityModel.getClockModel()
                .addObserver(new ClockController(cityModel.getClockModel(), windowView.getClockPanel()));
        new InputController(cityModel, cityModel.getInputModel(), windowView.getInputPanel(),
                windowView.getClockPanel());
        new GraphicsController(cityModel, windowView.getGraphicsPanel());
    }

    /**
     * A resize listener for the window view.
     */
    private final class ResizeListener extends ComponentAdapter {
        /**
         * Called when the component is resized.
         *
         * @param e The component event.
         */
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
