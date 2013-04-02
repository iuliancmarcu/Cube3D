package ro.enoor.cube3d.world;

import ro.enoor.cube3d.level.chunk.Chunk;
import ro.enoor.cube3d.level.chunk.ChunkManager;

public class World {
    public ChunkManager manager = ChunkManager.getInstance();

    public World() {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 8; z++)
                    manager.addChunk(x, y, z);
        for (Chunk chunk : manager.loadedChunks) {
            manager.chunkUpdater.generateVBO(chunk);
        }
    }

    public void update(int delta) {
        manager.updateChunks();
    }
}
