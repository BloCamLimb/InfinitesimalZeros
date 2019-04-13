package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.api.interfaces.IInventoryZ;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class InventoryHandler implements IItemHandlerModifiable {
	
	IInventoryZ inv;
	int slots;
	int slotOffset;
	boolean[] canInsert;
	boolean[] canExtract;
	
	public InventoryHandler(int slots, IInventoryZ inventory, int slotOffset, boolean[] canInsert, boolean[] canExtract) {
		
		this.slots = slots;
		this.inv = inventory;
		this.slotOffset = slotOffset;
		this.canInsert = canInsert;
		this.canExtract = canExtract;
	}
	
	public InventoryHandler(int slots, IInventoryZ inventory) {
		
		this(slots, inventory, 0, new boolean[slots], new boolean[slots]);
		for(int i = 0; i < slots; i++)
			this.canExtract[i] = this.canInsert[i] = true;
	}
	
	public InventoryHandler(int slots, IInventoryZ inventory, int slotOffset, boolean canInsert, boolean canExtract) {
		
		this(slots, inventory, slotOffset, new boolean[slots], new boolean[slots]);
		for(int i = 0; i < slots; i++) {
			this.canInsert[i] = canInsert;
			this.canExtract[i] = canExtract;
		}
	}
	
	@Override
	public int getSlots() {
		
		return slots;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		
		return this.inv.getInventory().get(this.slotOffset+slot);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		
		if(!canInsert[slot]||stack.isEmpty())
			return stack;
		
		stack = stack.copy();

		int offsetSlot = this.slotOffset+slot;
		ItemStack currentStack = inv.getInventory().get(offsetSlot);

		if(currentStack.isEmpty())
		{
			int accepted = Math.min(stack.getMaxStackSize(), 64);
			if(accepted < stack.getCount())
			{
				if(!simulate)
				{
					inv.getInventory().set(offsetSlot, stack.splitStack(accepted));
					inv.doGraphicalUpdates(offsetSlot);
					return stack;
				}
				else
				{
					stack.shrink(accepted);
					return stack;
				}
			}
			else
			{
				if(!simulate)
				{
					inv.getInventory().set(offsetSlot, stack);
					inv.doGraphicalUpdates(offsetSlot);
				}
				return ItemStack.EMPTY;
			}
		}
		else
		{
			if(!ItemHandlerHelper.canItemStacksStack(stack, currentStack))
				return stack;

			int accepted = Math.min(stack.getMaxStackSize(), 64)-currentStack.getCount();
			if(accepted < stack.getCount())
			{
				if(!simulate)
				{
					ItemStack newStack = stack.splitStack(accepted);
					newStack.grow(currentStack.getCount());
					inv.getInventory().set(offsetSlot, newStack);
					inv.doGraphicalUpdates(offsetSlot);
					return stack;
				}
				else
				{
					stack.shrink(accepted);
					return stack;
				}
			}
			else
			{
				if(!simulate)
				{
					ItemStack newStack = stack.copy();
					newStack.grow(currentStack.getCount());
					inv.getInventory().set(offsetSlot, newStack);
					inv.doGraphicalUpdates(offsetSlot);
				}
				return ItemStack.EMPTY;
			}
		}
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		
		if(!canExtract[slot]||amount==0)
			return ItemStack.EMPTY;

		int offsetSlot = this.slotOffset+slot;
		ItemStack currentStack = inv.getInventory().get(offsetSlot);

		if(currentStack.isEmpty())
			return ItemStack.EMPTY;

		int extracted = Math.min(currentStack.getCount(), amount);

		ItemStack copy = currentStack.copy();
		copy.setCount(extracted);
		if(!simulate)
		{
			if(extracted < currentStack.getCount())
				currentStack.shrink(extracted);
			else
				currentStack = ItemStack.EMPTY;
			inv.getInventory().set(offsetSlot, currentStack);
			inv.doGraphicalUpdates(offsetSlot);
		}
		return copy;
	}
	
	@Override
	public int getSlotLimit(int slot) {
		
		return 64;
	}
	
	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		
		inv.getInventory().set(this.slotOffset+slot, stack);
		inv.doGraphicalUpdates(this.slotOffset+slot);
	}
	
}
