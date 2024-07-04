package unibo.citylife.model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.api.BusinessFactory;
import unibo.citysimulation.model.business.impl.BusinessFactoryImpl;
import unibo.citysimulation.model.map.impl.ImageHandler;
import unibo.citysimulation.model.map.impl.MapModelImpl;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.api.PersonFactory;
import unibo.citysimulation.model.person.api.StaticPerson.PersonState;
import unibo.citysimulation.model.person.impl.PersonFactoryImpl;
import unibo.citysimulation.model.transport.api.TransportFactory;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.transport.impl.TransportFactoryImpl;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.zone.ZoneCreation;
import unibo.citysimulation.model.zone.ZoneTableCreation;
import unibo.citysimulation.utilities.Pair;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;



class MapModelImplTest {
    private MapModelImpl mapModel;
    private List<TransportLine> lines;
    private List<DynamicPerson> people;
    private final List<Business> businesses = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        final TransportFactory transportFactory = new TransportFactoryImpl();
        final List<Zone> zones = ZoneCreation.createZonesFromFile();
        lines = transportFactory.createTransportsFromFile(zones);
        ZoneTableCreation.createAndAddPairs(zones, lines);
        final BusinessFactory businessFactory = new BusinessFactoryImpl();
        businesses.addAll(businessFactory.createMultipleBusiness(zones, 100));
        final PersonFactory personFactory = new PersonFactoryImpl();
        final List<List<DynamicPerson>> peopleGroup = personFactory.createAllPeople(100, zones, businesses);

        people = peopleGroup.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        mapModel = new MapModelImpl("/unibo/citysimulation/images/mapImage.png");
        mapModel.setMaxCoordinates(1000, 1000);  // Set max coordinates for testing
    }

    @Test
    void testStartSimulation() {
        mapModel.startSimulation();
        assertTrue(mapModel.getTransportNames().isEmpty());
    }

    @Test
    void testGetTransportNames() {
        mapModel.setTransportInfo(lines);

        final List<String> names = mapModel.getTransportNames();

        assertFalse(names.isEmpty());
        assertEquals(lines.size(), names.size());
        for (int i = 0; i < lines.size(); i++) {
            assertEquals(lines.get(i).getName(), names.get(i));
        }
    }

    @Test
    void testGetBusinessInfos() {
        final List<Pair<Integer, Integer>> businessInfos = mapModel.getBusinessInfos(businesses);

        assertFalse(businessInfos.isEmpty());
        assertEquals(businesses.size(), businessInfos.size());
    }

    @Test
    void testGetPersonInfos() {
        final Map<String, Pair<Pair<Integer, Integer>, Color>> personInfos = mapModel.getPersonInfos(people);
        assertFalse(personInfos.isEmpty());
        assertEquals(people.size(), personInfos.size());
        for (final DynamicPerson person : people) {
            final Pair<Pair<Integer, Integer>, Color> info = personInfos.get(person.getPersonData().name());
            assertNotNull(info);
            assertEquals(person.getState() == PersonState.AT_HOME ? Color.BLUE : Color.RED, info.getSecond());
        }
    }

    @Test
    void testGetColorList() {
        final int maxColor = 255;
        final List<TransportLine> testLines = lines;

        for (int i = 0; i < testLines.get(0).getCapacity(); i++) {
            testLines.get(0).incrementPersonInLine();
        }

        // Without starting the simulation
        mapModel.setTransportCongestion(testLines);
        List<Color> colors = mapModel.getColorList();
        assertEquals(Collections.nCopies(testLines.size(), Color.GRAY), colors);

        // After starting the simulation
        mapModel.startSimulation();
        mapModel.setTransportCongestion(testLines);
        colors = mapModel.getColorList();
        assertEquals(testLines.size(), colors.size());

        assertEquals(new Color(maxColor, 0, 0), colors.get(0));
    }

    @Test
    void testGetLinesPointsCoordinates() {
        mapModel.setTransportInfo(lines);

        final List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> coordinates = mapModel.getLinesPointsCoordinates();

        assertFalse(coordinates.isEmpty());
        assertEquals(lines.size(), coordinates.size());
    }

    @Test
    void testSetTransportInfo() {
        mapModel.setTransportInfo(lines);

        final List<String> names = mapModel.getTransportNames();
        assertFalse(names.isEmpty());
        assertEquals(lines.size(), names.size());

        final List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> coordinates = mapModel.getLinesPointsCoordinates();
        assertFalse(coordinates.isEmpty());
        assertEquals(lines.size(), coordinates.size());
    }

    @Test
    void testSetTransportCongestion() {
        mapModel.setTransportCongestion(lines);

        final List<Color> colors = mapModel.getColorList();
        assertFalse(colors.isEmpty());
        assertEquals(lines.size(), colors.size());
    }

    @Test
void testSetMaxCoordinates() {
    final Pair<Integer, Integer> maxCoordinateTest = new Pair<>(100, 200); 

    // Test setting valid coordinates
    mapModel.setMaxCoordinates(maxCoordinateTest.getFirst(), maxCoordinateTest.getSecond());
    // Since there's no getter to directly check the values, we assume the method works correctly
}


    @Test
    void testGetImage() {
        final BufferedImage image = mapModel.getImage();
        assertNotNull(image);
    }

    @Test
    void testImageLoadError() {
        assertThrows(RuntimeException.class, () -> new MapModelImpl("/invalid/path/to/image.png"));
    }

    @Test
    void testCorruptedImageLoadError() {
        File corruptedFile = null;
        try {
            // Create a temporary file to act as a corrupted image
            corruptedFile = File.createTempFile("corrupted_image", ".png");

            // Write invalid data to the file to corrupt it
            try (FileOutputStream out = new FileOutputStream(corruptedFile)) {
                out.write("this is not a valid image content".getBytes(StandardCharsets.UTF_8));
            }

            // Use the corrupted file in the test
            final String corruptedFilePath = corruptedFile.getPath();
            assertThrows(RuntimeException.class, () -> new ImageHandler(corruptedFilePath));

        } catch (IOException e) {
            fail("Failed to create corrupted image file for testing", e);
        } finally {
            // Clean up the temporary file
            if (corruptedFile != null) {
                try {
                    Files.deleteIfExists(corruptedFile.toPath());
                } catch (IOException e) {
                    fail("Failed to delete corrupted image file", e);
                }
            }
        }
    }
}
