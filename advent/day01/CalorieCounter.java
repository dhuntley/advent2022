package advent.day01;

import java.util.ArrayList;
import java.util.List;

import advent.common.util.InputReader;

public class CalorieCounter {

    private static class Elf {
        List<Integer> items = new ArrayList<>();

        public int getCalorieTotal() {
            return items.stream().mapToInt(Integer::intValue).sum();
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

        Integer maxCalories = Integer.MIN_VALUE;
        for (Elf elf : elves) {
            if (elf.getCalorieTotal() > maxCalories) {
                maxCalories = elf.getCalorieTotal();
            }
        }
        
        System.out.println(maxCalories);
    }
}