package infinitesimalzeros.common.util;

import java.util.Map;

import infinitesimalzeros.api.EnumColor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public final class InventoryUtils {
	
	public static final int[] EMPTY = new int[] {};

	public static int[] getIntRange(int start, int end)
	{
		int[] ret = new int[1 + end - start];

		for(int i = start; i <= end; i++)
		{
			ret[i - start] = i;
		}

		return ret;
	}
	
	public static IInventory checkChestInv(IInventory inv)
	{
		if(inv instanceof TileEntityChest)
		{
			TileEntityChest main = (TileEntityChest)inv;
			TileEntityChest adj = null;

			if(main.adjacentChestXNeg != null)
			{
				adj = main.adjacentChestXNeg;
			}
			else if(main.adjacentChestXPos != null)
			{
				adj = main.adjacentChestXPos;
			}
			else if(main.adjacentChestZNeg != null)
			{
				adj = main.adjacentChestZNeg;
			}
			else if(main.adjacentChestZPos != null)
			{
				adj = main.adjacentChestZPos;
			}

			if(adj != null)
			{
				return new InventoryLargeChest("", main, adj);
			}
		}

		return inv;
	}

	public static boolean areItemsStackable(ItemStack toInsert, ItemStack inSlot) 
	{
		if(toInsert.isEmpty() || inSlot.isEmpty())
		{
			return true;
		}
		
    	return inSlot.isItemEqual(toInsert) && ItemStack.areItemStackTagsEqual(inSlot, toInsert);
  	}
	
	public static ItemStack loadFromNBT(NBTTagCompound nbtTags)
	{
		ItemStack ret = new ItemStack(nbtTags);
		return ret;
	}
	
	public static boolean isItemHandler(TileEntity tile, EnumFacing side)
	{
		return CapabilityUtils.hasCapability(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	}
	
	public static IItemHandler getItemHandler(TileEntity tile, EnumFacing side)
	{
		return CapabilityUtils.getCapability(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	}

	/*TODO From CCLib -- go back to that version when we're using dependencies again*/
	public static boolean canStack(ItemStack stack1, ItemStack stack2) {
		return stack1.isEmpty() || stack2.isEmpty() ||
				(stack1.getItem() == stack2.getItem() &&
						(!stack2.getHasSubtypes() || stack2.getItemDamage() == stack1.getItemDamage()) &&
						ItemStack.areItemStackTagsEqual(stack2, stack1)) &&
						stack1.isStackable();
	}

}
