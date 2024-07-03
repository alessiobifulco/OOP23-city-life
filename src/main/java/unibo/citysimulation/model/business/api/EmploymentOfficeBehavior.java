package unibo.citysimulation.model.business.api;

/**
 * The EmploymentOfficeBehavior interface represents the behavior of an
 * employment office in a city simulation.
 * It defines methods for handling employee firing, hiring, and payment.
 */
public interface EmploymentOfficeBehavior {

    /**
     * Handles the firing of an employee from a business.
     * 
     * @param business the business from which the employee is being fired
     */
    void handleEmployeeFiring(Business business);

    /**
     * Handles the hiring of an employee by a business.
     * 
     * @param business the business that is hiring the employee
     */
    void handleEmployeeHiring(Business business);

    /**
     * Handles the payment of an employee by a business.
     * 
     * @param business the business that is paying the employee
     */
    void handleEmployeePay(Business business);
}
