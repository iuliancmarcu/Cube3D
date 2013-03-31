package ro.enoor.cube3d.level.chunk;

import ro.enoor.cube3d.level.block.BlockType;
import ro.enoor.cube3d.level.octree.OTree;

import java.util.Random;

public class Chunk {
    public static final int SIZE = 16;
    public static Random random = new Random(0);
    public byte[][][] blocks;

    public int chunkX, chunkZ;
    public int offsetX, offsetZ;

    public int vboPositionHandle;
    public int vboColorHandle;
    public int vertexCount;

    public OTree tree;

    public Chunk(int x, int z) {
        this.chunkX = x;
        this.chunkZ = z;
        offsetX = x * SIZE;
        offsetZ = z * SIZE;

        blocks = new byte[SIZE][SIZE][SIZE];

        tree = new OTree(offsetX, 0, offsetZ, SIZE);

        generate();
    }

    private void generate() {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                for (int z = 0; z < SIZE; z++) {
                    if (random.nextBoolean()) blocks[x][y][z] = BlockType.WOOD.id;
                    else blocks[x][y][z] = BlockType.AIR.id;
                }
    }

    public int getBlock(int x, int y, int z) {
        if (x < 0) return ChunkManager.getInstance().getBlockInChunk(x + SIZE, y, z, chunkX - 1, chunkZ);
        else if (x >= SIZE) return ChunkManager.getInstance().getBlockInChunk(x - SIZE, y, z, chunkX + 1, chunkZ);
        else if (z < 0) return ChunkManager.getInstance().getBlockInChunk(x, y, z + SIZE, chunkX, chunkZ - 1);
        else if (z >= SIZE) return ChunkManager.getInstance().getBlockInChunk(x, y, z - SIZE, chunkX, chunkZ + 1);
        else if (!(y < 0 || y >= SIZE)) return blocks[x][y][z];

        return 0;
    }
}
