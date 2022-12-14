package advent.day14;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advent.common.util.Coord2D;
import advent.common.util.InputReader;

public class RegolithReservoir {

    private static class CaveMap {
        private Set<Coord2D> rocks = new HashSet<>();
        private Set<Coord2D> sand = new HashSet<>();

        private Coord2D lowBound = new Coord2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
        private Coord2D highBound = new Coord2D(Integer.MIN_VALUE, Integer.MIN_VALUE);

        public void addRock(Coord2D rock) {
            rocks.add(rock);
            updateBounds(rock);
        }

        public void addRocks(String input) {
            String[] nodes = input.split("->");
            for (int i=0; i<nodes.length-1; i++) {
                int x1 = Integer.parseInt(nodes[i].split(",")[0].trim());
                int y1 = Integer.parseInt(nodes[i].split(",")[1].trim());
                int x2 = Integer.parseInt(nodes[i+1].split(",")[0].trim());
                int y2 = Integer.parseInt(nodes[i+1].split(",")[1].trim());

                if (x1==x2) {
                    int startY = Math.min(y1, y2);
                    int endY = Math.max(y1, y2);
                    for (int y=startY; y<=endY; y++) {
                        addRock(new Coord2D(x1, y));
                    }
                } else {
                    int startX = Math.min(x1, x2);
                    int endX = Math.max(x1, x2);
                    for (int x=startX; x<=endX; x++) {
                        addRock(new Coord2D(x, y1));
                    }
                }
            }
        }

        public boolean spaceIsFree(Coord2D pos) {
            return !rocks.contains(pos) && !sand.contains(pos);
        }

        public Coord2D addSand(Coord2D origin) {
            
            Coord2D sandPos = origin;

            while (sandPos.y <= highBound.y) {
                Coord2D nextPos = new Coord2D(sandPos.x, sandPos.y);
                nextPos.y++;
                if (spaceIsFree(nextPos)) {
                    sandPos = nextPos;
                    continue;
                }
                nextPos.x = nextPos.x - 1;
                if (spaceIsFree(nextPos)) {
                    sandPos = nextPos;
                    continue;
                }
                nextPos.x = nextPos.x + 2;
                if (spaceIsFree(nextPos)) {
                    sandPos = nextPos;
                    continue;
                }

                sand.add(sandPos);
                updateBounds(sandPos);
                return sandPos;
            }
            
            return null;
        }

        public void updateBounds(Coord2D newItem) {
            if (newItem.x < lowBound.x) {
                lowBound = new Coord2D(newItem.x, lowBound.y);
            }
            if (newItem.x > highBound.x) {
                highBound = new Coord2D(newItem.x, highBound.y);
            }
            if (newItem.y < lowBound.y) {
                lowBound = new Coord2D(lowBound.x, newItem.y);
            }
            if (newItem.y > highBound.y) {
                highBound = new Coord2D(highBound.x, newItem.y);
            }
        }
    }

    public static void main(String[] args) {
       
        CaveMap caveMap = new CaveMap();
        List<String> inputs = InputReader.readLinesFromInput("advent/day14/input.txt");
        
        for (String line : inputs) {
            caveMap.addRocks(line);
        }

        Coord2D sandOrigin = new Coord2D(500, 0);
        int sandCount = 0;
        while (true) {
            if (caveMap.addSand(sandOrigin) == null) {
                break;
            }
            sandCount++;
        }

        System.out.println(sandCount);
    }
}