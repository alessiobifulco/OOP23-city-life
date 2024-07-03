package unibo.citysimulation.model.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.utilities.BusinessType;
import unibo.citysimulation.model.zone.Zone;

/**
 * A factory class for creating instances of {@link Business}.
 */
public final class BusinessFactoryImpl {

    private static final Random RANDOM = new Random();
    private static int id;

    private BusinessFactoryImpl() {
    }

    /**
     * Creates a new business of the specified type in the given zone.
     *
     * @param type The type of the business to create.
     * @param zone The zone where the business will be located.
     * @return An {@link Optional} containing the created business, or an empty
     *         {@link Optional} if the business type is not supported.
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
     * Creates a random business in one of the given zones.
     *
     * @param zones The list of zones where the business can be located.
     * @return An {@link Optional} containing the created business, or an empty
     *         {@link Optional} if no zones are provided.
     */
    public static Optional<Business> createRandomBusiness(final List<Zone> zones) {
        final BusinessType type = BusinessType.values()[RANDOM.nextInt(BusinessType.values().length)];
        final Zone zone = zones.get(RANDOM.nextInt(zones.size()));
        return createBusiness(type, zone);
    }

    /**
     * Creates multiple businesses in the given zones.
     *
     * @param zones              The list of zones where the businesses can be
     *                           located.
     * @param numberOfBusinesses The number of businesses to create.
     * @return A list of created businesses.
     */
    public static List<Business> createMultipleBusiness(final List<Zone> zones, final int numberOfBusinesses) {
        final List<Business> businesses = new ArrayList<>();
        for (int i = 0; i < numberOfBusinesses; i++) {
            createRandomBusiness(zones).ifPresent(businesses::add);
        }
        return businesses;
    }
}
