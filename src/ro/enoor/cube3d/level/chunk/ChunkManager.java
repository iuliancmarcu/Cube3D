package ro.enoor.cube3d.level.chunk;

import ro.enoor.cube3d.world.rendering.Camera;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class ChunkManager {
    private static ChunkManager instance = new ChunkManager();

    public static ChunkManager getInstance() {
        return instance;
    }

    public ChunkRenderer chunkRenderer = new ChunkRenderer();
    public ChunkUpdater chunkUpdater = new ChunkUpdater();

    public List<Chunk> loadedChunks = new ArrayList<Chunk>();
    public List<Chunk> visibleChunks = new ArrayList<Chunk>();

    /**
     * Generates VBOs for new visible chunks and removes the chunks that are not visible.
     */
    public void updateChunks() {
        for (Chunk chunk : loadedChunks) {
            int chunkIndex = visibleChunks.indexOf(chunk);
            if (Camera.getInstance().frustum.cubeInFrustum(chunk.offsetX, chunk.offsetY, chunk.offsetZ, Chunk.SIZE)) {
                if (chunkIndex == -1) {
                    chunkUpdater.generateVBO(chunk);
                    visibleChunks.add(chunk);
                }
            } else {
                if (chunkIndex != -1) {
                    glDeleteBuffers(chunk.vboPositionHandle);
                    glDeleteBuffers(chunk.vboTexCoordHandle);
                    chunk.vboPositionHandle = 0;
                    chunk.vboTexCoordHandle = 0;
                    visibleChunks.remove(chunkIndex);
                }
            }
        }
    }

    public void renderChunks() {
        for (Chunk chunk : visibleChunks)
            chunkRenderer.render(chunk);
    }

    public void addChunk(int x, int y, int z) {
        loadedChunks.add(new Chunk(x, y, z));
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
