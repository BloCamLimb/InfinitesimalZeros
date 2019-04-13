package infinitesimalzeros.common.container;

import javax.annotation.Nonnull;

import infinitesimalzeros.api.interfaces.IInventoryZ;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBasic extends Container {
	
	public TileEntity tileEntity;
	public IInventory inv;
	public int slotCount;
	
	public ContainerBasic(InventoryPlayer player, TileEntity tileEntity) {
		
		this.tileEntity = tileEntity;
		if(tileEntity instanceof IInventoryZ)
			this.inv = new ContainerCloud(tileEntity);
	}
	
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slotObject = this.inventorySlots.get(slot);
		
		if(slotObject != null && slotObject.getHasStack()) {
			
			ItemStack itemstack1 = slotObject.getStack();
			itemstack = itemstack1.copy();
			
			if(slot < slotCount) {
				if(!this.mergeItemStack(itemstack1, slotCount, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if(!this.mergeItemStack(itemstack1, 0, slotCount, false)) {
				return ItemStack.EMPTY;
			}
			
			if(itemstack1.isEmpty()) {
				slotObject.putStack(ItemStack.EMPTY);
			} else {
				slotObject.onSlotChanged();
			}
		}
		
		return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return inv!=null&&inv.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		
		super.onContainerClosed(playerIn);
		
		if(inv!=null)
			inv.closeInventory(playerIn);
	}
	
}
