package unibo.citysimulation.model.business.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Random;

import unibo.citysimulation.model.business.api.Business;
import unibo.citysimulation.model.business.api.EmploymentOfficeBehavior;
import unibo.citysimulation.model.business.utilities.EmploymentOfficeData;
import unibo.citysimulation.model.person.api.DynamicPerson;

/**
 * The EmploymentOfficeManager class implements the EmploymentOfficeBehavior
 * interface and provides the behavior for managing employees in a business.
 */
public class EmploymentOfficeManager implements EmploymentOfficeBehavior {

    private final EmploymentOfficeData employmentOffice;
    private final Random random;
    private static final int ZERO = 0;
    private static final LocalTime TIME_ZERO = LocalTime.of(ZERO, ZERO);

    /**
     * Constructs an EmploymentOfficeManager object with the given employment office
     * data.
     * 
     * @param employmentOffice the employment office data
     */
    public EmploymentOfficeManager(final EmploymentOfficeData employmentOffice) {
        this.employmentOffice = employmentOffice;
        this.random = new Random();
    }

    /**
     * Handles the firing of employees in the business.
     * 
     * @param business the business to handle employee firing for
     */
    @Override
    public final void handleEmployeeFiring(final Business business) {
        final List<Employee> employeesToFire = getEmployeesToFire(business);
        final int numToFire = random.nextInt(employeesToFire.size() + 1);
        fireEmployees(business, employeesToFire.stream().limit(numToFire).collect(Collectors.toList()));
    }

    /**
     * Handles the hiring of employees in the business.
     * 
     * @param business the business to handle employee hiring for
     */
    @Override
    public final void handleEmployeeHiring(final Business business) {
        while (canHire(business)) {
            final Optional<List<DynamicPerson>> peopleToHire = getPeopleToHire(business);
            if (peopleToHire.isPresent()) {
                final int numToHire = random.nextInt(peopleToHire.get().size() + 1);
                hirePeople(business, peopleToHire.get().stream().limit(numToHire).collect(Collectors.toList()));
            } else {
                break;
            }
        }
    }

    /**
     * Checks if the business can hire more employees.
     * 
     * @param business the business to check
     * @return true if the business can hire more employees, false otherwise
     */
    private boolean canHire(final Business business) {
        return business.getBusinessData().employees().size() < business.getBusinessData().maxEmployees();
    }

    /**
     * Checks if an employee should be fired based on their tardiness.
     * 
     * @param employee the employee to check
     * @return true if the employee should be fired, false otherwise
     */
    private boolean shouldFire(final Employee employee) {
        return employee != null && employee.count() > employee.businessData().maxTardiness();
    }

    /**
     * Gets a list of people eligible to be hired by the business.
     * 
     * @param business the business to get eligible people for
     * @return an optional list of eligible people, or an empty optional if no
     *         eligible people are found
     */
    private Optional<List<DynamicPerson>> getPeopleToHire(final Business business) {
        final int availableSpots = business.getBusinessData().maxEmployees()
                - business.getBusinessData().employees().size();
        if (availableSpots > 0) {
            final List<DynamicPerson> disoccupiedPeople = employmentOffice.disoccupied();
            final List<DynamicPerson> eligiblePeople = disoccupiedPeople.stream()
                    .filter(person -> !person.getPersonData().residenceZone().equals(business.getBusinessData().zone()))
                    .collect(Collectors.toList());
            if (!eligiblePeople.isEmpty()) {
                return Optional.of(eligiblePeople.subList(0, Math.min(availableSpots, eligiblePeople.size())));
            }
        }
        return Optional.empty();
    }

    /**
     * Hires the specified people for the business.
     * 
     * @param business     the business to hire people for
     * @param peopleToHire the list of people to hire
     */
    private void hirePeople(final Business business, final List<DynamicPerson> peopleToHire) {

        for (final DynamicPerson person : peopleToHire) {
            if (canHire(business)) {
                final Employee employee = new Employee(person, business.getBusinessData());
                business.hire(employee);
                employmentOffice.disoccupied().remove(person);
                person.setBusiness(Optional.of(business));
                person.setBusinessBegin(business.getBusinessData().openingTime());
                person.setBusinessEnd(business.getBusinessData().closingTime());
            }
        }
    }

    /**
     * Gets a list of employees to be fired from the business.
     * 
     * @param business the business to get employees to fire from
     * @return a list of employees to fire
     */
    private List<Employee> getEmployeesToFire(final Business business) {
        return business.getBusinessData().employees().stream()
                .filter(this::shouldFire)
                .collect(Collectors.toList());
    }

    /**
     * Fires the specified employees from the business.
     * 
     * @param business        the business to fire employees from
     * @param employeesToFire the list of employees to fire
     */
    private void fireEmployees(final Business business, final List<Employee> employeesToFire) {
        employeesToFire.forEach(employee -> {
            employmentOffice.disoccupied().add(employee.person());
            business.fire(employee);
            employee.person().setBusiness(Optional.empty());
            employee.person().setBusinessBegin(TIME_ZERO);
            employee.person().setBusinessEnd(TIME_ZERO);
        });
    }

    /**
     * Handles the payment of employees in the business.
     * 
     * @param business the business to handle employee pay for
     */
    @Override
    public final void handleEmployeePay(final Business business) {
        business.getBusinessData().employees().forEach(employee -> {
            final double pay = business.calculatePay();
            employee.person().addMoney(pay);
        });
    }
}
