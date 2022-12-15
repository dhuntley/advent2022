package advent.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advent.common.util.Coord2D;
import advent.common.util.InputReader;

public class BeaconExclusionZone {

    private static class Sensor {
        private Coord2D position;
        private Coord2D closestBeacon;
        private int radius;

        public Sensor(Coord2D pos, Coord2D beacon) {
            this.position = pos;
            this.closestBeacon = beacon;
            this.radius = Math.abs(position.x - closestBeacon.x) + Math.abs(position.y - closestBeacon.y);
        }

        public Range getBlockedXRange(int rowOfInterest) {
            int xDist = radius - Math.abs(rowOfInterest - position.y);
            return xDist > 0 ? new Range(position.x - xDist, position.x + xDist) : null;
        }
    }

    private static class Range implements Comparable<Range> {
        int min;
        int max;

        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public boolean intersects(Range other) {
            return !(this.min > other.max || this.max < other.min);
        }

        // Merge two intersecting ranges. Return null if they don't intersect.
        public Range merge(Range other) {
            if (!intersects(other)) {
                return null;
            }
            return new Range(Math.min(this.min, other.min), Math.max(this.max, other.max));
        }

        @Override
        public int compareTo(Range other) {
            return this.min - other.min;
        }
    }

    public static void main(String[] args) {
       
        List<Sensor> sensors = new ArrayList<>();
        Set<Coord2D> beacons = new HashSet<>();

        List<String> inputs = InputReader.readLinesFromInput("advent/day15/input.txt");
        for (String line : inputs) {
            String[] tokens = line.split(" ");
            int x1 = Integer.parseInt(tokens[2].substring(2, tokens[2].length() - 1));
            int y1 = Integer.parseInt(tokens[3].substring(2, tokens[3].length() - 1));
            int x2 = Integer.parseInt(tokens[8].substring(2, tokens[8].length() - 1));
            int y2 = Integer.parseInt(tokens[9].substring(2));
            sensors.add(new Sensor(new Coord2D(x1, y1), new Coord2D(x2, y2)));
            beacons.add(new Coord2D(x2, y2));
        }

        int rowOfInterest = 2000000;

        List<Range> blockedRanges = new ArrayList<>();

        for (Sensor sensor : sensors) {
            Range blockedRange = sensor.getBlockedXRange(rowOfInterest);
            if (blockedRange != null) {
                blockedRanges.add(blockedRange);
            }
        }

        blockedRanges.sort(Range::compareTo);
        
        List<Range> mergedRanges = new ArrayList<>();
        mergedRanges.add(blockedRanges.get(0));
        
        for (int index = 1; index < blockedRanges.size(); index++) {
            Range mergedRange = mergedRanges.get(mergedRanges.size() - 1).merge(blockedRanges.get(index));
            if (mergedRange == null) {
                // No intersection
                mergedRanges.add(blockedRanges.get(index));
            } else {
                mergedRanges.remove(mergedRanges.size() - 1);
                mergedRanges.add(mergedRange);
            }
        }
        
        int blockedCount = 0;
        for (Range range : mergedRanges) {
            blockedCount += range.max - range.min + 1;
            
            // Reduce the count by any known beacons in the merged ranges.
            for (Coord2D beacon : beacons) {
                if (beacon.y == rowOfInterest && beacon.x >= range.min && beacon.x <= range.max) {
                    blockedCount--;
                }
            }
        }

        System.out.println(blockedCount);

    }
}