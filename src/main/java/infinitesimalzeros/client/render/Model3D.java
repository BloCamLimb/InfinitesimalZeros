package infinitesimalzeros.client.render;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class Model3D {

    public double posX, posY, posZ;

    public double minX, minY, minZ;
    public double maxX, maxY, maxZ;

    public double textureStartX = 0, textureStartY = 0, textureStartZ = 0;
    public double textureSizeX = 16, textureSizeY = 16, textureSizeZ = 16;
    public double textureOffsetX = 0, textureOffsetY = 0, textureOffsetZ = 0;

    public int[] textureFlips = new int[]{2, 2, 2, 2, 2, 2};

    public TextureAtlasSprite[] textures = new TextureAtlasSprite[6];

    public boolean[] renderSides = new boolean[]{true, true, true, true, true, true, false};

    public Block baseBlock = Blocks.SAND;

    public final void setBlockBounds(double xNeg, double yNeg, double zNeg, double xPos, double yPos, double zPos) {
        minX = xNeg;
        minY = yNeg;
        minZ = zNeg;
        maxX = xPos;
        maxY = yPos;
        maxZ = zPos;
    }

    public double sizeX() {
        return maxX - minX;
    }

    public double sizeY() {
        return maxY - minY;
    }

    public double sizeZ() {
        return maxZ - minZ;
    }

    public void setSideRender(EnumFacing side, boolean value) {
        renderSides[side.ordinal()] = value;
    }

    public boolean shouldSideRender(EnumFacing side) {
        return renderSides[side.ordinal()];
    }

    public TextureAtlasSprite getBlockTextureFromSide(int i) {
        return textures[i];
    }

    public void setTexture(TextureAtlasSprite tex) {
        Arrays.fill(textures, tex);
    }

    public void setTextures(TextureAtlasSprite down, TextureAtlasSprite up, TextureAtlasSprite north,
          TextureAtlasSprite south, TextureAtlasSprite west, TextureAtlasSprite east) {
        textures[0] = down;
        textures[1] = up;
        textures[2] = north;
        textures[3] = south;
        textures[4] = west;
        textures[5] = east;
    }
}
