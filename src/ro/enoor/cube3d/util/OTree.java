package ro.enoor.cube3d.util;

public class OTree {
    public int x, y, z, size;
    public OTree[] children;

    public OTree(int x, int y, int z, int size) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public boolean breakTree() {
        if (size / 2 < 1) return false;

        children = new OTree[8];

        // Top part
        children[0] = new OTree(x, y, z, size / 2);
        children[1] = new OTree(x + size / 2, y, z, size / 2);
        children[2] = new OTree(x + size / 2, y, z + size / 2, size / 2);
        children[3] = new OTree(x, y, z + size / 2, size / 2);

        // Bottom part
        children[4] = new OTree(x, y + size / 2, z, size / 2);
        children[5] = new OTree(x + size / 2, y + size / 2, z, size / 2);
        children[6] = new OTree(x + size / 2, y + size / 2, z + size / 2, size / 2);
        children[7] = new OTree(x, y + size / 2, z + size / 2, size / 2);

        return true;
    }
}
