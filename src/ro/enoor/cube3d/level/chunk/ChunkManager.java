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

    public void addChunk(int x, int z) {
        Chunk chunk = new Chunk(x, z);
        loadedChunks.add(chunk);
    }

    public void updateChunks() {
        for (Chunk chunk : loadedChunks)
            ChunkRenderer.generateVBO(chunk);
    }

    public int getBlockInChunk(int x, int y, int z, int chunkX, int chunkZ) {
        Chunk searchedChunk = null;
        for (Chunk chunk : loadedChunks) {
            if (chunk.chunkX == chunkX && chunk.chunkZ == chunkZ) {
                searchedChunk = chunk;
                break;
            }
        }

        if (searchedChunk != null)
            return searchedChunk.getBlock(x, y, z);
        return 0;
    }
}
