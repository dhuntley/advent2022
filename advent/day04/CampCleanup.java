package advent.day04;

import advent.common.util.InputReader;

public class CampCleanup {

    public static void main(String[] args) {
        //int redundantElves = 0;
        int overlapCount = 0;
        for (String line : InputReader.readLinesFromInput("advent/day04/input1.txt")) {
           
            String elf1 = line.split(",")[0];
            String elf2 = line.split(",")[1];

            int elf1start = Integer.parseInt(elf1.split("-")[0]);
            int elf1end = Integer.parseInt(elf1.split("-")[1]);

            int elf2start = Integer.parseInt(elf2.split("-")[0]);
            int elf2end = Integer.parseInt(elf2.split("-")[1]);

            /*if (elf1start >= elf2start && elf1end <= elf2end || elf2start >= elf1start && elf2end <= elf1end) {
                redundantElves++;
            }*/

            if (!(elf1end < elf2start || elf2end < elf1start)) {
                overlapCount++;
            }
        }
        //System.out.println(redundantElves);
        System.out.println(overlapCount);
    }
}