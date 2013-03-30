package ro.enoor.cube3d.level.chunk;

import java.util.ArrayList;

public class ChunkManager {
    private static ChunkManager instance = new ChunkManager();

    public static ChunkManager getInstance() {
        return instance;
    }

    public int cubesDrawn = 0;

    public ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();

    public void renderChunks() {
        for(int i = 0; i < loadedChunks.size(); i++)
            ChunkRenderer.render(loadedChunks.get(i));
    }

    public void addChunk(int x, int z) {
        Chunk chunk = new Chunk(x, z);
        loadedChunks.add(chunk);
        cubesDrawn += chunk.blockCount;
    }

    public void updateChunks() {
        for(int i = 0; i < loadedChunks.size(); i++)
            ChunkRenderer.generateVBO(loadedChunks.get(i));
    }

    public int getBlockInChunk(int x, int y, int z, int chunkX, int chunkZ) {
        Chunk searchedChunk = null;
        for(int i = 0; i < loadedChunks.size(); i++) {
            Chunk chunk = loadedChunks.get(i);
            if(chunk.chunkX == chunkX && chunk.chunkZ == chunkZ) {
                searchedChunk = chunk;
                break;
            }
        }

        if(searchedChunk != null)
            return searchedChunk.getBlock(x, y, z);
        return 0;
    }
}
