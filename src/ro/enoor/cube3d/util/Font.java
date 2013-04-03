package ro.enoor.cube3d.util;

import static org.lwjgl.opengl.GL11.*;

public class Font {
    public static void renderString(String string, int textureObject, float x, float y, float characterWidth, float characterHeight) {
        glBindTexture(GL_TEXTURE_2D, textureObject);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        glPushMatrix();
        glLoadIdentity();
        glTranslatef(x, y, 0);

        glBegin(GL_QUADS);
        for (int i = 0; i < string.length(); i++) {
            int asciiCode = (int) string.charAt(i);
            final float cellSize = 1.0f / 16;
            float cellX = (asciiCode % 16) * cellSize;
            float cellY = (asciiCode / 16) * cellSize;

            glTexCoord2f(cellX, cellY + cellSize);
            glVertex2f(i * characterWidth / 3, y);
            glTexCoord2f(cellX + cellSize, cellY + cellSize);
            glVertex2f(i * characterWidth / 3 + characterWidth / 2, y);
            glTexCoord2f(cellX + cellSize, cellY);
            glVertex2f(i * characterWidth / 3 + characterWidth / 2, y + characterHeight);
            glTexCoord2f(cellX, cellY);
            glVertex2f(i * characterWidth / 3, y + characterHeight);
        }
        glEnd();

        glDisable(GL_BLEND);
        glPopMatrix();
    }
}
