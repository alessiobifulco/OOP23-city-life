package unibo.citysimulation.model.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.utilities.BusinessConfig;
import unibo.citysimulation.model.business.utilities.BusinessType;
import unibo.citysimulation.model.zone.Zone;

/**
 * The BusinessFactoryImpl class is responsible for creating instances of Business objects.
 */
public final class BusinessFactoryImpl {

    private static final Random RANDOM = new Random();
    private static int id = 0;

    private BusinessFactoryImpl() {
    }

    /**
     * Creates a new Business object based on the specified BusinessType.
     *
     * @param type The type of business to create.
     * @param zone The zone where the business is located.
     * @return An Optional containing the created Business object, or an empty Optional if the type is invalid.
     */
    public static Optional<Business> createBusiness(final BusinessType type, final Zone zone) {
        return switch (type) {
            case BIG -> Optional.of(new BigBusiness(id++, zone));
            case MEDIUM -> Optional.of(new MediumBusiness(id++, zone));
            case SMALL -> Optional.of(new SmallBusiness(id++, zone));
            default -> Optional.empty();
        };
    }
    /**
     * Creates a random Business object.
     *
     * @param zones The list of available zones.
     * @return An Optional containing the created Business object.
     */
    public static Optional<Business> createRandomBusiness(final List<Zone> zones) {
        final BusinessType type = BusinessType.values()[RANDOM.nextInt(BusinessType.values().length)];
        final Zone zone = zones.get(RANDOM.nextInt(zones.size()));
        return createBusiness(type, zone);
    }

    /**
     * Creates multiple Business objects.
     *
     * @param zones The list of available zones.
     * @param numberOfPeople The number of people.
     * @return A collection of created Business objects.
     */
    public static Collection<Business> createMultipleBusiness(final List<Zone> zones, final int numberOfPeople) {
        final List<Business> businesses = new ArrayList<>();
        for (int i = 0; i < numberOfPeople / BusinessConfig.BUSINESS_PERCENTAGE; i++) {
            createRandomBusiness(zones).ifPresent(businesses::add);
        }
        return businesses;
    }
}
