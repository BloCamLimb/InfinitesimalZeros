package infinitesimalzeros.common.tileentities;

import org.lwjgl.opengl.GL11;

import infinitesimalzeros.api.interfaces.IModelRender;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SmelterTESR extends TileEntitySpecialRenderer<TileEntitySmelter> {
	
	/*private ModelSmelter model = new ModelSmelter();
	
    @Override
    public void render(TileEntitySmelter tileEntity, double x, double y, double z, float partialTick, int destroyStage, float alpha) {
    	
        /*GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        bindTexture(IZUtils.getResource(ResourceType.RENDER, "DigitalMiner.png"));

        switch (tileEntity.facing.ordinal()) {
            case 2:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
                break;
            case 3:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
                break;
            case 4:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
                break;
            case 5:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
                break;
        }

        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        model.render(0.0625F, tileEntity.isActive, rendererDispatcher.renderEngine, true);
        GlStateManager.popMatrix();

        if (tileEntity.clientRendering) {
            MinerVisualRenderer.render(tileEntity);
        }
    
    
    }*/

	
	@Override
	public void render(TileEntitySmelter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		//super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		/*final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockPos blockPos = te.getPos();
		IBlockState state = getWorld().getBlockState(blockPos);

		state = state.getBlock().getActualState(state, getWorld(), blockPos);

		IBakedModel model = blockRenderer.getBlockModelShapes().getModelForState(state);


		float angle = 0;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();

		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(.5, .5, .5);
		GlStateManager.scale(0.01, 0.01, 0.01);
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

        float lightmapLastX = 0;
        float lightmapLastY = 0;
        boolean optifineBreak = true;
        
        try {
            lightmapLastX = OpenGlHelper.lastBrightnessX;
            lightmapLastY = OpenGlHelper.lastBrightnessY;
        } catch (NoSuchFieldError e) {
            optifineBreak = true;
        }

        float glowRatioX = Math.min((15 / 15F) * 240F + lightmapLastX, 240);
        float glowRatioY = Math.min((15 / 15F) * 240F + lightmapLastY, 240);

        if (!optifineBreak) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, glowRatioX, glowRatioY);
        }
        
        GL11.glDisable(GL11.GL_LIGHTING);
        //blockRenderer.getBlockModelRenderer().renderModel(), modelIn, blockStateIn, blockPosIn, buffer, true);
        //renderModelBrightness(model, state, 15, true);
        
		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		if(Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(7425);
		else
			GlStateManager.shadeModel(7424);
		GlStateManager.translate(te.facing.getFrontOffsetX()*.5, 0, te.facing.getFrontOffsetZ()*.5);
		GlStateManager.rotate(angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		worldRenderer.setTranslation(-.5-blockPos.getX(), -.5-blockPos.getY(), -.5-blockPos.getZ());
		worldRenderer.color(255, 255, 255, 255);
		blockRenderer.getBlockModelRenderer().renderModel(te.getWorld(), model, state, blockPos, worldRenderer, true);
		worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GlStateManager.rotate(-angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		GlStateManager.translate(te.facing.getFrontOffsetX()*-1, 0, te.facing.getFrontOffsetZ()*-1);
		GlStateManager.rotate(-angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		worldRenderer.setTranslation(-.5-blockPos.getX(), -.5-blockPos.getY(), -.5-blockPos.getZ());
		worldRenderer.color(255, 255, 255, 255);
		blockRenderer.getBlockModelRenderer().renderModel(te.getWorld(), model, state, blockPos, worldRenderer, true);
		worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GlStateManager.rotate(angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());

		
		 if (!optifineBreak) {
	            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapLastX, lightmapLastY);
	        }

	        GL11.glPopAttrib();
		RenderHelper.enableStandardItemLighting();
		GL11.glPopAttrib();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();

        GlStateManager.translate(.5, 0, .5);
        long angle = 0;
        GlStateManager.rotate(angle, 0, 1, 0);
        GlStateManager.scale(0.1, 0.1, 0.1);
        RenderHelper.disableStandardItemLighting();
        
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = getWorld().getBlockState(te.getPos());
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();*/
		
		
		
		
		
		
		final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockPos blockPos = te.getPos();
		IBlockState state = getWorld().getBlockState(blockPos);

		state = state.getBlock().getActualState(state, getWorld(), blockPos);
		IBakedModel model = blockRenderer.getBlockModelShapes().getModelForState(state);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();

		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		GlStateManager.pushMatrix();
		
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		
		GlStateManager.translate(x+.5, y+1.5, z+.5);
		GlStateManager.scale(2.8, 2.8, 2.8);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		float lightmapLastX = 0;
		float lightmapLastY = 0;
		boolean optifineBreak = false;
		
        try {
            lightmapLastX = OpenGlHelper.lastBrightnessX;
            lightmapLastY = OpenGlHelper.lastBrightnessY;
        } catch (NoSuchFieldError e) {
            optifineBreak = true;
        }

        float glowRatioX = Math.min((15 / 15F) * 240F + lightmapLastX, 240);
        float glowRatioY = Math.min((15 / 15F) * 240F + lightmapLastY, 240);

        if (!optifineBreak) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, glowRatioX, glowRatioY);
        }
		
		
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		if(Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(7425);
		else
			GlStateManager.shadeModel(7424);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		worldRenderer.setTranslation(-.5-blockPos.getX(), -.5-blockPos.getY(), -.5-blockPos.getZ());
		worldRenderer.color(255, 255, 255, 255);
		blockRenderer.getBlockModelRenderer().renderModel(te.getWorld(), model, state, blockPos, worldRenderer, true);
		
		
		worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		RenderHelper.enableStandardItemLighting();
		
		
        
		GlStateManager.popMatrix();
		
	}
	
}
