package advent.day05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import advent.common.util.InputReader;

public class SupplyStacks {

    private static final int NUM_STACKS = 9;

    public static void main(String[] args) {
        
        Stack<Character> tempStack = new Stack<>();

        List<Stack<Character>> crates = new ArrayList<>();
        for (int i = 0; i < NUM_STACKS; i++) {
            crates.add(new Stack<>());
        }

        List<String> crateInput = InputReader.readLinesFromInput("advent/day05/crates.txt");
        Collections.reverse(crateInput);
        for (String line : crateInput) {
            for (int i=0; i<NUM_STACKS; i++) {
                Character c = line.charAt(4 * i + 1);
                if (!Character.isWhitespace(c)) {
                    crates.get(i).push(c);
                }
            }
        }

        for (String line : InputReader.readLinesFromInput("advent/day05/instructions.txt")) {
            int numMoves = Integer.parseInt(line.split(" ")[1]);
            int srcIndex = Integer.parseInt(line.split(" ")[3]) - 1;
            int destIndex = Integer.parseInt(line.split(" ")[5]) - 1;

            /*for (int i=0; i<numMoves; i++) {
                crates.get(destIndex).push(crates.get(srcIndex).pop());
            }*/

            for (int i=0; i<numMoves; i++) {
                tempStack.push(crates.get(srcIndex).pop());
            }

            for (int i=0; i<numMoves; i++) {
                crates.get(destIndex).push(tempStack.pop());
            }
        }

        for (Stack<Character> pile : crates) {
            System.out.print(pile.peek());
        }
        System.out.println();
    }
}