package ro.enoor.cube3d.world;

import ro.enoor.cube3d.level.chunk.ChunkManager;
import ro.enoor.cube3d.world.rendering.Camera;

public class World {
    public ChunkManager chunkManager = ChunkManager.getInstance();

    public World() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                chunkManager.addChunk(i, j);
        chunkManager.updateChunks();
    }

    public void update(int delta) {
        if (Camera.getInstance().moved) {
            chunkManager.updateChunks();
            Camera.getInstance().moved = false;
        }
    }
}
