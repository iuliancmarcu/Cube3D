package ro.enoor.cube3d.world;

import ro.enoor.cube3d.level.chunk.ChunkManager;

public class World {
    public ChunkManager chunkManager = ChunkManager.getInstance();

    public World() {
        for (int i = 0; i < 1; i++)
            for (int j = 0; j < 1; j++)
                chunkManager.addChunk(i, 0, j);
        chunkManager.updateChunks();
    }

    public void update(int delta) {
        /*if (Camera.getInstance().moved) {
            chunkManager.updateChunks();
            Camera.getInstance().moved = false;
        }*/
    }
}
