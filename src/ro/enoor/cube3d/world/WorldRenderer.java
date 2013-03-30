package ro.enoor.cube3d.world;

import org.lwjgl.util.vector.Vector3f;
import ro.enoor.cube3d.Main;
import ro.enoor.cube3d.level.chunk.ChunkManager;
import ro.enoor.cube3d.world.rendering.Camera;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class WorldRenderer {
    public World world;
    public Camera camera = Camera.getInstance();

    public WorldRenderer(World world) {
        this.world = world;
        camera.position = new Vector3f(-16 * 2f, 18f, -16 * 2f);
    }

    public void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45f, (float) Main.WIDTH / Main.HEIGHT, 0.1f, 80f);
        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        camera.lookThrough();

        world.chunkManager.renderChunks();
    }
}
