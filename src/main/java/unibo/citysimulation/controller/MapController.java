package unibo.citysimulation.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.model.MapModel;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.view.map.MapPanel;
import unibo.citysimulation.view.sidePanels.InfoPanel;

/**
 * Controller class responsible for handling mouse events on the map.
 */
public class MapController {
    private InfoPanel infoPanel;
    private MapPanel mapPanel;
    private MapModel mapModel;
    private CityModel cityModel;

    /**
     * Constructs a MapController object.
     *
     * @param model     The MapModel object containing the map data.
     * @param infoPanel The InfoPanel object to display additional information.
     */
    public MapController(CityModel cityModel, InfoPanel infoPanel, MapPanel mapPanel) {
        this.cityModel = cityModel;
        this.infoPanel = infoPanel;
        this.mapPanel = mapPanel;
        this.mapModel = cityModel.getMapModel();
        mapPanel.setZones(cityModel.getZones());

        mapPanel.setImage(mapModel.getImage());

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseclick(e);
            }
        });
    }

    /**
     * Handles mouse click events.
     *
     * @param e The MouseEvent object representing the mouse event.
     */
    public void handleMouseclick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (cityModel.getZones() != null) {
            System.out.println("pressed coordinates: " + x + " " + y);
            List<Zone> zones = cityModel.getZones();
            String zoneName = ""; // Declare zoneName here
            for (Zone zone : zones) {
                if (zone.getBoundary().isInside(x, y)) {
                    zoneName = zone.getName(); // Assign value here
                    System.out.println("Clicked inside zone: " + zoneName);
                    break;
                }
            }
            infoPanel.updateZoneName(zoneName);
        }

        mapModel.setMaxCoordinates((int) mapPanel.getSize().getWidth(), (int) mapPanel.getSize().getHeight());

        mapModel.setLastClickedCoordinates(x, y);

        infoPanel.updatePositionInfo(mapModel.getNormX(), mapModel.getNormY());

    }

    public BufferedImage getImage() {
        return mapModel.getImage();
    }
}
