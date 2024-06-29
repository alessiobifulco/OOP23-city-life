package unibo.citylife.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unibo.citysimulation.model.InputModel;
import unibo.citysimulation.model.InputModelImpl;
import unibo.citysimulation.utilities.ConstantAndResourceLoader;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the InputModel class.
 */
final class InputModelTest {
    private static final int INITIAL_PEOPLE_PERCENTAGE = 40;
    private static final int EXPECTED_INITIAL_PEOPLE = calculateExpectedPeople(INITIAL_PEOPLE_PERCENTAGE);
    private static final int INCREASED_PEOPLE_PERCENTAGE = 60;
    private static final int EXPECTED_INCREASED_PEOPLE = calculateExpectedPeople(INCREASED_PEOPLE_PERCENTAGE);
    private static final int INITIAL_BUSINESS_COUNT = 15;
    private static final int ADDITIONAL_BUSINESS_COUNT = 10;
    private static final int EXPECTED_TOTAL_BUSINESS_COUNT = INITIAL_BUSINESS_COUNT + ADDITIONAL_BUSINESS_COUNT;
    private static final int TEST_CAPACITY = 200;
    private static final int TEST_RICHNESS = 250;

    private InputModel inputModel;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    void setUp() {
        inputModel = new InputModelImpl();
        inputModel.addNumberOfBusiness(INITIAL_BUSINESS_COUNT);
    }

    /**
     * Tests the setNumberOfPeople and getNumberOfPeople methods.
     */
    @Test
    void testNumberOfPeople() {
        inputModel.setNumberOfPeople(INITIAL_PEOPLE_PERCENTAGE);
        assertEquals(EXPECTED_INITIAL_PEOPLE, inputModel.getNumberOfPeople());

        inputModel.setNumberOfPeople(INCREASED_PEOPLE_PERCENTAGE);
        assertEquals(EXPECTED_INCREASED_PEOPLE, inputModel.getNumberOfPeople());
    }

    /**
     * Tests the addNumberOfBusiness and getNumberOfBusiness methods.
     */
    @Test
    void testNumberOfBusiness() {
        inputModel.incrementNumberOfBusiness(ADDITIONAL_BUSINESS_COUNT);
        assertEquals(EXPECTED_TOTAL_BUSINESS_COUNT, inputModel.getNumberOfBusiness());
    }

    /**
     * Tests the setCapacity and getCapacity methods.
     */
    @Test
    void testCapacity() {
        inputModel.setCapacity(TEST_CAPACITY);
        assertEquals(TEST_CAPACITY, inputModel.getCapacity());
    }

    /**
     * Tests the setRichness and getRichness methods.
     */
    @Test
    void testRichness() {
        inputModel.setRichness(TEST_RICHNESS);
        assertEquals(TEST_RICHNESS, inputModel.getRichness());
    }

    /**
     * Calculates the expected number of people based on the percentage.
     *
     * @param percentage the percentage to calculate
     * @return the expected number of people
     */
    private static int calculateExpectedPeople(final int percentage) {
        final int range = ConstantAndResourceLoader.MAX_PEOPLE - ConstantAndResourceLoader.MIN_PEOPLE;
        return percentage * range / 100 + ConstantAndResourceLoader.MIN_PEOPLE;
    }
}
