package ro.enoor.cube3d.level.chunk;

import org.lwjgl.BufferUtils;
import ro.enoor.cube3d.level.block.BlockType;
import ro.enoor.cube3d.level.octree.OTree;
import ro.enoor.cube3d.world.rendering.Camera;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public abstract class ChunkRenderer {
    private static final int SIZE = Chunk.SIZE;

    static int vertexCount = 0;
    static Chunk usedChunk;
    static FloatBuffer positionBuffer;
    static FloatBuffer colorBuffer;

    public static void generateVBO(Chunk chunk) {
        vertexCount = 0;

        usedChunk = chunk;

        positionBuffer = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4);
        colorBuffer = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4);

        generateOctreeVBO(chunk.tree);

        positionBuffer.flip();
        colorBuffer.flip();

        chunk.vertexCount = vertexCount;

        glDeleteBuffers(chunk.vboPositionHandle);
        glDeleteBuffers(chunk.vboColorHandle);

        chunk.vboPositionHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboPositionHandle);
        glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        chunk.vboColorHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboColorHandle);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        usedChunk = null;
        positionBuffer = null;
        colorBuffer = null;
    }

    private static void generateOctreeVBO(OTree tree) {
        if (Camera.getInstance().frustum.cubeInFrustum(tree.x, tree.y, tree.z, tree.size)) {
            if (tree.breakTree()) {
                for (int i = 0; i < 8; i++)
                    generateOctreeVBO(tree.children[i]);
            } else if (usedChunk.getBlock(tree.x, tree.y, tree.z) != BlockType.AIR.id) {
                generateBlockVBO(tree.x, tree.y, tree.z);
            }
        }
    }

    private static void generateBlockVBO(int x, int y, int z) {
        if (usedChunk.getBlock(x, y, z + 1) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{x, y, z + 1f, x + 1f, y, z + 1f, x + 1f, y + 1f, z + 1f, x, y + 1f, z + 1f});
            colorBuffer.put(new float[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0});
            vertexCount += 4;
        }

        if (usedChunk.getBlock(x, y, z - 1) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{x + 1f, y, z, x, y, z, x, y + 1f, z, x + 1f, y + 1f, z});
            colorBuffer.put(new float[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0});
            vertexCount += 4;
        }

        if (usedChunk.getBlock(x - 1, y, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{x, y, z, x, y, z + 1f, x, y + 1f, z + 1f, x, y + 1f, z});
            colorBuffer.put(new float[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0});
            vertexCount += 4;
        }

        if (usedChunk.getBlock(x + 1, y, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{x + 1f, y, z + 1f, x + 1f, y, z, x + 1f, y + 1f, z, x + 1f, y + 1f, z + 1f});
            colorBuffer.put(new float[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0});
            vertexCount += 4;
        }

        if (usedChunk.getBlock(x, y + 1, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{x, y + 1f, z + 1f, x + 1f, y + 1f, z + 1f, x + 1f, y + 1f, z, x, y + 1f, z});
            colorBuffer.put(new float[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1});
            vertexCount += 4;
        }

        if (usedChunk.getBlock(x, y - 1, z) == BlockType.AIR.id) {
            positionBuffer.put(new float[]{x, y, z, x + 1f, y, z, x + 1f, y, z + 1f, x, y, z + 1f});
            colorBuffer.put(new float[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1});
            vertexCount += 4;
        }
    }

    public static void render(Chunk chunk) {
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboPositionHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glDrawArrays(GL_QUADS, 0, chunk.vertexCount);
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
    }
}
