package advent.day18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advent.common.util.Coord3D;
import advent.common.util.InputReader;

public class BoilingBouldersSimple {

    public static void main(String[] args) {
    
        Set<Coord3D> voxels = new HashSet<>();

        List<String> inputs = InputReader.readLinesFromInput("advent/day18/input.txt");
        for (String line : inputs) {
            String[] tokens = line.split(",");
            voxels.add(new Coord3D(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
        }

        int surfaceArea = 0;
        for (Coord3D voxel : voxels) {
            if (!voxels.contains(new Coord3D(voxel.x-1, voxel.y, voxel.z))) {
                surfaceArea++;
            }
            if (!voxels.contains(new Coord3D(voxel.x+1, voxel.y, voxel.z))) {
                surfaceArea++;
            }
            if (!voxels.contains(new Coord3D(voxel.x, voxel.y-1, voxel.z))) {
                surfaceArea++;
            }
            if (!voxels.contains(new Coord3D(voxel.x, voxel.y+1, voxel.z))) {
                surfaceArea++;
            }
            if (!voxels.contains(new Coord3D(voxel.x, voxel.y, voxel.z-1))) {
                surfaceArea++;
            }
            if (!voxels.contains(new Coord3D(voxel.x, voxel.y, voxel.z+1))) {
                surfaceArea++;
            }
        }

        System.out.println(surfaceArea);
    }
}