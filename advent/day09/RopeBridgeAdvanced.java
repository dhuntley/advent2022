package advent.day09;

import java.util.HashSet;
import java.util.Set;

import advent.common.util.InputReader;
import advent.common.util.Coord2D;

public class RopeBridgeAdvanced {

    private static Coord2D moveHead(Coord2D head, String direction) {
        switch(direction) {
            case "L":
                return new Coord2D(head.x - 1, head.y);
            case "R":
                return new Coord2D(head.x + 1, head.y);
            case "U":
                return new Coord2D(head.x, head.y - 1);
            case "D":
                return new Coord2D(head.x, head.y + 1);
            default:
                return null;
        }
    }

    private static Coord2D moveTail(Coord2D head, Coord2D tail) {
        if (Math.abs(head.x - tail.x) < 2 && Math.abs(head.y - tail.y) < 2) {
            return tail;
        }

        if (head.x - tail.x == 2 && head.y == tail.y) {
            return new Coord2D(tail.x + 1, tail.y);
        } else if (tail.x - head.x == 2 && head.y == tail.y) {
            return new Coord2D(tail.x - 1, tail.y);
        } else if (head.y - tail.y == 2 && head.x == tail.x) {
            return new Coord2D(tail.x, tail.y + 1);
        } else if (tail.y - head.y == 2 && head.x == tail.x) {
            return new Coord2D(tail.x, tail.y - 1);
        }

        if (head.x > tail.x) {
            if (head.y > tail.y) {
                return new Coord2D(tail.x + 1, tail.y + 1);
            } else {
                return new Coord2D(tail.x + 1, tail.y - 1);
            }
        } else {
            if (head.y > tail.y) {
                return new Coord2D(tail.x - 1, tail.y + 1);
            } else {
                return new Coord2D(tail.x - 1, tail.y - 1);
            }
        }
    }

    private static final int NUM_KNOTS = 10;

    public static void main(String[] args) {
        Set<Coord2D> tailPositions = new HashSet<>();
        Coord2D[] knots = new Coord2D[NUM_KNOTS];
        for (int i=0; i<NUM_KNOTS; i++) {
            knots[i] = new Coord2D(0, 0);
        };

        for (String line : InputReader.readLinesFromInput("advent/day09/input.txt")) {

            String[] tokens = line.split(" ");
            String direction = tokens[0];
            int distance = Integer.parseInt(tokens[1]);

            while (distance > 0) {
                knots[0] = moveHead(knots[0], direction);

                for (int i=0; i<NUM_KNOTS-1; i++) {
                    knots[i+1] = moveTail(knots[i], knots[i+1]);
                }
                tailPositions.add(knots[NUM_KNOTS - 1]);
                distance--;

                /*System.out.println("MOVE " + direction);
                System.out.println("HEAD: " + head);
                System.out.println("TAIL: " + tail);*/
            }
        }

        /*for (int i=-10; i<=10; i++) {
            for (int j=-10; j<=10; j++) {
                if (tailPositions.contains(new Coord2D(i, j))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            } 
            System.out.println();
        }
        System.out.println();*/

        System.out.println(tailPositions.size());
    }
}