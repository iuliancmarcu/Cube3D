package ro.enoor.cube3d.world;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import ro.enoor.cube3d.Main;
import ro.enoor.cube3d.level.chunk.Chunk;
import ro.enoor.cube3d.level.chunk.ChunkManager;
import ro.enoor.cube3d.util.Font;
import ro.enoor.cube3d.util.TextureManager;
import ro.enoor.cube3d.world.rendering.Camera;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GameRenderer {
    public Game game;
    public Camera camera = Camera.getInstance();
    public boolean showDebug;

    private ChunkManager manager = ChunkManager.getInstance();

    public GameRenderer(Game game) {
        this.game = game;
        camera.position = new Vector3f(0f, Chunk.SIZE * 2, 0f);

        initGL();
    }

    public void initGL() {
        glEnable(GL_TEXTURE_2D);

        // Init fog
        FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
        fogColor.put(0.5f).put(0.5f).put(1.0f).put(1.0f).flip();

        glFogi(GL_FOG_MODE, GL_LINEAR);
        glFog(GL_FOG_COLOR, fogColor);
        glFogf(GL_FOG_DENSITY, 0.35f);
        glHint(GL_FOG_HINT, GL_DONT_CARE);
        glFogf(GL_FOG_START, Chunk.SIZE * (Camera.VIEW_RADIUS - 1));
        glFogf(GL_FOG_END, Chunk.SIZE * Camera.VIEW_RADIUS);

        glClearColor(0.5f, 0.5f, 1.0f, 1.0f);
    }

    public void setup3D() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45f, (float) Main.WIDTH / Main.HEIGHT, 0.1f, Chunk.SIZE * Camera.VIEW_RADIUS);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_FOG);
    }

    private void setup2D() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0f, Main.WIDTH, 0f, Main.HEIGHT, -1f, 1f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
        glDisable(GL_FOG);
    }

    public void renderWorld() {
        setup3D();
        camera.lookThrough();
        manager.renderChunks();
    }

    public void renderInterface() {
        setup2D();
        Font.renderString(Camera.getInstance().toString(), TextureManager.fontTextureID, 0f, 0f, 32f, 32f);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderWorld();
        if (showDebug)
            renderInterface();
    }
}
