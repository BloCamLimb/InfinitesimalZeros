package infinitesimalzeros.common.container;

import infinitesimalzeros.common.tileentity.basis.TileEntityElectricMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerElectricMachine extends Container {
	
	private TileEntityElectricMachine tileEntity;
	
	public ContainerElectricMachine(InventoryPlayer player, TileEntityElectricMachine tileEntity) {
		
		this.tileEntity = tileEntity;
		
		tileEntity.open(player.player);
		tileEntity.openInventory(player.player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		
		super.onContainerClosed(playerIn);
		tileEntity.close(playerIn);
		tileEntity.closeInventory(playerIn);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return tileEntity.isUsableByPlayer(playerIn);
	}
	
}
