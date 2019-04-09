package infinitesimalzeros.common.util.handlers;

import infinitesimalzeros.common.block.NanaFurnaceCon;
import infinitesimalzeros.common.block.NanaGUI;
import infinitesimalzeros.common.tileentity.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == 0)
			return new NanaFurnaceCon(player.inventory, (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == 0)
			return new NanaGUI(player.inventory, (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
	
}
