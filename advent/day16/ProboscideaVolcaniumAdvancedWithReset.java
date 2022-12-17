package advent.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import advent.common.util.InputReader;

public class ProboscideaVolcaniumAdvancedWithReset {

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

    // Sim end-to-end twice with a reset to position AA and a reset of time?

    private static final int TOTAL_TIME = 26;
    private static final String START_VALVE = "AA";

    private static int getMaxScore(String currentValve, Set<String> remainingValves, int remainingTime, boolean resetAvailable, Map<String, Integer> maxScoreCache, TunnelMap tunnelMap) {
        
        String hashKey1 = currentValve + remainingValves.toString() + remainingTime + resetAvailable;
        
        if (maxScoreCache.containsKey(hashKey1)) {
            return maxScoreCache.get(hashKey1);
        }

        if (remainingValves.isEmpty() || (remainingTime < 0)) {
            return 0;
        }

        int maxScore = 0;

        Set<String> nextRemainingValves = new HashSet<>();
        nextRemainingValves.addAll(remainingValves);

        for (String nextValue : remainingValves) {
            nextRemainingValves.remove(nextValue);

            if (remainingTime > 0) {
                int myDistance = tunnelMap.getDistance(currentValve, nextValue);
                int myNextRemainingTime = remainingTime - myDistance - 1; // travel + valve opening time
                if (myNextRemainingTime >= 0) {
                    int score = myNextRemainingTime * tunnelMap.getFlowRate(nextValue);
                    score += getMaxScore(nextValue, nextRemainingValves, myNextRemainingTime, resetAvailable, maxScoreCache, tunnelMap);
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }

            nextRemainingValves.add(nextValue);
        }

        // OR, reset to start position once if allowed
        if (resetAvailable) {
            int score =  getMaxScore(START_VALVE, remainingValves, TOTAL_TIME, false, maxScoreCache, tunnelMap);
            if (score > maxScore) {
                maxScore = score;
            }
        }

        maxScoreCache.put(hashKey1, maxScore);

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
        Set<String> goodValves = new HashSet<>();
        Map<String, Integer> maxScoreCache = new HashMap<>();

        goodValves.addAll(tunnelMap.getGoodValves());
        int maxScore = getMaxScore(START_VALVE, goodValves, TOTAL_TIME, true, maxScoreCache, tunnelMap);
        
        System.out.println(maxScore);
    }
}