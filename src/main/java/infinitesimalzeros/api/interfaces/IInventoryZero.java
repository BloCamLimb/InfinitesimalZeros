package infinitesimalzeros.api.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public interface IInventoryZero {
	
	NonNullList<ItemStack> getInventory();
	
	boolean isStackValid(int slot, ItemStack stack);
	
	void doGraphicalUpdates(int slot);
	
}
