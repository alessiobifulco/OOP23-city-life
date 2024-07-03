package unibo.citysimulation.model.person.api;

import unibo.citysimulation.model.zone.Zone;

/**
 * Represents the static data of a person, containing the name, age, business,
 * and residence zone.
 * 
 * @param name          the name of the person.
 * @param age           the age of the person.
 * @param residenceZone the zone where the person lives.
 */
public record PersonData(String name, int age, Zone residenceZone) {
}
