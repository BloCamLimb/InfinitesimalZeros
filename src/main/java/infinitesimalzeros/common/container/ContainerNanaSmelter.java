package infinitesimalzeros.common.container;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.container.slot.SlotOutput;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerNanaSmelter extends ContainerBasic {
	
	public ContainerNanaSmelter(InventoryPlayer player, TileEntitySmelter tileEntity) {
		
		super(player, tileEntity);
		
		this.tileEntity = tileEntity;
		
		tileEntity.open(player.player);
		
		inv.openInventory(player.player);
		
		addSlotToContainer(new Slot(inv, 0, 42, 30) {
			@Override
			public boolean isItemValid(ItemStack stack) {
			
				return RecipeCoreT1.recipeExists(stack);
			
		}
			@Override
			public boolean canTakeStack(EntityPlayer playerIn) {
				
				return !tileEntity.masterControl;
			}});
		addSlotToContainer(new SlotOutput(this, inv, 1, 118, 30));
		
		slotCount = 1;
		
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, x * 19 + 4, 96 + y * 19));
			
		
		
		for(int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(player, x, 4 + x * 19, 159));
		
		
	}
	
	/*@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		
		super.onContainerClosed(playerIn);
	}

	@Override
	public void detectAndSendChanges() {
		
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener listener = (IContainerListener) this.listeners.get(i);
			
			if(this.cookTime != this.tileEntity.getField(2))
				listener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
			if(this.burnTime != this.tileEntity.getField(0))
				listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
			if(this.currentBurnTime != this.tileEntity.getField(1))
				listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
			if(this.totalCookTime != this.tileEntity.getField(3))
				listener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
		}
		
		this.cookTime = this.tileEntity.getField(2);
		this.burnTime = this.tileEntity.getField(0);
		this.currentBurnTime = this.tileEntity.getField(1);
		this.totalCookTime = this.tileEntity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		
		this.tileEntity.setField(id, data);
	}*/
	
	
	
	/*@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3) {
				if(!this.mergeItemStack(stack1, 4, 40, true))
					return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			} else if(index != 2 && index != 1 && index != 0) {
				Slot slot1 = (Slot) this.inventorySlots.get(index + 1);
				
				
					if(!this.mergeItemStack(stack1, 0, 2, false)) {
						return ItemStack.EMPTY;
					} else if(index >= 4 && index < 31) {
						if(!this.mergeItemStack(stack1, 31, 40, false))
							return ItemStack.EMPTY;
					} else if(index >= 31 && !this.mergeItemStack(stack1, 4, 31, false)) {
						return ItemStack.EMPTY;
					}
				
			} else if(!this.mergeItemStack(stack1, 4, 40, false)) {
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
				
			}
			if(stack1.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}*/
	
}
