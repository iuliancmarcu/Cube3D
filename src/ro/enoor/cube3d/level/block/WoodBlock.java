package ro.enoor.cube3d.level.block;

import ro.enoor.cube3d.util.TextureManager;

public class WoodBlock extends Block {
    public WoodBlock(int id) {
        super(id);

        for (int i = 0; i < 6; i++)
            blockTexCoord[i] = TextureManager.getTexCoordinates(TextureManager.WOOD);
    }
}
