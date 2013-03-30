package ro.enoor.cube3d.level.chunk;

import org.lwjgl.BufferUtils;
import ro.enoor.cube3d.level.block.BlockType;
import ro.enoor.cube3d.world.rendering.Camera;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public abstract class ChunkRenderer {
    private static final int SIZE = Chunk.SIZE;

    static int vboCount = 0;

    public static void generateVBO(Chunk chunk) {
        vboCount = 0;

        FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 3);
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 3);

        for(int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                for (int z = 0; z < SIZE; z++)
                    if(chunk.blocks[x][y][z] != 0)
                        generateBlockVBO(x, y, z, chunk, positionBuffer, colorBuffer);

        positionBuffer.flip();
        colorBuffer.flip();

        chunk.vboCount = vboCount;

        glDeleteBuffers(chunk.vboPositionHandle);
        glDeleteBuffers(chunk.vboColorHandle);

        chunk.vboPositionHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboPositionHandle);
        glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        chunk.vboColorHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboColorHandle);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void generateBlockVBO(int x, int y, int z, Chunk chunk, FloatBuffer positionBuffer, FloatBuffer colorBuffer) {
        if(Camera.getInstance().frustum.cubeInFrustum(x + chunk.offsetX, y, z + chunk.offsetZ, 1f)) {
            if(chunk.getBlock(x, y, z + 1) == 0) {
                positionBuffer.put(offsetPositionArray(new float[] { x, y, z + 1, x + 1, y, z + 1, x + 1, y + 1, z + 1, x, y + 1, z + 1 }, chunk));
                colorBuffer.put(new float[] { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 });
                vboCount += 4;
            }

            if(chunk.getBlock(x, y, z - 1) == 0) {
                positionBuffer.put(offsetPositionArray(new float[] { x + 1, y, z, x, y, z, x, y + 1, z, x + 1, y + 1, z }, chunk));
                colorBuffer.put(new float[] { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 });
                vboCount += 4;
            }

            if(chunk.getBlock(x - 1, y, z) == 0) {
                positionBuffer.put(offsetPositionArray(new float[] { x, y, z, x, y, z + 1, x, y + 1, z + 1, x, y + 1, z }, chunk));
                colorBuffer.put(new float[] { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 });
                vboCount += 4;
            }

            if(chunk.getBlock(x + 1, y, z) == 0) {
                positionBuffer.put(offsetPositionArray(new float[] { x + 1, y, z + 1, x + 1, y, z, x + 1, y + 1, z, x + 1, y + 1, z + 1 }, chunk));
                colorBuffer.put(new float[] { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 });
                vboCount += 4;
            }

            if(chunk.getBlock(x, y + 1, z) == 0) {
                positionBuffer.put(offsetPositionArray(new float[] { x, y + 1, z + 1, x + 1, y + 1, z + 1, x + 1, y + 1, z, x, y + 1, z }, chunk));
                colorBuffer.put(new float[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });
                vboCount += 4;
            }

            if(chunk.getBlock(x, y - 1, z) == 0) {
                positionBuffer.put(offsetPositionArray(new float[] { x, y, z, x + 1, y, z, x + 1, y, z + 1, x, y, z + 1 }, chunk));
                colorBuffer.put(new float[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });
                vboCount += 4;
            }
        }
    }

    private static float[] offsetPositionArray(float[] array, Chunk chunk) {
        for(int i = 0; i < array.length; i++) {
            if(i % 3 == 0) array[i] += chunk.offsetX;
            else if((i - 2) % 3 == 0) array[i] += chunk.offsetZ;

            array[i] -= 0.5f;
        }
        return array;
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
        glDrawArrays(GL_QUADS, 0, chunk.vboCount);
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
    }
}
