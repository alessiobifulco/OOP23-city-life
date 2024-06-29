package unibo.citylife.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.model.InfoModelImpl;
import unibo.citysimulation.view.sidepanels.InfoPanel;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;

import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * This class tests the InfoModelImpl.
 */
public final class InfoModelImplTest {

    private InfoModelImpl infoModel;
    private CityModel cityModel;
    private InfoPanel infoPanel;
    private static final int X_COORDINATE = 348;
    private static final int Y_COORDINATE = 287;
    private static final int PEOPLE_COUNT = 100;
    private static final int BUSINESS_COUNT = 45;
    private static final double AVERAGE_PAY = 4000.0;
    private static final int DIRECT_LINES_COUNT = 5;

    /**
     * This method sets up the test environment.
     */
    @BeforeEach
    public void setupInfoModel() {
        cityModel = mock(CityModel.class);
        infoPanel = mock(InfoPanel.class);
        infoModel = new InfoModelImpl(cityModel, infoPanel);
    }

    /**
     * This test verifies the update of zone information when a zone exists.
     */
    @Test
    public void testUpdateZoneInfoWithExistingZone() {
        // Arrange
        Zone zone = mock(Zone.class);
        when(zone.name()).thenReturn("Centre");
        when(cityModel.getZoneByPosition(new Pair<>(X_COORDINATE, Y_COORDINATE))).thenReturn(Optional.of(zone));
        when(cityModel.getPeopleInZone("Centre")).thenReturn(Optional.of(PEOPLE_COUNT));
        when(cityModel.getBusinessesInZone("Centre")).thenReturn(BUSINESS_COUNT);
        when(cityModel.avaragePayZone(zone)).thenReturn(AVERAGE_PAY);
        when(cityModel.getNumberOfDirectLinesFromZone(zone)).thenReturn(DIRECT_LINES_COUNT);

        // Act
        infoModel.updateZoneInfo(X_COORDINATE, Y_COORDINATE);

        // Assert
        verify(infoPanel).updatePositionInfo(X_COORDINATE, Y_COORDINATE);
        verify(infoPanel).updateZoneName("Centre");
        verify(infoPanel).updateNumberOfPeople(PEOPLE_COUNT);
        verify(infoPanel).updateNumberOfBusiness(BUSINESS_COUNT);
        verify(infoPanel).updateAvaragePay(AVERAGE_PAY);
        verify(infoPanel).updateNumberOfDirectLines(DIRECT_LINES_COUNT);
    }

    /**
     * This test verifies the update of zone information when a zone does not exist.
     */
    @Test
    public void testUpdateZoneInfoWithNonExistingZone() {
        // Arrange
        when(cityModel.getZoneByPosition(new Pair<>(X_COORDINATE, Y_COORDINATE))).thenReturn(Optional.empty());

        // Act
        infoModel.updateZoneInfo(X_COORDINATE, Y_COORDINATE);

        // Assert
        verify(infoPanel).updatePositionInfo(X_COORDINATE, Y_COORDINATE);
        verify(infoPanel).updateZoneName("");
        verify(infoPanel).updateNumberOfPeople(0);
        verify(infoPanel).updateNumberOfBusiness(0);
        verify(infoPanel).updateAvaragePay(0);
        verify(infoPanel).updateNumberOfDirectLines(0);
    }
}

