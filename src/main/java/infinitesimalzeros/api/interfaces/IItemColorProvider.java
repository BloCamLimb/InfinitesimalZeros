package infinitesimalzeros.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IItemColorProvider {
	
	int getItemStackColor(ItemStack stack, int colorIn);
	
}
