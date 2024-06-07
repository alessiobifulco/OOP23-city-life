package unibo.citylife.model.person;
import org.junit.jupiter.api.Test;

import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.zone.ZoneCreation;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonDataTest {
    private final List<Zone> zones = ZoneCreation.createZonesFromFile();
    private final Random random = new Random();
    @Test
    void testPersonDataConstructor() {
        final String name = "John";
        final int age = 30;
        final Zone residenceZone = zones.get(random.nextInt(zones.size()));
        final PersonData personData = new PersonData(name, age, residenceZone);

        assertNotNull(personData);
        assertEquals(name, personData.name());
        assertEquals(age, personData.age());
        assertEquals(residenceZone, personData.residenceZone());
    }
}
