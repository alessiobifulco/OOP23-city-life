package unibo.citysimulation.model.business.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import unibo.citysimulation.model.business.api.EmploymentOfficeBehavior;
import unibo.citysimulation.model.business.utilities.EmploymentOfficeData;
import unibo.citysimulation.model.person.api.DynamicPerson;

/**
 * The EmploymentOfficeManager class manages the hiring and firing of employees for businesses.
 */
public class EmploymentOfficeManager implements EmploymentOfficeBehavior {

    private final EmploymentOfficeData employmentOffice;
    private static final int ZERO = 0;
    private static final LocalTime TIME_ZERO = LocalTime.of(ZERO, ZERO);
    private static final int MAX_FIRING_PER_CYCLE = 3;
    private static final int MAX_HIRING_PER_CYCLE = 2;

    /**
     * Constructs an EmploymentOfficeManager with the given employment office.
     *
     * @param employmentOffice The employment office to interact with.
     */
    public EmploymentOfficeManager(final EmploymentOfficeData employmentOffice) {
        this.employmentOffice = employmentOffice;
    }

    /**
     * Handles both the firing and hiring of employees for the specified business.
     * Ensures that all employees who should be fired are fired and continues to hire employees
     * until the maximum number of employees is reached.
     *
     * @param business The business for which to handle employee management.
     */
    @Override
    public final void handleEmployees(final Business business) {
        handleEmployeeFiring(business);
        handleEmployeeHiring(business);
    }

    /**
     * Handles the firing of employees for the specified business.
     *
     * @param business The business for which to handle employee firing.
     */
    @Override
    public final void handleEmployeeFiring(final Business business) {
        final List<Employee> employeesToFire = getEmployeesToFire(business).stream()
                .limit(MAX_FIRING_PER_CYCLE)
                .collect(Collectors.toList());
        fireEmployees(business, employeesToFire);
    }

    /**
     * Handles the hiring of employees for the specified business.
     *
     * @param business The business for which to handle employee hiring.
     */
    @Override
    public final void handleEmployeeHiring(final Business business) {
        int hires = 0;
        while (canHire(business) && hires < MAX_HIRING_PER_CYCLE) {
            final Optional<List<DynamicPerson>> peopleToHire = getPeopleToHire(business);
            if (peopleToHire.isPresent()) {
                hires += hirePeople(business, peopleToHire.get());
            } else {
                break;
            }
        }
    }

    /**
     * Checks if a business can hire more employees.
     *
     * @param business The business to check.
     * @return true if the business can hire more employees, false otherwise.
     */
    private boolean canHire(final Business business) {
        return business.getBusinessData().employees().size() < business.getBusinessData().maxEmployees();
    }

    /**
     * Checks if an employee should be fired based on their delay count.
     *
     * @param employee The employee to check.
     * @return true if the employee should be fired, false otherwise.
     */
    private boolean shouldFire(final Employee employee) {
        return employee != null && employee.count() > employee.businessData().maxTardiness();
    }

    /**
     * Retrieves a random number of disoccupied people to be hired by the business.
     * Filters out people who live in the same zone as the business.
     *
     * @param business The business that will hire the people.
     * @return The list of people to be hired.
     */
    private Optional<List<DynamicPerson>> getPeopleToHire(final Business business) {
        final int availableSpots = business.getBusinessData().maxEmployees() - business.getBusinessData().employees().size();
        if (availableSpots > 0) {
            final List<DynamicPerson> disoccupiedPeople = employmentOffice.disoccupied();
            System.out.println("Disoccupied people: " + disoccupiedPeople.size());

            final List<DynamicPerson> eligiblePeople = disoccupiedPeople.stream()
                    .filter(person -> !person.getPersonData().residenceZone().equals(business.getBusinessData().zone()))
                    .collect(Collectors.toList());

            System.out.println("Eligible people: " + eligiblePeople.size());

            if (!eligiblePeople.isEmpty()) {
                return Optional.of(eligiblePeople.subList(0, Math.min(availableSpots, eligiblePeople.size())));
            }
        }
        return Optional.empty();
    }

    /**
     * Hires the specified people for the given business and removes them from the employment office.
     *
     * @param business The business that will hire the people.
     * @param peopleToHire The list of people to be hired.
     * @return The number of people hired.
     */
    private int hirePeople(final Business business, final List<DynamicPerson> peopleToHire) {
        int hiredCount = 0;
        for (DynamicPerson person : peopleToHire) {
            if (canHire(business) && hiredCount < MAX_HIRING_PER_CYCLE) {
                final Employee employee = new Employee(person, business.getBusinessData());
                business.hire(employee);
                employmentOffice.disoccupied().remove(person);
                person.setBusiness(Optional.of(business));
                hiredCount++;
            }
        }
        return hiredCount;
    }

    /**
     * Retrieves a list of employees that should be fired from the specified business.
     *
     * @param business The business to check for employees to fire.
     * @return The list of employees to be fired.
     */
    private List<Employee> getEmployeesToFire(final Business business) {
        return business.getBusinessData().employees().stream()
                .filter(this::shouldFire)
                .collect(Collectors.toList());
    }

    /**
     * Fires the specified employees and adds them to the employment office's disoccupied people list.
     *
     * @param business The business that will fire the employees.
     * @param employeesToFire The list of employees to be fired.
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
     * Handles the payment for all employees in the given business.
     * Calculates the pay for each employee and adds it to their personal account.
     *
     * @param business the business for which the payment is being handled
     */
    @Override
    public final void handleEmployeePay(final Business business) {
        business.getBusinessData().employees().forEach(employee -> {
            final double pay = business.calculatePay();
            employee.person().addMoney(pay);
        });
    }
}
