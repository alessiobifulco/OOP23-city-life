package unibo.citysimulation.model.person.factory;

import unibo.citysimulation.model.business.impl.Business;
import unibo.citysimulation.model.business.impl.Employee;
import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.person.api.PersonData;
import unibo.citysimulation.model.person.impl.DynamicPersonImpl;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import unibo.citysimulation.utilities.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Optional;


/**
 * The PersonFactory class is responsible for creating instances of
 * DynamicPerson objects.
 */
public final class PersonFactory {
    private static Random random = new Random();

    private PersonFactory() {
    }

    /**
     * Creates a list of people based on the specified parameters.
     *
     * @param numberOfPeople The total number of people to create, given in input.
     * @param zones          The list of available zones.
     * @param businesses     The list of available businesses.
     * @return A list of lists of DynamicPerson objects for every zone.
     */
    public static List<List<DynamicPerson>> createAllPeople(final int numberOfPeople, final List<Zone> zones,
            final List<Business> businesses) {
        final List<List<DynamicPerson>> allPeople = new ArrayList<>();
        for (final Zone zone : zones) {
            final int zoneIndex = zones.indexOf(zone);
            final int peopleInZone = (int) (numberOfPeople * (zone.personPercents() / 100.0));
            final List<DynamicPerson> peopleInCurrentZone = createGroupOfPeople(
                    zoneIndex,
                    peopleInZone,
                    zone.wellfareMinMax(),
                    businesses,
                    zone);
            allPeople.add(peopleInCurrentZone);
        }
        return allPeople;
    }

    private static List<DynamicPerson> createGroupOfPeople(final int groupCounter, final int numberOfPeople,
            final Pair<Integer, Integer> moneyMinMax,
            final List<Business> businesses, final Zone residenceZone) {
                final List<DynamicPerson> people = new ArrayList<>();
                for (int i = 0; i < numberOfPeople; i++) {
                    final List<Business> eligibleBusinesses = businesses.stream()
                            .filter(business -> !business.getBusinessData().zone().equals(residenceZone))
                            .collect(Collectors.toList());
                    if (eligibleBusinesses.isEmpty()) {
                        throw new IllegalStateException("No eligible businesses found for zone: " + residenceZone.name());
                    }
                    final Optional<Business> optionalBusiness = Optional.ofNullable(
                            eligibleBusinesses.get(random.nextInt(eligibleBusinesses.size())));
                    final DynamicPerson person = createPerson(
                            "Person" + groupCounter + i,
                            random.nextInt(ConstantAndResourceLoader.MAX_RANDOM_AGE) + ConstantAndResourceLoader.MIN_AGE,
                            optionalBusiness,
                            residenceZone,
                            random.nextInt(moneyMinMax.getSecond() - moneyMinMax.getFirst()) + moneyMinMax.getFirst()
                    );
                    optionalBusiness.ifPresentOrElse(
                        business -> {
                            business.hire(new Employee(person, business.getBusinessData()));
                        }, 
                    () -> Optional.empty());
                    people.add(person);
                }
                return people;
    }

    private static DynamicPerson createPerson(final String name, final int age, final Optional<Business> business,
            final Zone residenceZone, final int money) {
        return new DynamicPersonImpl(new PersonData(name, age, business, residenceZone), money);
    }
}