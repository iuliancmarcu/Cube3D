package ro.enoor.cube3d.level.block;

import ro.enoor.cube3d.util.TextureManager;

public class GrassBlock extends Block {
    public GrassBlock(int id) {
        super(id);

        for (int i = 0; i < 4; i++)
            blockTexCoord[i] = TextureManager.getTexCoordinates(TextureManager.GRASS_SIDE);
        blockTexCoord[4] = TextureManager.getTexCoordinates(TextureManager.GRASS_TOP);
        blockTexCoord[5] = TextureManager.getTexCoordinates(TextureManager.DIRT);
    }
}
