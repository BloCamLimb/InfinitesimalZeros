package infinitesimalzeros.common.util.handlers;

import infinitesimalzeros.common.block.NanaFurnaceTE;
import infinitesimalzeros.common.tileentity.TileEntitySmelter;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void registerTileEntity() {
		GameRegistry.registerTileEntity(NanaFurnaceTE.class, "infinitesimalzeros:nanafurnace");
		GameRegistry.registerTileEntity(TileEntitySmelter.class, "infinitesimalzeros:smelter");
	}

}
