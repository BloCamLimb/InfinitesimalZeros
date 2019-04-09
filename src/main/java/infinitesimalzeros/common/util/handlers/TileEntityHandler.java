package infinitesimalzeros.common.util.handlers;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.block.NanaFurnaceTE;
import infinitesimalzeros.common.tileentity.TileEntitySmelter;
import infinitesimalzeros.common.tileentity.TileEntitySmelterAdv;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void registerTileEntity() {
		GameRegistry.registerTileEntity(NanaFurnaceTE.class, new ResourceLocation(InfinitesimalZeros.MODID, "nanafurnace"));
		GameRegistry.registerTileEntity(TileEntitySmelter.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelter"));
		GameRegistry.registerTileEntity(TileEntitySmelterAdv.class, new ResourceLocation(InfinitesimalZeros.MODID, "smelter_adv"));
	}

}
