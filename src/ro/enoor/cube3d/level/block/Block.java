package ro.enoor.cube3d.level.block;

public abstract class Block {
    public static final Block[] blocks = new Block[64];

    public static final Block AIR = new AirBlock(0);
    public static final Block STONE = new DirtBlock(1);
    public static final Block DIRT = new DirtBlock(2);
    public static final Block GRASS = new GrassBlock(3);
    public static final Block WOOD = new WoodBlock(4);

    public byte id;
    public float[][] blockTexCoord;

    public Block(int id) {
        this.id = (byte) id;
        if (blocks[id] != null) throw new InternalError("Block duplication at id " + id);
        blocks[id] = this;
        blockTexCoord = new float[6][12];
    }
}
