package advent.day12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import advent.common.util.Coord2D;
import advent.common.util.InputReader;

public class HillClimbing {

    private static boolean reachable(Coord2D src, Coord2D dest, char[][] heightMap) {
        return dest.x >= 0 && dest.x < heightMap.length && dest.y >= 0 && dest.y < heightMap[0].length && heightMap[src.x][src.y] >= heightMap[dest.x][dest.y] - 1;
    }

    private static List<Coord2D> getNeighbours(Coord2D pos, char[][] heightMap) {
        List<Coord2D> neighbours = Arrays.asList(
            new Coord2D(pos.x-1, pos.y),
            new Coord2D(pos.x+1, pos.y),
            new Coord2D(pos.x, pos.y-1),
            new Coord2D(pos.x, pos.y+1)
        );
        return neighbours.stream().filter((Coord2D neighbour) -> reachable(pos, neighbour, heightMap)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> inputs = InputReader.readLinesFromInput("advent/day12/input.txt");
        char[][] heightMap = new char[inputs.size()][inputs.get(0).length()];

        int x=0;
        int y=0;
        Coord2D start = null;
        Coord2D goal = null;

        for (String line : inputs) {
            for (char c : line.toCharArray()) {
                if (c == 'S') {
                    start = new Coord2D(x, y);
                    heightMap[x][y] = 'a';
                } else if (c == 'E') {
                    goal = new Coord2D(x, y);
                    heightMap[x][y] = 'z';
                } else {
                    heightMap[x][y] = c;
                }
                y++;
            }
            y=0;
            x++;
        }

        final int[][] distanceMap = new int[heightMap.length][heightMap[0].length];
        Set<Coord2D> openNodes = new HashSet<>();

        for (x=0; x<heightMap.length; x++) {
            for (y=0; y<heightMap[0].length; y++) {
                distanceMap[x][y] = Integer.MAX_VALUE;
                openNodes.add(new Coord2D(x, y));
            }   
        }

        distanceMap[start.x][start.y] = 0;

        while (!openNodes.isEmpty()) {
            Coord2D pos = null;
            int dist = Integer.MAX_VALUE;
            for (Coord2D coord : openNodes) {
                if (distanceMap[coord.x][coord.y] < dist) {
                    pos = coord;
                    dist = distanceMap[coord.x][coord.y];
                }
            }

            if (pos == null) {
                break;
            }
            openNodes.remove(pos);

            int newDist = dist + 1;
            for (Coord2D neighbour : getNeighbours(pos, heightMap)) {
                if (newDist < distanceMap[neighbour.x][neighbour.y]) {
                    distanceMap[neighbour.x][neighbour.y] = newDist;
                }
            }
        }

        System.out.println(distanceMap[goal.x][goal.y]);
        
    }
}