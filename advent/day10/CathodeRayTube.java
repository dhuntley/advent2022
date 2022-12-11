package advent.day10;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import advent.common.util.InputReader;

public class CathodeRayTube {

    public static final String ADDX = "addx";
    public static final String NOOP = "noop";
    public static final Integer[] MILESTONE_CYCLE_ARRAY = {20, 60, 100, 140, 180, 220};
    public static final List<Integer> MILESTONE_CYCLES = Arrays.asList(MILESTONE_CYCLE_ARRAY);

    public static void main(String[] args) {
        Deque<String> summands = new ArrayDeque<>();
        List<String> inputs = InputReader.readLinesFromInput("advent/day10/input.txt");

        for (String line : inputs) {
            String[] tokens = line.split(" ");

            summands.add(NOOP);
            if (tokens[0].equals(ADDX)) {
                summands.add(tokens[1]);
            }
        }

        int signal = 1;
        int cycle = 1;
        int signalSum = 0;

        while (!summands.isEmpty()) {

            if (MILESTONE_CYCLES.indexOf(cycle) != -1) {
                //System.out.println(cycle + ": " + signal);
                signalSum += signal * cycle;
            }

            String operation = summands.poll();
            if (!NOOP.equals(operation)) {
                signal += Integer.parseInt(operation);
            }
            cycle++;
        }

        System.out.println(signalSum);
    }
}