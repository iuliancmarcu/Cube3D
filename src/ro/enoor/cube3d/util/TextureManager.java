package ro.enoor.cube3d.util;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class TextureManager {
    public static int textureAtlasID;

    public static int GRASS_TOP = 0;
    public static int STONE = 1;
    public static int DIRT = 2;
    public static int GRASS_SIDE = 3;
    public static int WOOD = 4;
    public static int AIR = 253;

    public static void loadTextures() {
        try {
            textureAtlasID = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/texturePack.png")), GL11.GL_NEAREST).getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float[] getTexCoordinates(int blockID) {
        int blockX = blockID % 16;
        int blockY = blockID / 16;

        return new float[]{
                1f / 16f * blockX, 1f / 16f * blockY,
                1f / 16f * blockX + 1f / 16f, 1f / 16f * blockY,
                1f / 16f * blockX + 1f / 16f, 1f / 16f * blockY + 1f / 16f,

                1f / 16f * blockX, 1f / 16f * blockY,
                1f / 16f * blockX + 1f / 16f, 1f / 16f * blockY + 1f / 16f,
                1f / 16f * blockX, 1f / 16f * blockY + 1f / 16f
        };
    }
}
