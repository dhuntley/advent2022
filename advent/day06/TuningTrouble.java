package advent.day06;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import advent.common.util.InputReader;

public class TuningTrouble {

    private static final int PACKET_HEADER_SIZE = 14;

    public static void main(String[] args) {
        
        String signal = InputReader.readLinesFromInput("advent/day06/input.txt").get(0);
        Set<Character> uniqueChars = new HashSet<>();

        for (int i=0; i<signal.length()-PACKET_HEADER_SIZE; i++) {
            String header = signal.substring(i, i+PACKET_HEADER_SIZE);
            uniqueChars.addAll(Arrays.asList(header.chars().mapToObj(c -> (char)c).toArray(Character[]::new)));
            if (uniqueChars.size() == PACKET_HEADER_SIZE) {
                System.out.println(header);
                System.out.println(i + PACKET_HEADER_SIZE);
                return;
            }
            uniqueChars.clear();
        }
    }
}