package unibo.citylife.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unibo.citysimulation.model.CityModel;
import unibo.citysimulation.model.CityModelImpl;
import unibo.citysimulation.model.InputModel;
import unibo.citysimulation.model.clock.api.ClockModel;
import unibo.citysimulation.model.graphics.api.GraphicsModel;
import unibo.citysimulation.model.map.api.MapModel;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.zone.Boundary;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CityModelTest {
    private CityModel cityModel;
    private static final int TEST_ZONE_X = 5;
    private static final int TEST_ZONE_Y = 5;

    @BeforeEach
    void setUpCityModel() {
        cityModel = new CityModelImpl();
        cityModel.createEntities(); // assuming this method initializes people and businesses
    }
    @Test
    void testCreateEntities() {
        assertTrue(cityModel.isPeoplePresent());
        assertTrue(cityModel.isBusinessesPresent());
    }

    @Test
    void testIsPositionInZone() {
        final Pair<Integer, Integer> position = new Pair<>(TEST_ZONE_X, TEST_ZONE_Y);
        final Zone zone = new Zone("prova", 0, 0, position, position, new Boundary(0, 0, 10, 10));
        assertTrue(cityModel.isPositionInZone(position, zone));
    }
    @Test
    void testGetNumberOfDirectLinesFromZone() {
        final Zone zone = new Zone("test", 0, 0, new Pair<>(1, 10), new Pair<>(1, 100), new Boundary(0, 0, 10, 10));
        final int lines = cityModel.getNumberOfDirectLinesFromZone(zone);
        assertTrue(lines >= 0);
    }

    @Test
    void testGetMapModel() {
        final MapModel mapModel = cityModel.getMapModel();
        assertNotNull(mapModel);
    }

    @Test
    void testGetClockModel() {
        final ClockModel clockModel = cityModel.getClockModel();
        assertNotNull(clockModel);
    }

    @Test
    void testGetInputModel() {
        final InputModel inputModel = cityModel.getInputModel();
        assertNotNull(inputModel);
    }

    @Test
    void testGetGraphicsModel() {
        final GraphicsModel graphicsModel = cityModel.getGraphicsModel();
        assertNotNull(graphicsModel);
    }

    @Test
    void testGetZones() {
        final List<Zone> zones = cityModel.getZones();
        assertNotNull(zones);
        assertFalse(zones.isEmpty());
    }

    @Test
    void testGetTransportLines() {
        final List<TransportLine> transportLines = cityModel.getTransportLines();
        assertNotNull(transportLines);
        assertFalse(transportLines.isEmpty());
    }


    @Test
    void testIsPeoplePresent() {
        assertTrue(cityModel.isPeoplePresent());
    }

    @Test
    void testIsBusinessesPresent() {
        assertTrue(cityModel.isBusinessesPresent());
    }

    @Test
    void testGetFrameWidth() {
        final int width = cityModel.getFrameWidth();
        assertTrue(width > 0);
    }

    @Test
    void testGetFrameHeight() {
        final int height = cityModel.getFrameHeight();
        assertTrue(height > 0);
    }

    @Test
    void testGetPeopleInZone() {
        final Optional<Integer> peopleInZone = cityModel.getPeopleInZone("Zone1");
        assertTrue(peopleInZone.isPresent());
    }

    @Test
    void testGetBusinessesInZone() {
        final int businessesInZone = cityModel.getBusinessesInZone("Zone1");
        assertTrue(businessesInZone >= 0);
    }
}
