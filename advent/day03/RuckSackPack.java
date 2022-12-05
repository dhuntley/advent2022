package advent.day03;

import advent.common.util.InputReader;

public class RuckSackPack {

    private static int getPriority(char c) {
        return 1 + c - (Character.isUpperCase(c) ? 'A' - 26 : 'a');
    }

    private static char getIntersection(String a, String b) {
        for (char x : a.toCharArray()) {
            for (char y : b.toCharArray()) {
                if (x==y) {
                    return x;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int prioritySum = 0;
        for (String line : InputReader.readLinesFromInput("advent/day03/input1.txt")) {
            int size = line.length() / 2;
            String compartment1 = line.substring(0, size);
            String compartment2 = line.substring(size, line.length());

            prioritySum += getPriority(getIntersection(compartment1, compartment2));
        }
        System.out.println(prioritySum);
    }
}