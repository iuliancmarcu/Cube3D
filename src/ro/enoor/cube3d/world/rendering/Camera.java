package ro.enoor.cube3d.world.rendering;

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
    public boolean moved;

    private Camera() {
        frustum = new Frustum();
        position = new Vector3f();
        yaw = 0f;
        pitch = 0f;
    }

    public void yaw(float rotX) {
        if (Math.abs(rotX) > 0.01f)
            moved = true;

        yaw += rotX;

        if (yaw > 180f) yaw = -180f;
        else if (yaw < -180f) yaw = 180f;
    }

    public void pitch(float rotY) {
        if (Math.abs(rotY) > 0.01f)
            moved = true;

        pitch += rotY;

        if (pitch > 90f) pitch = 90f;
        else if (pitch < -90f) pitch = -90f;
    }

    public void walkForward(float distance) {
        moved = true;

        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }

    public void walkBackwards(float distance) {
        moved = true;

        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

    public void walkLeft(float distance) {
        moved = true;

        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    public void walkRight(float distance) {
        moved = true;

        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    public void ascend(float distance) {
        moved = true;

        position.y += distance;
    }

    public void descend(float distance) {
        moved = true;

        position.y -= distance;
    }

    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, -position.y, position.z);

//        frustum.calculateFrustum();
    }

    public String toString() {
        return "X: " + position.x + " Y: " + position.y + " Z: " + position.z + " Pitch: " + pitch + " Yaw: " + yaw;
    }
}
