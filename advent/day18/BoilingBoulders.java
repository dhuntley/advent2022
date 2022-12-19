package advent.day18;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advent.common.util.Coord3D;
import advent.common.util.InputReader;

public class BoilingBoulders {

    private static Collection<Coord3D> getNeighbours(Coord3D voxel) {
        return Set.of(
            new Coord3D(voxel.x - 1, voxel.y, voxel.z),
            new Coord3D(voxel.x + 1, voxel.y, voxel.z),
            new Coord3D(voxel.x, voxel.y - 1, voxel.z),
            new Coord3D(voxel.x, voxel.y + 1, voxel.z),
            new Coord3D(voxel.x, voxel.y, voxel.z - 1),
            new Coord3D(voxel.x, voxel.y, voxel.z + 1)
        );
    }

    public static void main(String[] args) {
    
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int zMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;
        int zMax = Integer.MIN_VALUE;

        Set<Coord3D> voxels = new HashSet<>();
        List<String> inputs = InputReader.readLinesFromInput("advent/day18/input.txt");
        for (String line : inputs) {
            String[] tokens = line.split(",");
            int x = Integer.parseInt(tokens[0]);
            int y = Integer.parseInt(tokens[1]);
            int z = Integer.parseInt(tokens[2]);
            voxels.add(new Coord3D(x, y, z));

            xMin = Math.min(xMin, x);
            yMin = Math.min(yMin, y);
            zMin = Math.min(zMin, z);

            xMax = Math.max(xMax, x);
            yMax = Math.max(yMax, y);
            zMax = Math.max(zMax, z);
        }

        xMax += 2;
        yMax += 2;
        zMax += 2;

        boolean[][][] floodMap = new boolean[xMax][yMax][zMax];

        for (Coord3D voxel : voxels) {
            floodMap[voxel.x][voxel.y][voxel.z] = true;
        }

        Deque<Coord3D> openfloodPoints = new ArrayDeque<>();
        openfloodPoints.add(new Coord3D(0, 0, 0));

        while (!openfloodPoints.isEmpty()) {
            Coord3D floodPoint = openfloodPoints.poll();
            for (Coord3D voxel : getNeighbours(floodPoint)) {
                if (voxel.x >= 0 && voxel.x < xMax &&
                    voxel.y >= 0 && voxel.y < yMax &&
                    voxel.z >= 0 && voxel.z < zMax && 
                    !floodMap[voxel.x][voxel.y][voxel.z]
                ) {
                    floodMap[voxel.x][voxel.y][voxel.z] = true;
                    openfloodPoints.add(voxel);      
                }
            }
        }

        for (int x=0; x < xMax; x++) {
            for (int y=0; y < yMax; y++) {
                for (int z=0; z < zMax; z++) {
                    if (!floodMap[x][y][z]) {
                        voxels.add(new Coord3D(x, y, z));
                    }
                }
            }   
        }

        int exteriorSurfaceArea = 0;
        for (Coord3D voxel : voxels) {
            for (Coord3D neighbour : getNeighbours(voxel)) {
                if (!voxels.contains(neighbour)) {
                    exteriorSurfaceArea++;
                }
            }
        }

        System.out.println(exteriorSurfaceArea);
    }
}