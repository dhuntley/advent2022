package advent.day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import advent.common.util.InputReader;

public class CalorieCounter2 {

    private static class Elf implements Comparable<Elf> {
        List<Integer> items = new ArrayList<>();

        public int getCalorieTotal() {
            return items.stream().mapToInt(Integer::intValue).sum();
        }

        @Override
        public int compareTo(Elf other) {
            return this.getCalorieTotal() - other.getCalorieTotal();
        }
    }

    public static void main(String[] args) {
        
        List<Elf> elves = new ArrayList<>();
        Elf currentElf = new Elf();
        elves.add(currentElf);
        
        for (Integer item : InputReader.readIntegersFromInput("advent/day01/input1.txt")) {
            if (item == null) {
                currentElf = new Elf();
                elves.add(currentElf);
            } else {
                currentElf.items.add(item);
            }
        }

        elves.sort(Elf::compareTo);
        Collections.reverse(elves);
        System.out.println(elves.get(0).getCalorieTotal() + elves.get(1).getCalorieTotal() + elves.get(2).getCalorieTotal());
    }
}