package infinitesimalzeros.common.container;

import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ContainerCloud implements IInventory {
	
	TileEntityBasicBlock tileEntity;
	
	IInventoryZero inv;
	
	public ContainerCloud(TileEntityBasicBlock tileEntity) {
		
		this.tileEntity = tileEntity;
		inv = (IInventoryZero) tileEntity;
	}
	
	@Override
	public String getName() {
		
		return tileEntity.getBlockType().getUnlocalizedName();
	}
	
	@Override
	public boolean hasCustomName() {
		
		return true;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(getName());
	}
	
	@Override
	public int getSizeInventory() {
		
		return inv.getInventory().size();
	}
	
	@Override
	public boolean isEmpty() {
		
		for(ItemStack stack : inv.getInventory()) {
			
			if(!stack.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		
		return inv.getInventory() != null ? inv.getInventory().get(index) : ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		if(inv.getInventory() == null) {
			return ItemStack.EMPTY;
		}
		
		return ItemStackHelper.getAndSplit(inv.getInventory(), index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		
		ItemStack ret = inv.getInventory().get(index).copy();
		inv.getInventory().set(index, ItemStack.EMPTY);
		
		return ret;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		inv.getInventory().set(index, stack);
		
	}
	
	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}
	
	@Override
	public void markDirty() {
		
		tileEntity.markDirty();
		
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		
		return !tileEntity.isInvalid() && tileEntity.getWorld().isBlockLoaded(tileEntity.getPos()) && tileEntity.getDistanceSq(player.posX, player.posY, player.posZ) < 64;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}
	
	@Override
	public void closeInventory(EntityPlayer player) {}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		
		return true;
	}
	
	@Override
	public int getField(int id) {
		
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {}
	
	@Override
	public int getFieldCount() {
		
		return 0;
	}
	
	@Override
	public void clear() {}
	
}
