package unibo.citysimulation.model;

import unibo.citysimulation.model.person.api.DynamicPerson;
import unibo.citysimulation.model.business.impl.EmploymentOfficeManager;
import unibo.citysimulation.model.transport.impl.TransportLineImpl;

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
        final int totalHired = EmploymentOfficeManager.getTotalHired();
        final int totalFired = EmploymentOfficeManager.getTotalFired();
        final double averageCongestion = TransportLineImpl.calculateAverageCongestion(cityModel.getTransportLines());
        final StringBuilder report = new StringBuilder(256);

        report
        .append("Simulation Report\n =================\n Total people: ").append(people.size())
        .append("\n Employed people: ").append(employedCount)
        .append("\n Unemployed people: ").append(unemployedCount)
        .append("\n Average occupation rate: ").append(String.format("%.2f", averageOccupationRate * 100))
        .append("%\n Total hired: ").append(totalHired)
        .append("\n Total fired: ").append(totalFired)
        .append("\n Average transport line congestion: ").append(String.format("%.2f", averageCongestion)).append("%\n");
        return report.toString();
    }
}
