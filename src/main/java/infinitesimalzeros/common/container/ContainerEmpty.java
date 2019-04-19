package infinitesimalzeros.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerEmpty extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return false;
	}
	
}
