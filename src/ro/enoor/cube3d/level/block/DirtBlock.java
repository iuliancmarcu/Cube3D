package ro.enoor.cube3d.level.block;

import ro.enoor.cube3d.util.TextureManager;

public class DirtBlock extends Block {
    public DirtBlock(int id) {
        super(id);

        for (int i = 0; i < 6; i++)
            blockTexCoord[i] = TextureManager.getTexCoordinates(TextureManager.DIRT);
    }
}
