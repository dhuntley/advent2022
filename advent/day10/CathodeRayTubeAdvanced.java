package advent.day10;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import advent.common.util.InputReader;

public class CathodeRayTubeAdvanced {

    public static final String ADDX = "addx";
    public static final String NOOP = "noop";

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

        int spritePos = 1;
        int cycle = 1;

        while (!summands.isEmpty()) {

            int currentPixel = (cycle - 1) % 40;
            System.out.print(Math.abs(currentPixel - spritePos) < 2 ? "#" : ".");

            if (cycle % 40 == 0) {
                System.out.println();
            }

            String operation = summands.poll();
            if (!NOOP.equals(operation)) {
                spritePos += Integer.parseInt(operation);
            }
            cycle++;
        }
    }
}