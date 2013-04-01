package ro.enoor.cube3d.world;

import ro.enoor.cube3d.level.chunk.ChunkManager;

public class World {
    public ChunkManager manager = ChunkManager.getInstance();

    public World() {
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                for (int z = 0; z < 10; z++)
                    manager.addChunk(x, y, z);
    }

    public void update(int delta) {
        manager.updateChunks();
    }
}
