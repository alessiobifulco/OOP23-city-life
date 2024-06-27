package unibo.citysimulation.model.business.impl;

import java.time.LocalTime;
import java.util.List;

import unibo.citysimulation.model.zone.Zone;
import unibo.citysimulation.utilities.Pair;

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
        Zone zone) {
}
