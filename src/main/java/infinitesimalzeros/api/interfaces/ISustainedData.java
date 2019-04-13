package infinitesimalzeros.api.interfaces;

import net.minecraft.item.ItemStack;

public interface ISustainedData {
	
	void writeSustainedData(ItemStack itemStack);
	
	void readSustainedData(ItemStack itemStack);
	
}
