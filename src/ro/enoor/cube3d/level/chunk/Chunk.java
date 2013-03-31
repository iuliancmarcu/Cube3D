package ro.enoor.cube3d.level.chunk;

import ro.enoor.cube3d.level.block.BlockType;

public class Chunk {
    public static final int SIZE = 16;
    public byte[][][] blocks;

    public int chunkX, chunkY, chunkZ;
    public int offsetX, offsetY, offsetZ;

    public int vboPositionHandle;
    public int vboColorHandle;
    public int vertexCount;

    /**
     * @param x Chunk's offsetX.
     * @param y Chunk's offsetY.
     * @param z Chunk's offsetZ.
     */
    public Chunk(int x, int y, int z) {
        this.chunkX = x;
        this.chunkY = y;
        this.chunkZ = z;
        offsetX = x * SIZE;
        offsetY = y * SIZE;
        offsetZ = z * SIZE;

        blocks = new byte[SIZE][SIZE][SIZE];

        generate();
    }

    private void generate() {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                for (int z = 0; z < SIZE; z++) {
                    if (x % 2 == 0 ^ y % 2 == 0 ^ z % 2 == 0) {
                        blocks[x][y][z] = BlockType.WOOD.id;
                    }
                }
    }

    public int getBlock(int x, int y, int z) {
        if (x < 0) return ChunkManager.getInstance().getBlockInChunk(x + SIZE, y, z, chunkX - 1, chunkY, chunkZ);
        else if (x >= SIZE)
            return ChunkManager.getInstance().getBlockInChunk(x - SIZE, y, z, chunkX + 1, chunkY, chunkZ);
        else if (z < 0) return ChunkManager.getInstance().getBlockInChunk(x, y, z + SIZE, chunkX, chunkY, chunkZ - 1);
        else if (z >= SIZE)
            return ChunkManager.getInstance().getBlockInChunk(x, y, z - SIZE, chunkX, chunkY, chunkZ + 1);
        else if (y < 0) return ChunkManager.getInstance().getBlockInChunk(x, y + SIZE, z, chunkX, chunkY - 1, chunkZ);
        else if (y >= SIZE)
            return ChunkManager.getInstance().getBlockInChunk(x, y - SIZE, z, chunkX, chunkY + 1, chunkZ);

        return blocks[x][y][z];
    }
}
