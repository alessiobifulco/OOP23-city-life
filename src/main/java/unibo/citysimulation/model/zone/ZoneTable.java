package unibo.citysimulation.model.zone;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import unibo.citysimulation.model.transport.api.TransportLine;
import unibo.citysimulation.utilities.Pair;
/**
 * Private constructor to prevent instantiation.
 */
public final class ZoneTable {
    private static final Map<Pair<Zone, Zone>, TransportLine[]> ZONE_PAIRS = new HashMap<>();
    private static final int MINUTES_IN_HOUR = 60;
    /**
     * This class holds the singleton instance of ZoneTable in a final field.
     */
    private ZoneTable() {
    }
    /**
     * Returns the singleton instance of ZoneTable.
     * @return the singleton instance of ZoneTable
     */
    private static final class Holder {
        private static final ZoneTable INSTANCE = new ZoneTable();
    }
    /**
     * Returns the singleton instance of ZoneTable.
     * @return the singleton instance of ZoneTable
     */
    public static ZoneTable getInstance() {
        return Holder.INSTANCE;
    }
    /**
     * Adds a pair of zones and transport lines to the zone table.
     * @param zone1 the first zone
     * @param zone2 the second zone
     * @param transportLine the transport lines connecting the two zones
     */
    public void addPair(final Zone zone1, final Zone zone2, final TransportLine[] transportLine) {
        ZONE_PAIRS.put(new Pair<>(zone1, zone2), transportLine);
        ZONE_PAIRS.put(new Pair<>(zone2, zone1), transportLine); // to ensure the table works both ways
    }
    /**
     * Returns the transport lines connecting two zones.
     * @param zone1 the first zone
     * @param zone2 the second zone
     * @return the transport lines connecting the two zones
     */
    public TransportLine[] getTransportLine(final Zone zone1, final Zone zone2) {
        return ZONE_PAIRS.get(new Pair<Zone, Zone>(zone1, zone2));
    }
    /**
     * Returns the duration of a trip given the transport lines.
     * @param transportLines the transport lines
     * @return the duration of the trip
     */
    public int getTripDuration(final TransportLine[] transportLines) {
        return Arrays.stream(transportLines)
                             .mapToInt(TransportLine::getDuration)
                             .map(duration -> duration * MINUTES_IN_HOUR)
                             .sum();
    }
}

