package ro.enoor.cube3d.world.rendering;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera {
    private static Camera instance = new Camera();

    public static Camera getInstance() {
        return instance;
    }

    public Frustum frustum;
    public Vector3f position;
    public float yaw;
    public float pitch;

    private Camera() {
        frustum = new Frustum();
        position = new Vector3f();
        yaw = 0f;
        pitch = 0f;
    }

    public void processInput() {
        processMouse();
        processKeyboard();
    }

    private void processMouse() {
        float moveX = Mouse.getDX();
        float moveY = -Mouse.getDY();

        yaw += moveX * 0.05f;
        if (yaw > 360f) yaw = 0f;
        else if (yaw < 0f) yaw = 360f;

        pitch += moveY * 0.05f;
        if (pitch > 90f) pitch = 90f;
        else if (pitch < -90f) pitch = -90f;
    }

    private void processKeyboard() {
        boolean upPressed = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
        boolean downPressed = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        boolean leftPressed = Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        boolean rightPressed = Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
        boolean jumpPressed = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean duckPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        if (upPressed) {
            position.x += 0.5f * Math.sin(Math.toRadians(yaw));
            position.z -= 0.5f * Math.cos(Math.toRadians(yaw));
        }
        if (downPressed) {
            position.x -= 0.5f * Math.sin(Math.toRadians(yaw));
            position.z += 0.5f * Math.cos(Math.toRadians(yaw));
        }
        if (leftPressed) {
            position.x += 0.5f * Math.sin(Math.toRadians(yaw - 90));
            position.z -= 0.5f * Math.cos(Math.toRadians(yaw - 90));
        }
        if (rightPressed) {
            position.x += 0.5f * Math.sin(Math.toRadians(yaw + 90));
            position.z -= 0.5f * Math.cos(Math.toRadians(yaw + 90));
        }
        if (jumpPressed) {
            position.y += 0.5f;
        }
        if (duckPressed) {
            position.y -= 0.5f;
        }
    }

    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(-position.x, -position.y, -position.z);

        frustum.calculateFrustum();
    }

    public String toString() {
        return "X: " + position.x + " Y: " + position.y + " Z: " + position.z + " Pitch: " + pitch + " Yaw: " + yaw;
    }
}
