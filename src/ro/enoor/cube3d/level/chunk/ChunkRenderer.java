package ro.enoor.cube3d.level.chunk;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

public class ChunkRenderer {
    /**
     * Render the chunk using it's VBOs.
     *
     * @param chunk Chunk that should be rendered.
     */
    public void render(Chunk chunk) {
        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboPositionHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, chunk.vboTextureHandle);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glDrawArrays(GL_TRIANGLES, 0, chunk.vertexCount);
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }
}
