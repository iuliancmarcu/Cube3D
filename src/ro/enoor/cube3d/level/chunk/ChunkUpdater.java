package ro.enoor.cube3d.level.chunk;

import org.lwjgl.BufferUtils;
import ro.enoor.cube3d.level.block.BlockType;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class ChunkUpdater {
    private static final int SIZE = Chunk.SIZE;

    public int vertexCount = 0;
    public Chunk usedChunk;
    public FloatBuffer positionBuffer;
    public FloatBuffer textureBuffer;

    /**
     * Set the chunk's VBO buffers after calculating every cube's faces.
     *
     * @param chunk Chunk that should be calculated.
     */
    public void generateVBO(Chunk chunk) {
//        System.out.println("Chunk at x: " + chunk.offsetX + " y: " + chunk.offsetY + " z: " + chunk.offsetZ + " has been updated.");

        vertexCount = 0;
        usedChunk = chunk;

        positionBuffer = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 6 * 3 / 2);
        textureBuffer = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 6 * 2 / 2);
        generateChunkVBO(chunk);
        positionBuffer.flip();
        textureBuffer.flip();

        chunk.vertexCount = vertexCount;

        glDeleteBuffers(chunk.vboPositionHandle);
        glDeleteBuffers(chunk.vboTextureHandle);

        chunk.vboPositionHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboPositionHandle);
        glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        chunk.vboTextureHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        usedChunk = null;
        positionBuffer = null;
        textureBuffer = null;
    }

    private void generateChunkVBO(Chunk chunk) {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                for (int z = 0; z < SIZE; z++)
                    if (chunk.getBlock(x, y, z) != BlockType.AIR.id)
                        generateBlockVBO(x + chunk.offsetX, y + chunk.offsetY, z + chunk.offsetZ);
    }

    /**
     * @param x Block's X in WORLD coordinates.
     * @param y Block's Y in WORLD coordinates.
     * @param z Block's Z in WORLD coordinates.
     */
    private void generateBlockVBO(int x, int y, int z) {
        ChunkManager manager = ChunkManager.getInstance();

        int blockID = manager.getBlockInWorld(x, y, z);
        BlockType blockType = BlockType.AIR;

        for (BlockType block : BlockType.values())
            if (block.id == blockID) {
                blockType = block;
                break;
            }

        // FRONT face
        if (manager.getBlockInWorld(x, y, z + 1) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{
                    x, y, z + 1f,
                    x + 1f, y, z + 1f,
                    x + 1f, y + 1f, z + 1f,

                    x, y, z + 1f,
                    x + 1f, y + 1f, z + 1f,
                    x, y + 1f, z + 1f
            });
            textureBuffer.put(blockType.texCoords);
            vertexCount += 6;
        }

        // BACK face
        if (manager.getBlockInWorld(x, y, z - 1) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{
                    x + 1f, y, z,
                    x, y, z,
                    x, y + 1f, z,

                    x + 1f, y, z,
                    x, y + 1f, z,
                    x + 1f, y + 1f, z
            });
            textureBuffer.put(blockType.texCoords);
            vertexCount += 6;
        }

        // LEFT face
        if (manager.getBlockInWorld(x - 1, y, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{
                    x, y, z,
                    x, y, z + 1f,
                    x, y + 1f, z + 1f,

                    x, y, z,
                    x, y + 1f, z + 1f,
                    x, y + 1f, z
            });
            textureBuffer.put(blockType.texCoords);
            vertexCount += 6;
        }

        // RIGHT face
        if (manager.getBlockInWorld(x + 1, y, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{
                    x + 1f, y, z + 1f,
                    x + 1f, y, z,
                    x + 1f, y + 1f, z,

                    x + 1f, y, z + 1f,
                    x + 1f, y + 1f, z,
                    x + 1f, y + 1f, z + 1f
            });
            textureBuffer.put(blockType.texCoords);
            vertexCount += 6;
        }

        // TOP face
        if (manager.getBlockInWorld(x, y + 1, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{
                    x, y + 1f, z + 1f,
                    x + 1f, y + 1f, z + 1f,
                    x + 1f, y + 1f, z,

                    x, y + 1f, z + 1f,
                    x + 1f, y + 1f, z,
                    x, y + 1f, z
            });
            textureBuffer.put(blockType.texCoords);
            vertexCount += 6;
        }

        // BOTTOM face
        if (manager.getBlockInWorld(x, y - 1, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{
                    x, y, z,
                    x + 1f, y, z,
                    x + 1f, y, z + 1f,

                    x, y, z,
                    x + 1f, y, z + 1f,
                    x, y, z + 1f
            });
            textureBuffer.put(blockType.texCoords);
            vertexCount += 6;
        }
    }
}
