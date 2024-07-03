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
 * Implementation of the {@link PersonFactory} interface.
 */
public final class PersonFactoryImpl implements PersonFactory {
    private static Random random = new Random();

    /**
     * Creates all the people in the city simulation based on the given parameters.
     *
     * @param numberOfPeople the total number of people to create
     * @param zones          the list of zones in the city
     * @param businesses     the list of businesses in the city
     * @return a list of lists, where each inner list represents a group of people
     *         in a specific zone
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
     * Creates a group of people in a specific zone based on the given parameters.
     *
     * @param groupCounter   the counter for the group
     * @param numberOfPeople the number of people to create in the group
     * @param moneyMinMax    the minimum and maximum money values for the people in
     *                       the group
     * @param businesses     the list of businesses in the city
     * @param residenceZone  the zone where the people in the group reside
     * @return a list of dynamic persons representing the group of people
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
     * Creates a dynamic person with the given parameters.
     *
     * @param name          the name of the person
     * @param age           the age of the person
     * @param business      the optional business the person is associated with
     * @param residenceZone the zone where the person resides
     * @param money         the amount of money the person has
     * @return a dynamic person object
     */
    @Override
    public DynamicPerson createPerson(final String name, final int age, final Optional<Business> business,
            final Zone residenceZone, final int money) {
        return new DynamicPersonImpl(new PersonData(name, age, residenceZone), money, business);
    }
}
