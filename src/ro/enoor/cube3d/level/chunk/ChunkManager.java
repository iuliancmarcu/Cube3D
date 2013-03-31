package ro.enoor.cube3d.level.chunk;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {
    private static ChunkManager instance = new ChunkManager();

    public static ChunkManager getInstance() {
        return instance;
    }

    public List<Chunk> loadedChunks = new ArrayList<Chunk>();

    public void renderChunks() {
        for (Chunk chunk : loadedChunks)
            ChunkRenderer.render(chunk);
    }

    public void addChunk(int x, int y, int z) {
        loadedChunks.add(new Chunk(x, y, z));
    }

    public void updateChunks() {
        for (Chunk chunk : loadedChunks)
            ChunkRenderer.generateVBO(chunk);
    }

    public int getBlockInWorld(int x, int y, int z) {
        int chunkX = x / Chunk.SIZE;
        int chunkY = y / Chunk.SIZE;
        int chunkZ = z / Chunk.SIZE;
        x %= Chunk.SIZE;
        y %= Chunk.SIZE;
        z %= Chunk.SIZE;

        return getBlockInChunk(x, y, z, chunkX, chunkY, chunkZ);
    }

    public int getBlockInChunk(int x, int y, int z, int chunkX, int chunkY, int chunkZ) {
        Chunk searchedChunk = null;
        for (Chunk chunk : loadedChunks) {
            if (chunk.chunkX == chunkX && chunk.chunkY == chunkY && chunk.chunkZ == chunkZ) {
                searchedChunk = chunk;
                break;
            }
        }

        if (searchedChunk != null)
            return searchedChunk.getBlock(x, y, z);
        return 0;
    }
}
