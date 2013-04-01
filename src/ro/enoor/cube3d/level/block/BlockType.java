package ro.enoor.cube3d.level.block;

import ro.enoor.cube3d.util.TextureManager;

public enum BlockType {
    AIR(0, false, TextureManager.AIR),
    STONE(1, true, TextureManager.STONE),
    DIRT(2, true, TextureManager.DIRT),
    GRASS(3, true, TextureManager.GRASS_TOP);

    public byte id;
    public boolean solid;
    public float[] texCoords;

    BlockType(int id, boolean solid, int blockID) {
        this.id = (byte) id;
        this.solid = solid;
        this.texCoords = TextureManager.getTexCoord(blockID);
    }
}
