package infinitesimalzeros.client.render.machine;

import org.lwjgl.opengl.GL11;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.client.render.CoreRenderer;
import infinitesimalzeros.client.render.FluidRenderer;
import infinitesimalzeros.client.render.Model3D;
import infinitesimalzeros.client.render.CoreRenderer.FluidType;
import infinitesimalzeros.client.render.FluidRenderer.RenderData;
import infinitesimalzeros.common.tileentities.TileEntitySaltTank;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.IZUtils.ResourceType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class SaltTankTESR extends TileEntitySpecialRenderer<TileEntitySaltTank> {
	
	@Override
	public void render(TileEntitySaltTank tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		//super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		
		final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockPos blockPos = tileEntity.getPos();
		IBlockState state = getWorld().getBlockState(blockPos);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();

		
		
		GlStateManager.pushMatrix();
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		IBakedModel model1 = blockRenderer.getBlockModelShapes().getModelForState(state);
		//Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.translate(x+.5, y+2.5, z+.5);
		GlStateManager.rotate(-180, 0, blockPos.getY(), blockPos.getZ());
		GlStateManager.rotate(180, 0, blockPos.getY(), blockPos.getZ());
		GlStateManager.scale(5, 5, 5);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
        
		
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		if(Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(7425);
		else
			GlStateManager.shadeModel(7424);

		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		worldRenderer.setTranslation(-.5-blockPos.getX(), -.5-blockPos.getY(), -.5-blockPos.getZ());
		worldRenderer.color(255, 255, 255, 255);
		blockRenderer.getBlockModelRenderer().renderModel(tileEntity.getWorld(), model1, state, blockPos, worldRenderer, true);
		
		
		worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		
		

        GlStateManager.popMatrix();
        
        {
			RenderData data = new RenderData();

            data.location = Coord4D.get(tileEntity);
            data.height = 1;
            data.length = 5;
            data.width = 5;
            data.fluidType = tileEntity.inputTank.getFluid();

            //bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            
            if (data.location != null && tileEntity.inputTank.getFluid() != null) {
                FluidRenderer.push();

                FluidRenderer.translateToOrigin(data.location);
                GlStateManager.scale(0.9513, 0.55, 0.9513);
                CoreRenderer.glowOn(tileEntity.inputTank.getFluid().getFluid().getLuminosity());
                CoreRenderer.colorFluid(tileEntity.inputTank.getFluid());
                //InfinitesimalZeros.logger.info(FluidRenderer.getTankDisplay(data, tileEntity.inputTank.getFluidAmount()/tileEntity.inputTank.getCapacity()));
                //FluidRenderer.getTankDisplay(data, tileEntity.inputTank.getFluidAmount()/tileEntity.inputTank.getCapacity()).render();
                
                Model3D toReturn = new Model3D();
                toReturn.baseBlock = Blocks.WATER;
                toReturn.setTexture(CoreRenderer.getFluidTexture(tileEntity.inputTank.getFluid(), FluidType.STILL));
                
                if (data.fluidType.getFluid().getStill(tileEntity.inputTank.getFluid()) != null) {
                    toReturn.minX = 0 + .01;
                    toReturn.minY = 0;
                    toReturn.minZ = 0 + .01;
                    
                    toReturn.maxX = data.length - .01;
                    toReturn.maxY = (double) tileEntity.inputTank.getFluidAmount() / tileEntity.inputTank.getCapacity() + .01;
                    toReturn.maxZ = data.width - .01;

                    CoreRenderer.renderObject(toReturn);
                }
                
                
                CoreRenderer.glowOff();
                CoreRenderer.resetColor();

                FluidRenderer.pop();

                /*for (ValveData valveData : tileEntity.valveViewing) {
                    FluidRenderer.push();

                    FluidRenderer.translateToOrigin(valveData.location);

                    CoreRenderer.glowOn(tileEntity.structure.fluidStored.getFluid().getLuminosity());
                    CoreRenderer.colorFluid(tileEntity.structure.fluidStored);

                    FluidRenderer.getValveDisplay(ValveRenderData.get(data, valveData)).render();

                    CoreRenderer.glowOff();
                    CoreRenderer.resetColor();

                    FluidRenderer.pop();
                }*/
            }
		}
        
	}
	
}
