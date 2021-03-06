package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentities.TileEntitySaltTank;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import infinitesimalzeros.common.tileentities.TileEntitySmeltingFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void registerTileEntity() {
		
		GameRegistry.registerTileEntity(TileEntitySmelter.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelter"));
		GameRegistry.registerTileEntity(TileEntitySaltTank.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelter_adv"));
		GameRegistry.registerTileEntity(TileEntitySmeltingFactory.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelting_factory"));
		GameRegistry.registerTileEntity(TileEntityAdvancedBoundingBox.class, new ResourceLocation(InfinitesimalZeros.MODID, "advanced_bounding_box"));
	}
	
}
