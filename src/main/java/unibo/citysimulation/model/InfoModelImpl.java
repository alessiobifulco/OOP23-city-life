package unibo.citysimulation.model;


import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;
import unibo.citysimulation.view.sidepanels.InfoPanel;
import java.util.Objects;

import java.util.Optional;

/**
 * Class responsible for managing information related to zones, businesses, and people.
 */
public class InfoModelImpl implements InfoModel {
    private final InfoPanel infoPanel;
    private final CityModel cityModel;

    /**
     * Constructs an InfoModelImpl object.
     *
     * @param cityModel The CityModel interface containing the method to access and modify city data.
     * @param infoPanel The InfoPanel interface to update information display.
     */
    public InfoModelImpl(final CityModel cityModel, final InfoPanel infoPanel) {
        this.cityModel = Objects.requireNonNull(cityModel, "CityModel cannot be null");
        this.infoPanel = Objects.requireNonNull(infoPanel, "InfoPanel cannot be null");
    }

    /**
     * Updates the information panel based on the coordinates provided.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    @Override
    public void updateZoneInfo(final int x, final int y) {
        final Optional<Zone> selectedZone = cityModel.getZoneByPosition(new Pair<>(x, y));
        selectedZone.ifPresentOrElse(zone -> updateInfoPanelWithZone(zone, x, y), () -> clearInfoPanel(x, y));
    }

    private void updateInfoPanelWithZone(final Zone zone, final int x, final int y) {
        infoPanel.updatePositionInfo(x, y);
        infoPanel.updateZoneName(zone.name());
        cityModel.getPeopleInZone(zone.name()).ifPresentOrElse(
                infoPanel::updateNumberOfPeople,
                () -> infoPanel.updateNumberOfPeople(0));
        infoPanel.updateNumberOfBusiness(cityModel.getBusinessesInZone(zone.name()));
        infoPanel.updateAvaragePay(cityModel.avaragePayZone(zone));
        infoPanel.updateNumberOfDirectLines(cityModel.getNumberOfDirectLinesFromZone(zone));
    }

    private void clearInfoPanel(final int x, final int y) {
        infoPanel.updatePositionInfo(x, y);
        infoPanel.updateZoneName("");
        infoPanel.updateNumberOfPeople(0);
        infoPanel.updateNumberOfBusiness(0);
        infoPanel.updateAvaragePay(0);
        infoPanel.updateNumberOfDirectLines(0);
    }
}

