package ro.enoor.cube3d.world;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import ro.enoor.cube3d.Main;
import ro.enoor.cube3d.level.chunk.Chunk;
import ro.enoor.cube3d.level.chunk.ChunkManager;
import ro.enoor.cube3d.world.rendering.Camera;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class WorldRenderer {
    public static final int VIEW_RADIUS = 4;
    public World world;
    public Camera camera = Camera.getInstance();

    private ChunkManager manager = ChunkManager.getInstance();

    public WorldRenderer(World world) {
        this.world = world;
        camera.position = new Vector3f(0f, Chunk.SIZE * 2, 0f);
    }

    public void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45f, (float) Main.WIDTH / Main.HEIGHT, 0.1f, Chunk.SIZE * VIEW_RADIUS);
        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_FOG);

        // Setup fog
        FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
        fogColor.put(0.5f).put(0.5f).put(1.0f).put(1.0f).flip();

        glFogi(GL_FOG_MODE, GL_LINEAR);
        glFog(GL_FOG_COLOR, fogColor);
        glFogf(GL_FOG_DENSITY, 0.35f);
        glHint(GL_FOG_HINT, GL_DONT_CARE);
        glFogf(GL_FOG_START, Chunk.SIZE * (VIEW_RADIUS - 1));
        glFogf(GL_FOG_END, Chunk.SIZE * VIEW_RADIUS);

        glClearColor(0.5f, 0.5f, 1.0f, 1.0f);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        camera.lookThrough();

        manager.renderChunks();
    }
}
