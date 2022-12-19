package advent.common.util;

import java.util.Objects;

public class Coord3D {
    public int x;
    public int y;
    public int z;

    public Coord3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord3D)) {
            return false;
        } else {
            Coord3D other = (Coord3D) o;
            return x == other.x && y == other.y && z == other.z;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y,z);
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}