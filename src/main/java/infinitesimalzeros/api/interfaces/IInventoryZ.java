package infinitesimalzeros.api.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public interface IInventoryZ {
	
	NonNullList<ItemStack> getInventory();
	
	void doGraphicalUpdates(int slot);
	
}
