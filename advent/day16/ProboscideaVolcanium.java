package advent.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advent.common.util.InputReader;

public class ProboscideaVolcanium {

    private static class TunnelMap {
        private Map<String, Set<String>> tunnels = new HashMap<>();

        private Map<String, Map<String, Integer>> distances = null;

        private Set<String> valves = new HashSet<>();

        Map<String, Integer> flowRates = new HashMap<>();

        public void addTunnel(String a, String b) {
            if (!tunnels.containsKey(a)) {
                tunnels.put(a, new HashSet<>());
            }
            tunnels.get(a).add(b);
            valves.add(a);
            valves.add(b);
        }

        public void addFlowRate(String valve, int flowRate) {
            flowRates.put(valve, flowRate);
        }

        public Set<String> getNeighbours(String a) {
            return tunnels.containsKey(a) ? tunnels.get(a) : Set.of();
        }

        public int getFlowRate(String valve) {
            return flowRates.containsKey(valve) ? flowRates.get(valve) : 0;
        }

        public Set<String> getGoodValves() {
            return flowRates.keySet();
        }

        // Compute shortest pair-wise paths between all nodes (Floyd-Warshall)
        // (Only really need non-0 nodes and start)
        public void initDistances() {
            distances = new HashMap<>();
            for (String valve : valves) {
                Map<String, Integer> myDistances = new HashMap<>();
                myDistances.put(valve, 0);
                for (String neighbourValve : getNeighbours(valve)) {
                    myDistances.put(neighbourValve, 1);
                }
                distances.put(valve, myDistances);
            }

            for (String k : valves) {
                for (String i : valves) {
                    for (String j : valves) {
                        if (!distances.get(i).containsKey(k) || !distances.get(k).containsKey(j)) {
                            continue;
                        }
                        if (!distances.get(i).containsKey(j) || distances.get(i).get(j) > distances.get(i).get(k) + distances.get(k).get(j)) {
                            distances.get(i).put(j, distances.get(i).get(k) + distances.get(k).get(j));
                        }
                    }
                }
            }
        }

        public int getDistance(String a, String b) {
            return distances.get(a).get(b);
        }
    }

    private static int getMaxScore(String currentValve, Set<String> remainingValves, int remainingTime, TunnelMap tunnelMap) {
        if (remainingValves.isEmpty()) {
            return 0;
        }

        int maxScore = 0;

        Set<String> nextRemainingValves = new HashSet<>();
        nextRemainingValves.addAll(remainingValves);

        for (String nextValve : remainingValves) {
            nextRemainingValves.remove(nextValve);
            
            int distance = tunnelMap.getDistance(currentValve, nextValve);
            int nextRemainingTime = remainingTime - distance - 1; // travel + valve opening time
            if (nextRemainingTime < 0) {
                nextRemainingValves.add(nextValve);
                continue;
            }

            int score = ((nextRemainingTime) * tunnelMap.getFlowRate(nextValve)) + getMaxScore(nextValve, nextRemainingValves, nextRemainingTime, tunnelMap);

            if (score > maxScore) {
                maxScore = score;
            }

            nextRemainingValves.add(nextValve);
        }

        return maxScore;
    }

    public static void main(String[] args) {
    
        TunnelMap tunnelMap = new TunnelMap();

        List<String> inputs = InputReader.readLinesFromInput("advent/day16/input.txt");
        for (String line : inputs) {
            String[] tokens = line.split(" ");
            int flowRate = Integer.parseInt(tokens[4].substring(5, tokens[4].length() - 1));
            if (flowRate > 0) {
                tunnelMap.addFlowRate(tokens[1], flowRate);
            }

            for (int index = 9; index < tokens.length; index++) {
                tunnelMap.addTunnel(tokens[1], tokens[index].replaceAll(",", ""));
            }
        }

        tunnelMap.initDistances();

        // Use path lengths to optimize order of opening valves
        String startValve = "AA";

        Set<String> goodValves = new HashSet<>();
        goodValves.addAll(tunnelMap.getGoodValves());
        int maxScore = getMaxScore(startValve, goodValves, 30, tunnelMap);

        System.out.println(maxScore);
    }
}