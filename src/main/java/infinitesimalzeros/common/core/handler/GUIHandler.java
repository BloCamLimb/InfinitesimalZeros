package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.client.gui.GUITestTE;
import infinitesimalzeros.client.gui.NanaGUI;
import infinitesimalzeros.common.blocks.NanaFurnaceTE;
import infinitesimalzeros.common.container.ContainerTestTE;
import infinitesimalzeros.common.container.NanaFurnaceCon;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == 0)
			return new NanaFurnaceCon(player.inventory, (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == 1)
			return new ContainerTestTE(player.inventory, (NanaFurnaceTE) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == 0)
			return new NanaGUI(player.inventory, (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == 1)
			return new GUITestTE(player.inventory, (NanaFurnaceTE) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
	
}
