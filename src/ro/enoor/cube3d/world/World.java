package ro.enoor.cube3d.world;

import ro.enoor.cube3d.level.chunk.ChunkManager;

public class World {
    public ChunkManager manager = ChunkManager.getInstance();

    public World() {
        manager.generateWorld();
//
//        for(int x = 0; x < VIEW_RADIUS * 2; x++)
//            for(int y = 0; y < 1; y++)
//                for(int z = 0; z < VIEW_RADIUS * 2; z++) {
//        for (Chunk chunk : manager.loadedChunks) {
//            manager.chunkUpdater.generateVBO(chunk);
//        }
    }

    public void update(int delta) {
        manager.updateChunks();
    }
}
