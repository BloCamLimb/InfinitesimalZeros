package infinitesimalzeros.common.util.handlers;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.block.NanaFurnaceTE;
import infinitesimalzeros.common.tileentity.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentity.TileEntitySmelter;
import infinitesimalzeros.common.tileentity.TileEntitySmelterAdv;
import infinitesimalzeros.common.tileentity.TileEntitySmeltingFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void registerTileEntity() {
		
		GameRegistry.registerTileEntity(NanaFurnaceTE.class, new ResourceLocation(InfinitesimalZeros.MODID, "nanafurnace"));
		GameRegistry.registerTileEntity(TileEntitySmelter.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelter"));
		GameRegistry.registerTileEntity(TileEntitySmelterAdv.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelter_adv"));
		GameRegistry.registerTileEntity(TileEntitySmeltingFactory.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelting_factory"));
		GameRegistry.registerTileEntity(TileEntityAdvancedBoundingBox.class, new ResourceLocation(InfinitesimalZeros.MODID, "advanced_bounding_box"));
	}
	
}
