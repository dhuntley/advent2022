package advent.day03;

import java.util.List;

import advent.common.util.InputReader;

public class RuckSackPackAdvanced {

    private static int getPriority(char c) {
        return 1 + c - (Character.isUpperCase(c) ? 'A' - 26 : 'a');
    }

    private static char getIntersection(String a, String b, String c) {
        for (char x : a.toCharArray()) {
            for (char y : b.toCharArray()) {
                for (char z : c.toCharArray()) {
                    if (x==y && y==z) {
                        return x;
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int prioritySum = 0;
        int index = 0;

        List<String> lines = InputReader.readLinesFromInput("advent/day03/input1.txt");

        while (index < lines.size()) {
            prioritySum += getPriority(getIntersection(lines.get(index), lines.get(index + 1), lines.get(index + 2)));
            index += 3;
        }

        System.out.println(prioritySum);
    }
}