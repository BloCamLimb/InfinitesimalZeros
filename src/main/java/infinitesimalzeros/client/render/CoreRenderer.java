package infinitesimalzeros.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CoreRenderer {
	
	public static TextureMap texMap = null;
	public static TextureAtlasSprite missingIcon;
    private static float lightmapLastX;
    private static float lightmapLastY;
    private static boolean optifineBreak = false;
	
	public static void initFluidTextures(TextureMap map) {
        
		missingIcon = map.getMissingSprite();

        texMap = map;
    }
	
    public static void resetColor() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
	
	public enum FluidType {
        STILL,
        FLOWING
    }
	
    public static void glowOn() {
        glowOn(15);
    }

    public static void glowOn(int glow) {
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

        try {
            lightmapLastX = OpenGlHelper.lastBrightnessX;
            lightmapLastY = OpenGlHelper.lastBrightnessY;
        } catch (NoSuchFieldError e) {
            optifineBreak = true;
        }

        float glowRatioX = Math.min((glow / 15F) * 240F + lightmapLastX, 240);
        float glowRatioY = Math.min((glow / 15F) * 240F + lightmapLastY, 240);

        if (!optifineBreak) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, glowRatioX, glowRatioY);
        }
    }

    public static void glowOff() {
        if (!optifineBreak) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapLastX, lightmapLastY);
        }

        GL11.glPopAttrib();
    }

    public static void blendOn() {
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void blendOff() {
        GL11.glPopAttrib();
    }
	
    public static void colorFluid(FluidStack fluid) {
        color(fluid.getFluid().getColor(fluid));
    }

    public static void color(int color) {
        float cR = (color >> 16 & 0xFF) / 255.0F;
        float cG = (color >> 8 & 0xFF) / 255.0F;
        float cB = (color & 0xFF) / 255.0F;

        GL11.glColor3f(cR, cG, cB);
    }
	
	public static void renderObject(Model3D object) {
        if (object == null) {
            return;
        }

        GlStateManager.pushMatrix();
        GL11.glTranslated(object.minX, object.minY, object.minZ);
        RenderResizableCuboid.INSTANCE.renderCube(object);
        GlStateManager.popMatrix();
    }
	
	public static TextureAtlasSprite getFluidTexture(FluidStack fluidStack, FluidType type) {
        if (fluidStack == null || fluidStack.getFluid() == null || type == null) {
            return missingIcon;
        }

        Fluid fluid = fluidStack.getFluid();

        ResourceLocation spriteLocation;
        if (type == FluidType.STILL) {
            spriteLocation = fluid.getStill(fluidStack);
        } else {
            spriteLocation = fluid.getFlowing(fluidStack);
        }
        TextureAtlasSprite sprite = texMap.getTextureExtry(spriteLocation.toString());
        return sprite != null ? sprite : missingIcon;
    }
	
	public static void prepFlowing(Model3D model, FluidStack fluid) {
        TextureAtlasSprite still = getFluidTexture(fluid, FluidType.STILL);
        TextureAtlasSprite flowing = getFluidTexture(fluid, FluidType.FLOWING);

        model.setTextures(still, still, flowing, flowing, flowing, flowing);
    }
	
	public static class DisplayInteger {

        public int display;

        public static DisplayInteger createAndStart() {
            DisplayInteger newInteger = new DisplayInteger();
            newInteger.display = GLAllocation.generateDisplayLists(1);
            GL11.glNewList(newInteger.display, GL11.GL_COMPILE);
            return newInteger;
        }

        public static void endList() {
            GL11.glEndList();
        }

        @Override
        public int hashCode() {
            int code = 1;
            code = 31 * code + display;
            return code;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof DisplayInteger && ((DisplayInteger) obj).display == display;
        }

        public void render() {
            GL11.glCallList(display);
        }
    }
	
}
