package unibo.citylife.model.person;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.BusinessFactoryImpl;
import unibo.citysimulation.model.business.utilities.BusinessType;
import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.person.api.StaticPerson;
import unibo.citysimulation.model.person.api.StaticPerson.PersonState;
import unibo.citysimulation.model.person.impl.StaticPersonImpl;
import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.model.transport.impl.TransportFactoryImpl;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.model.zone.ZoneCreation;
import unibo.citysimulation.model.zone.ZoneTableCreation;
import unibo.citysimulation.utilities.Pair;

import java.util.Optional;
import java.util.Random;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaticPersonImplTest {
    private final List<Zone> zones = ZoneCreation.createZonesFromFile();
    private final List<TransportLine> transports = new TransportFactoryImpl().createTransportsFromFile(zones);
    private final Random random = new Random();
    private StaticPerson staticPerson;

    @BeforeEach
    void setUp() {
        final Zone residenceZone = zones.get(random.nextInt(zones.size()));
        Business business;
        do {
            business = BusinessFactoryImpl.createBusiness(BusinessType.BIG, zones.get(random.nextInt(zones.size()))).get();
        } while (business.getBusinessData().zone() == residenceZone);
        ZoneTableCreation.createAndAddPairs(zones, transports);
        // Simuliamo un dato di una persona per i test
        final PersonData personData = new PersonData("Mario", 30, residenceZone);
        staticPerson = new StaticPersonImpl(personData, 100.00, Optional.of(business));
    }

    @Test
    void testGetPersonData() {
        final PersonData personData = staticPerson.getPersonData();
        final int expectedAge = 30;
        assertEquals("Mario", personData.name());
        assertEquals(expectedAge, personData.age());
    }

    @Test
    void testGetPosition() {
        final Optional<Pair<Integer, Integer>> position = staticPerson.getPosition();
        assertTrue(position.isPresent());
        assertTrue(staticPerson.getPersonData().residenceZone().boundary().isInside(position.get().getFirst(),
            position.get().getSecond()));
    }

    @Test
    void testGetMoney() {
        assertEquals(100.00, staticPerson.getMoney());
    }

    @Test
    void testAddMoney() {
        final int moneyToAdd = 50;
        final int expectedMoney = 150;
        staticPerson.addMoney(moneyToAdd);
        assertEquals(expectedMoney, staticPerson.getMoney());
    }

    @Test
    void testGetState() {
        assertEquals(PersonState.AT_HOME, staticPerson.getState());
    }
}
