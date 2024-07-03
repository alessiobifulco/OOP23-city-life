package unibo.citysimulation.model;

import unibo.citysimulation.model.person.api.DynamicPerson;

import java.util.List;

/**
 * Utility class for generating simulation reports.
 */
public final class ReportLogic {

    // Private constructor to prevent instantiation
    private ReportLogic() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a report based on the current state of the city model.
     * 
     * @param cityModel the city model containing all simulation data
     * @return a formatted string report
     */
    public static String generateReport(final CityModel cityModel) {
        final List<DynamicPerson> people = cityModel.getAllPeople();
        final long employedCount = people.stream().filter(p -> p.getBusiness().isPresent()).count();
        final long unemployedCount = people.size() - employedCount;

        final double averageOccupationRate = (double) employedCount / people.size();

        final StringBuilder report = new StringBuilder(256);  

        report.append("Simulation Report\n")
              .append("=================\n")
              .append("Total people: ").append(people.size()).append("\n")
              .append("Employed people: ").append(employedCount).append("\n")
              .append("Unemployed people: ").append(unemployedCount).append("\n")
              .append("Average occupation rate: ")
              .append(String.format("%.2f", averageOccupationRate * 100)).append("%\n");

        return report.toString();
    }
}
