package ro.enoor.cube3d;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import ro.enoor.cube3d.util.TextureManager;
import ro.enoor.cube3d.world.World;
import ro.enoor.cube3d.world.WorldRenderer;
import ro.enoor.cube3d.world.rendering.Camera;

public class Main {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public World world;
    public WorldRenderer renderer;

    private long lastFrame;
    private long lastFPS;
    private int fps;

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
//            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));

            Display.setTitle("Cube3D");
            Display.create();

            Mouse.setGrabbed(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        world = new World();
        renderer = new WorldRenderer(world);

        renderer.initGL();
        lastFrame = getTime();
        lastFPS = getTime();

        TextureManager.loadTextures();

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            input();
            world.update(getDelta());
            renderer.render();

            Display.setTitle(Camera.getInstance().toString());
            updateFPS();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    private void input() {
        renderer.camera.processInput();
    }

    public static void main(String[] args) {
        new Main();
    }
}
