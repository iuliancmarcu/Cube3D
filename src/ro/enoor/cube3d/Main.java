package ro.enoor.cube3d;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import ro.enoor.cube3d.util.TextureManager;
import ro.enoor.cube3d.world.Game;
import ro.enoor.cube3d.world.GameRenderer;

public class Main {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public Game game;
    public GameRenderer renderer;

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
            Display.setTitle("Cube3D (FPS: " + fps + ")");
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));

            Display.create();

            Mouse.setGrabbed(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        game = new Game();
        renderer = new GameRenderer(game);

        lastFrame = getTime();
        lastFPS = getTime();

        TextureManager.loadTextures();

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            renderer.render();
            input();
            game.update(getDelta());

            updateFPS();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    private void input() {
        renderer.camera.processInput();

        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_Q)
                renderer.showDebug ^= true;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
