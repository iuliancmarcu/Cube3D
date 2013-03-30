package ro.enoor.cube3d.level.block;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum BlockType {
    AIR(0, false, ""),
    WOOD(1, true, "wood");

    public byte id;
    public int textureID;
    public boolean solid;

    BlockType(int id, boolean solid, String textureName) {
        this.id = (byte) id;
        this.solid = solid;

        textureID = loadTexture(textureName);
    }

    private int loadTexture(String name) {
        try {
            return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/textures/" + name + ".png"))).getTextureID();
        } catch (IOException e) {
        } finally {
            return 0;
        }
    }
}
