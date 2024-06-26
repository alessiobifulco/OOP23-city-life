package unibo.citysimulation.model.person.impl;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.impl.Employee;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.person.api.PersonFactory;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.utilities.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The PersonFactory class is responsible for creating instances of
 * DynamicPerson objects.
 */
public final class PersonFactoryImpl implements PersonFactory {
    private static Random random = new Random();

    /**
     * Creates all the people for the simulation.
     *
     * @param numberOfPeople The total number of people to create, given in input.
     * @param zones          The list of available zones.
     * @param businesses     The list of available businesses.
     * @return A list of lists of DynamicPerson objects for every zone.
     */
    @Override
    public List<List<DynamicPerson>> createAllPeople(final int numberOfPeople, final List<Zone> zones,
            final List<Business> businesses) {
        return zones.stream()
                .map(zone -> createGroupOfPeople(
                        zones.indexOf(zone),
                        (int) (numberOfPeople * (zone.personPercents() / 100.0)),
                        zone.wellfareMinMax(),
                        businesses,
                        zone))
                .collect(Collectors.toList());
    }

    /**
     * Creates a group of people for a certain zone.
     * 
     * @param groupCounter   a counter for the group of people.
     * @param numberOfPeople The number of people to create for the given zone.
     * @param moneyMinMax    The minimum and maximum amount of money that the people
     *                       can have in that zone.
     * @param businesses     The list of available businesses.
     * @param residenceZone  The zone where this group of people will live.
     * @return A list of DynamicPerson objects for the given zone.
     */
    @Override
    public List<DynamicPerson> createGroupOfPeople(final int groupCounter, final int numberOfPeople,
            final Pair<Integer, Integer> moneyMinMax,
            final List<Business> businesses, final Zone residenceZone) {
        final List<DynamicPerson> people = new ArrayList<>();
        for (int i = 0; i < numberOfPeople; i++) {
            final DynamicPerson person = createPerson(
                    "Person" + groupCounter + i,
                    random.nextInt((ConstantAndResourceLoader.MAX_RANDOM_AGE - ConstantAndResourceLoader.MIN_AGE)
                    + 1) + ConstantAndResourceLoader.MIN_AGE,
                    Optional.empty(), 
                    residenceZone,
                    random.nextInt(moneyMinMax.getSecond() - moneyMinMax.getFirst()) + moneyMinMax.getFirst());
            people.add(person);
        }
        for (final DynamicPerson person : people) {
            boolean hired = false;
            for (final Business business : businesses) {
                if (person.getPersonData().age() >= business.getBusinessData().minAge()
                        && person.getPersonData().age() <= business.getBusinessData().maxAge()
                        && business.getBusinessData().employees().size() < business.getBusinessData().maxEmployees() 
                        && !business.getBusinessData().zone().equals(person.getPersonData().residenceZone())) {
                    business.hire(new Employee(person, business.getBusinessData()));
                    person.setBusiness(Optional.of(business));
                    person.setBusinessBegin(business.getBusinessData().openingTime());
                    person.setBusinessEnd(business.getBusinessData().closingTime());
                    hired = true;
                    break;
                }
            }
            if (hired) {
                continue;
            }
        }
        return people;
    }

    /**
     * Creates a single person.
     * 
     * @param name          The name of the person.
     * @param age           The age of the person.
     * @param business      The business where the person works, if any.
     * @param residenceZone The zone where the person lives.
     * @param money         The amount of money that the person has at the creation
     *                      moment.
     * @return A DynamicPerson object.
     */
    @Override
    public DynamicPerson createPerson(final String name, final int age, final Optional<Business> business,
            final Zone residenceZone, final int money) {
                return new DynamicPersonImpl(new PersonData(name, age, residenceZone), money, business);
    }
}
