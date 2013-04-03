package ro.enoor.cube3d.level.chunk;

import ro.enoor.cube3d.util.TextureManager;
import ro.enoor.cube3d.world.rendering.Camera;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class ChunkManager {
    private static ChunkManager instance = new ChunkManager();

    public static ChunkManager getInstance() {
        return instance;
    }

    public ChunkRenderer chunkRenderer = new ChunkRenderer();
    public ChunkUpdater chunkUpdater = new ChunkUpdater();

    public List<Chunk> loadedChunks = new ArrayList<Chunk>();
    public List<Chunk> visibleChunks = new ArrayList<Chunk>();

    public void generateWorld() {
        for (int x = 0; x < Camera.VIEW_RADIUS * 2; x++)
            for (int y = 0; y < 1; y++)
                for (int z = 0; z < Camera.VIEW_RADIUS * 2; z++)
                    addChunk(x, y, z);
    }

    /**
     * Generates VBOs for new visible chunks and removes the chunks that are not visible.
     */
    public void updateChunks() {
        for (Chunk chunk : loadedChunks) {
            int chunkIndex = visibleChunks.indexOf(chunk);
            if (Camera.getInstance().frustum.cubeInFrustum(chunk.offsetX, chunk.offsetY, chunk.offsetZ, Chunk.SIZE)) {
                if (chunkIndex == -1) {
                    visibleChunks.add(chunk);
                }
            } else {
                if (chunkIndex != -1) {
                    visibleChunks.remove(chunkIndex);
                }
            }
        }
    }

    public void renderChunks() {
        glBindTexture(GL_TEXTURE_2D, TextureManager.textureAtlasID);
        for (Chunk chunk : visibleChunks)
            chunkRenderer.render(chunk);
    }

    public void addChunk(int x, int y, int z) {
        Chunk chunk = new Chunk(x, y, z);
        loadedChunks.add(chunk);
        chunkUpdater.generateVBO(chunk);
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