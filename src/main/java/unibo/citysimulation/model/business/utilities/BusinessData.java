package unibo.citysimulation.model.business.utilities;

import java.time.LocalTime;
import java.util.List;

import unibo.citysimulation.model.business.impl.Employee;
import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;

/**
 * Represents the data of a business.
 *
 * @param id           The ID of the business.
 * @param employees    The list of employees working in the business.
 * @param openingTime  The opening time of the business.
 * @param closingTime  The closing time of the business.
 * @param revenue      The revenue of the business.
 * @param maxEmployees The maximum number of employees allowed in the business.
 * @param position     The position of the business.
 * @param minAge       The minimum age requirement for employees in the
 *                     business.
 * @param maxAge       The maximum age requirement for employees in the
 *                     business.
 * @param maxTardiness The maximum allowed tardiness for employees in the
 *                     business.
 * @param zone         The zone where the business is located.
 * @param businessType The type of the business.
 */
public record BusinessData(
                int id,
                List<Employee> employees,
                LocalTime openingTime,
                LocalTime closingTime,
                double revenue,
                int maxEmployees,
                Pair<Integer, Integer> position,
                int minAge,
                int maxAge,
                int maxTardiness,
                Zone zone,
                BusinessType businessType) {
}
