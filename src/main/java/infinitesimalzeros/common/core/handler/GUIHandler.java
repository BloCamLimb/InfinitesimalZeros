package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.client.gui.GuiNanaSmelter;
import infinitesimalzeros.client.gui.GuiSaltTank;
import infinitesimalzeros.common.container.ContainerNanaSmelter;
import infinitesimalzeros.common.container.ContainerSaltTank;
import infinitesimalzeros.common.tileentities.TileEntitySaltTank;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class GUIHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		switch(ID) {
			case 0:
				return new ContainerNanaSmelter(player.inventory, (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z)));
			case 2:
				return new ContainerSaltTank(player.inventory, (TileEntitySaltTank) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		switch(ID) {
			case 0:
				return new GuiNanaSmelter(player.inventory, (TileEntitySmelter) world.getTileEntity(new BlockPos(x, y, z)));
			case 2:
				return new GuiSaltTank(player.inventory, (TileEntitySaltTank) world.getTileEntity(new BlockPos(x, y, z)));
		}

		return null;
	}

}
