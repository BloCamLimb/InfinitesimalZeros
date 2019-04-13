package infinitesimalzeros.common.container.slot;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotOutput extends SlotBasic {

	public SlotOutput(Container container, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		
		super(container, inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		
		return false;
	}
	
}
