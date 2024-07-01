package unibo.citysimulation.model;

import unibo.citysimulation.model.person.api.DynamicPerson;

import java.util.List;

public class ReportLogic {

    public static String generateReport(CityModel cityModel) {
        List<DynamicPerson> people = cityModel.getAllPeople();
        long employedCount = people.stream().filter(p -> p.getBusiness().isPresent()).count();
        long unemployedCount = people.size() - employedCount;

        double averageOccupationRate = (double) employedCount / people.size();



        StringBuilder report = new StringBuilder();
        report.append("Simulation Report\n");
        report.append("=================\n");
        report.append("Total people: ").append(people.size()).append("\n");
        report.append("Employed people: ").append(employedCount).append("\n");
        report.append("Unemployed people: ").append(unemployedCount).append("\n");
        report.append("Average occupation rate: ").append(String.format("%.2f", averageOccupationRate * 100)).append("%\n");
    
        return report.toString();
    }
}
